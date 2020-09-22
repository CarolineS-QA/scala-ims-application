package com.qa.ims.configuration

import java.util.concurrent.TimeUnit

import com.qa.ims.model.{CustomerModel, OrderModel, ProductModel}
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


  val mongoUri = "mongodb://localhost:27017"

  import ExecutionContext.Implicits.global

  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  val connection = parsedUri.flatMap(driver.connect(_))
  def db: Future[DB] = connection.flatMap(_.database("DbIMS"))
  def customerCollection: Future[BSONCollection] = db.map(_.collection("customer"))
  def productCollection: Future[BSONCollection] = db.map(_.collection("product"))
  def orderCollection: Future[BSONCollection] = db.map(_.collection("order"))

  implicit def customerWriter: BSONDocumentWriter[CustomerModel] = Macros.writer[CustomerModel]
  implicit def customerReader: BSONDocumentReader[CustomerModel] = Macros.reader[CustomerModel]

  implicit def productWriter: BSONDocumentWriter[ProductModel] = Macros.writer[ProductModel]
  implicit def productReader: BSONDocumentReader[ProductModel] = Macros.reader[ProductModel]

  implicit def orderWriter: BSONDocumentWriter[OrderModel] = Macros.writer[OrderModel]
  implicit def orderReader: BSONDocumentReader[OrderModel] = Macros.reader[OrderModel]



}
