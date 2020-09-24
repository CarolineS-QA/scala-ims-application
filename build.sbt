lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """scala-ims-application""",
    organization := "com.qa",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.reactivemongo" %% "reactivemongo" % "0.20.11",
      "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.0-beta2",
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
