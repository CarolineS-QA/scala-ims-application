package configuration

import model.{CustomerModel, OrderModel, ProductModel}
import play.api.http.Writeable
import play.api.libs.json.Json
import reactivemongo.play.json._
import play.api.mvc.Codec
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.annotation.nowarn
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




  @nowarn
  implicit def customerWriter: BSONDocumentWriter[CustomerModel] = Macros.writer[CustomerModel]
  @nowarn
  implicit def customerReader: BSONDocumentReader[CustomerModel] = Macros.reader[CustomerModel]
  @nowarn
  implicit def productWriter: BSONDocumentWriter[ProductModel] = Macros.writer[ProductModel]
  @nowarn
  implicit def productReader: BSONDocumentReader[ProductModel] = Macros.reader[ProductModel]
  @nowarn
  implicit def orderWriter: BSONDocumentWriter[OrderModel] = Macros.writer[OrderModel]
  @nowarn
  implicit def orderReader: BSONDocumentReader[OrderModel] = Macros.reader[OrderModel]


  implicit def customerPlayWriter = Json.writes[CustomerModel]

  implicit def customerPlayReader = Json.reads[CustomerModel]
}
