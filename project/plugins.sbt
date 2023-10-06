addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.1")

resolvers ++= Seq(
  "GCS Maven Central mirror" at "https://maven-central.storage-download.googleapis.com/repos/central/data/",
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"    
)
 
