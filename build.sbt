name := "scala-ims-application"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "0.20.11",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.0-beta2",
  "org.slf4j" % "slf4j-log4j12" % "1.7.25",
  "org.slf4j" % "slf4j-simple" % "1.7.30",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.reactivemongo" %% "reactivemongo-akkastream" % "0.20.11"
)