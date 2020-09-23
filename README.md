# Redfern Scala Inventory Management System - QA Individual Project

The Redfern Scala Inventory Management System is a [Scala](https://www.scala-lang.org/)-based application which utilised [ReactiveMongo](http://reactivemongo.org/) to create, read, update and delete entries within a [MongoDB](https://www.mongodb.com/) database hosted locally. The project's source code is being developed on [IntelliJ IDEA](https://www.jetbrains.com/idea/) and is built using [sbt](https://www.scala-sbt.org/). An executable jar was assembled using [sbt-assembly](https://github.com/sbt/sbt-assembly/tree/master/project).

The program can be run on a terminal of your choice, though is only confirmed to work on cmd.exe (Windows Command Prompt).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development purposes. See deployment for notes on how to deploy the project on a live system.
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To use this program, you will need:

- [Java SE 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) or [Java SE 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (needed to run the jar file located in the target folder)
- A [mySQL](https://www.mysql.com/) database, preferably one hosted on [GCP](https://console.cloud.google.com/) as this application was built with GCP in mind.

To function, the application requires a [MongoDB](https://www.mongodb.com/) Database. To function without altering the source code, you need to create a database called 'DbIMS', with the collections: 'customer', 'product' and 'order'.

To develop using this project, you will need:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/) or a similar IDE
- [sbt](https://www.scala-sbt.org/)

If you use this project for your own development, or wish to change the database configuration, the uri, database name and collection names can be found in MongoConfiguration.scala, which can be accessed using a Scala-compatible IDE.


## Deployment

The project is open source, therefore is available to anyone to clone or fork.
Regardless of whether you clone this repository or a repository you forked, you will need to open a terminal within the local directory and enter the following:

- sbt clean compile
- cd target/scala-2.13
- java -jar scala-ims-application-assembly-0.1.jar

Note: if you changed the 'name' stored in built.sbt, you will need to enter the new name followed by .jar instead.

## Built With

- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
- [Scala](https://www.scala-lang.org/) - Programming Language
- [sbt](https://www.scala-sbt.org/) - Dependency Management and Build Tool
- [sbt-assembly](https://github.com/sbt/sbt-assembly/tree/master/project) - Assembly Tool
- [MongoDB](https://www.mongodb.com/) - NoSQL Database Language
- [ReactiveMongo](http://reactivemongo.org/) - Database Connector
- [Git](https://git-scm.com/) - Local Repository and Version Control
- [GitHub](https://github.com/) - Online Repository, Version Control and Project Management





