package com.qa.ims.configuration

import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.commands.WriteResult

import scala.util.{Failure, Success}

object MongoConfiguration {
  val mongoUri: String = "mongodb://root:root@clusterims-shard-00-00.tejxf.mongodb.net:27017,clusterims-shard-00-01.tejxf.mongodb.net:27017,clusterims-shard-00-02.tejxf.mongodb.net:27017/DbIMS?ssl=true&replicaSet=atlas-15lq0w-shard-0&authSource=admin&retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")

  import ExecutionContext.Implicits.global // use any appropriate context

  // Connect to the database: Must be done only once per application
  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection = parsedUri.flatMap(driver.connect(_))
  def db1: Future[DB] = futureConnection.flatMap(_.database("DbIMS"))

  def personCollection = db1.map(_.collection("DbIMS"))

  val document1 = BSONDocument(
    "firstName" -> "Chris",
    "lastName" -> "Red",
    "age" -> 24)

  def insertDoc1(coll: BSONCollection, doc: BSONDocument): Future[Unit] = {
    val writeRes: Future[WriteResult] = coll.insert(document1)

    writeRes.onComplete { // Dummy callbacks
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) =>
        println(s"successfully inserted document with result: $writeResult")
    }

    writeRes.map(_ => {}) // in this example, do nothing with the success



  println(parsedUri)


}
