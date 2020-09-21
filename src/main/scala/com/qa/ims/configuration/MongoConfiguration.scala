package com.qa.ims.configuration

import java.util.concurrent.TimeUnit

import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{Await, ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection, MongoConnectionOptions}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration

object MongoConfiguration {
/*
  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")

  val database: MongoDatabase = mongoClient.getDatabase("DbIMS")


  val collection: MongoCollection[Document] = database.getCollection("customer")



  /// Create
  try {
    collection.insertOne(doc)
    println("Document successfully inserted into customer collection")
  } catch {
    case e: Exception => println("Failed to insert document into customer collection")
  }
*/

  val mongoUri = "mongodb://localhost:27017/mydb?authMode=scram-sha1"

  import ExecutionContext.Implicits.global // use any appropriate context

  // Connect to the database: Must be done only once per application
  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection = parsedUri.flatMap(driver.connect(_))
  def db1: Future[DB] = futureConnection.flatMap(_.database("DbIMS"))
  def personCollection = db1.map(_.collection("customer"))

  // Write Documents: insert or update

  implicit def personWriter: BSONDocumentWriter[Customer] = Macros.writer[Customer]
  // or provide a custom one

  // use personWriter
  val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  def createPerson(collection: MongoCollection[Document]): Future[Unit] =
    collection.flatMap(collection.insertOne(doc).map(_ => {}))

  def updatePerson(customer: Customer): Future[Int] = {
    val selector = document(
      "firstName" -> customer.firstName,
      "lastName" -> customer.lastName
    )

    // Update the matching person
    personCollection.flatMap(_.updateOne(selector, person).map(_.n))
  }

  implicit def personReader: BSONDocumentReader[Customer] = Macros.reader[Customer]
  // or provide a custom one

  def findPersonByAge(age: Int): Future[List[Customer]] =
    personCollection.flatMap(_.find(document("age" -> age)). // query builder
      cursor[Customer](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[Customer]]()))
  // ... deserializes the document using personReader



  // Custom persistent types
  case class Customer(firstName: String, lastName: String, age: Int)

  /// Read
  // Count



  println(s"total # of documents after inserting 100 small ones (should be 101):  ${insertAndCount.headResult()}")



}
