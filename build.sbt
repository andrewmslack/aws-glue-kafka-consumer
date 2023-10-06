ThisBuild / organization := "com.evenfinancial"
ThisBuild / scalaVersion := "2.12.18"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalacOptions ++= Seq(
  "-Wconf:any:wv",
  "-Ypartial-unification",
  "-Duser.timezone=UTC",
  "-language:higherKinds",
  "-language:postfixOps"
)

// ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
// ThisBuild / evictionErrorLevel := Level.Info

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

ThisBuild / resolvers ++= Seq(
  "GCS Maven Central mirror" at "https://maven-central.storage-download.googleapis.com/repos/central/data/",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"
)

val fs2KafkaVer = "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "aws-glue-consumer",
    libraryDependencies ++= Seq(
      "com.github.fd4s" %% "fs2-kafka" % fs2KafkaVer,
      "software.amazon.glue"           % "schema-registry-kafkastreams-serde" % "1.1.15",
      "com.github.scopt" %% "scopt" % "4.0.1"      
    )
  )
