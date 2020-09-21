name := "scala-ims-application"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "1.0.0",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.0-beta2",
  "org.slf4j" % "slf4j-log4j12" % "1.7.25"
)