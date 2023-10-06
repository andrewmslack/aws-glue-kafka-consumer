# aws-glue-kafka-consumer
glue aware kafka consumer


```
$ sbt

sbt> run

Usage: aws-glue-consumer [options]

  -k, --key-registry <key-registry>
                           key-registry is a required field
  -v, --value-registry <value-registry>
                           value-registry is a required field
  -b, --bootstrap-servers <bootstrap-servers>
                           bootstrap-servers is a required field
  -g, --consumer-group-id <consumer-group-id>
  -t, --topic <topic>      topic is a required field


sbt> run -k my-glue-key-registry -v ,my-glue-value-registry -b "kafka-broker:9092" -t my-topic

```

alternatively, usign cousier:

````

sbt> publishLocal
...
[info] 	delivering ivy file to /Users/andrewslack/workspace/aws-glue-kafka-consumer/target/scala-2.12/ivy-0.1.0-SNAPSHOT.xml
...
[success] Total time: 3 s, completed Oct 6, 2023, 11:51:40 AM

sbt:aws-glue-consumer> exit

$> cs launch com.evenfinancial::aws-glue-consumer:0.1.0-SNAPSHOT -- -k my-glue-key-registry -v ,my-glue-value-registry -b "kafka-broker:9092" -t my-topic
