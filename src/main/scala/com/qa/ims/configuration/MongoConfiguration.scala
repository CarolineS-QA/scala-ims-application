package com.qa.ims.configuration

import com.qa.ims.model.{CustomerModel, OrderModel, ProductModel}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

object MongoConfiguration {

  val mongoUri = "mongodb://localhost:27017"

  import ExecutionContext.Implicits.global

  val driver: AsyncDriver = AsyncDriver()
  val parsedUri: Future[MongoConnection.ParsedURI] = MongoConnection.fromString(mongoUri)

  val connection: Future[MongoConnection] = parsedUri.flatMap(driver.connect)
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
