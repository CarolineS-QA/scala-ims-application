package com.qa.ims.controller


import com.qa.ims.model.CustomerModel
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}


object CustomerController {

  val mongoUri = "mongodb://localhost:27017/DbIMS?authMode=scram-sha1"

  import ExecutionContext.Implicits.global

  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  val mongoClient: MongoClient = MongoClient(mongoUri)
  val database: MongoDatabase = mongoClient.getDatabase("DbIMS")
  val collection: MongoCollection[CustomerModel] = database.getCollection("customer")


  println("Controller Accessed")

  val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  println("Controller Accessed")

  def insertCustomer(doc: CustomerModel): Unit = {
    collection.insertOne(doc)
  }


  println("Controller Accessed")




}
