package com.evenfinancial.kafka.consumer

import cats.effect._
import fs2.kafka._
import com.amazonaws.services.schemaregistry.kafkastreams.AWSKafkaAvroSerDe
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants
import scala.collection.JavaConverters._
import java.util.UUID
import scala.concurrent.duration._

object AWSGlueConsumer extends IOApp {

  def serdeConfigMap(registryName: String, awsRegion: String) = {
    Map(
      AWSSchemaRegistryConstants.REGISTRY_NAME -> registryName,
      AWSSchemaRegistryConstants.AWS_REGION -> awsRegion,
      AWSSchemaRegistryConstants.AVRO_RECORD_TYPE -> "GENERIC_RECORD"
    ).asJava
  }

  def serde(registryName: String, awsRegion: String) = {
    val avroSerde = new AWSKafkaAvroSerDe()
    avroSerde.configure(
      serdeConfigMap(registryName, awsRegion),
      true
    )
    avroSerde
  }

  case class Config(
      keyRegistry: String = "undefined",
      valueRegistry: String = "undefined",
      bootstrapServers: String = "undefined",
      groupId: Option[String] = None,
      topic: String = "undefined"
  )

  def run(args: List[String]): IO[ExitCode] = {

    import scopt.OParser

    val builder = OParser.builder[Config]
    val parser1 = {
      import builder._
      OParser.sequence(
        programName("aws-glue-consumer"),
        head("myapp", "1.0"),
        opt[String]('k', "key-registry")
          .required()
          .valueName("<key-registry>")
          .action((k, c) => c.copy(keyRegistry = k))
          .text("key-registry is a required field"),
        opt[String]('v', "value-registry")
          .required()
          .valueName("<value-registry>")
          .action((v, c) => c.copy(valueRegistry = v))
          .text("value-registry is a required field"),
        opt[String]('b', "bootstrap-servers")
          .required()
          .valueName("<bootstrap-servers>")
          .action((v, c) => c.copy(bootstrapServers = v))
          .text("bootstrap-servers is a required field"),
        opt[String]('g', "consumer-group-id")
          .valueName("<consumer-group-id>")
          .action((v, c) => c.copy(groupId = Some(v))),
        opt[String]('t', "topic")
          .required()
          .valueName("<topic>")
          .action((v, c) => c.copy(topic = v))
          .text("topic is a required field")
      )
    }

    val res = OParser.parse(parser1, args, Config()) match {
      case Some(config) =>
        val keyAvroSerde = serde(config.keyRegistry, "us-east-1")
        val valueAvroSerde = serde(config.valueRegistry, "us-east-1")

        // do something with the obtained config
        val consumerSettings: ConsumerSettings[IO, Object, Object] =
          ConsumerSettings(
            keyDeserializer =
              Deserializer.delegate[IO, Object](keyAvroSerde.deserializer()),
            valueDeserializer =
              Deserializer.delegate[IO, Object](valueAvroSerde.deserializer())
          ).withAutoOffsetReset(AutoOffsetReset.Earliest)
            .withBootstrapServers(config.bootstrapServers)
            .withGroupId(
              config.groupId.getOrElse(
                s"aws-glue-consumer-${config.topic}-${UUID.randomUUID().toString()}"
              )
            )

        // Stream of consumer records
        val stream: fs2.Stream[IO, Unit] =
          KafkaConsumer
            .stream(consumerSettings)
            .evalTap(
              _.subscribeTo(config.topic)
            )
            .flatMap { consumer =>
              consumer.stream
                .evalMap { committable =>

                  // Process the record
                  IO.blocking(
                    println(
                      s"Consumed: ${committable.record.key} -> ${committable.record.value}"
                    )
                  )
                }
            }

        // Run the stream
        stream.compile.drain.as(ExitCode.Success)
      case _ =>
        IO(ExitCode.Error)
    }
    res
  }
}
