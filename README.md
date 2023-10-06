# aws-glue-kafka-consumer
Simple glue aware kafka consumer

** Make sure you have you AWS credentials set beforehand

```
$ sbt

sbt:aws-glue-consumer> run

Usage: aws-glue-consumer [options]

  -k, --key-registry <key-registry>
                           key-registry is a required field
  -v, --value-registry <value-registry>
                           value-registry is a required field
  -b, --bootstrap-servers <bootstrap-servers>
                           bootstrap-servers is a required field
  -g, --consumer-group-id <consumer-group-id>
  -t, --topic <topic>      topic is a required field
  -n, --number-of-messages <number>

sbt:aws-glue-consumer> run -k my-glue-key-registry -v ,my-glue-value-registry -b "kafka-broker:9092" -t my-topic

```

alternatively, using coursier run local artifact:

````

sbt:aws-glue-consumer> publishLocal
...
[info] 	delivering ivy file to .../target/scala-2.12/ivy-0.1.0-SNAPSHOT.xml
...
[success] Total time: 3 s, completed Oct 6, 2023, 11:51:40 AM

sbt:aws-glue-consumer> exit

$> cs launch com.evenfinancial::aws-glue-consumer:0.1.0-SNAPSHOT -- \
            -k my-glue-key-registry \
            -v my-glue-value-registry \
            -b "kafka-broker:9092" \
            -t my-topic -n 1 

...
```

