package configuration

import model.{CustomerModel, OrderModel, ProductModel}
import play.api.http.Writeable
import play.api.libs.json.Json
import reactivemongo.play.json._
import play.api.mvc.Codec
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}
import reactivemongo.play.json.collection.JSONCollection

import scala.annotation.nowarn
import scala.concurrent.{ExecutionContext, Future}

object MongoConfiguration {

  val mongoUri = "mongodb://localhost:27017"

  import ExecutionContext.Implicits.global

  val driver: AsyncDriver = AsyncDriver()
  val parsedUri: Future[MongoConnection.ParsedURI] = MongoConnection.fromString(mongoUri)

  val connection: Future[MongoConnection] = parsedUri.flatMap(driver.connect)
  def db: Future[DB] = connection.flatMap(_.database("DbIMS"))
  def customerCollection: Future[JSONCollection] = db.map(_.collection[JSONCollection]("customer"))
  def productCollection: Future[JSONCollection] = db.map(_.collection[JSONCollection]("product"))
  def orderCollection: Future[JSONCollection] = db.map(_.collection[JSONCollection]("order"))


}
