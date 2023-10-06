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


sbt> run -k glue-keys -v glue-values -b my-kafka:9092 -t my-topic-1

```
