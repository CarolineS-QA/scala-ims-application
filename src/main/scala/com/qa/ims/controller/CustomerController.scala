package com.qa.ims.controller


import com.qa.ims.model.CustomerModel
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.bson.BSONObjectID

import scala.Console.println
import scala.Predef.println
import scala.util.{Failure, Success}


object CustomerController {

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


  // use personWriter
  def createCustomer(customer: CustomerModel): Future[Unit] =
    customerCollection.flatMap(_.insert.one(customer).map(_ => {}))


  def findCustomerById(id: String) {
    val cid = BSONObjectID.parse(id)
    val selector = BSONDocument("_id" -> id)

    val findFuture = customerCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(f) => {}
    }
  }


  def findCustomerByName(forename: String) {
    val selector = BSONDocument("forename" -> forename)
    val findFuture = customerCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(f) => {}
    }
  }

//  def findCustomerById(id: String): CustomerModel = {
//    val cid = BSONObjectID.parse(id)
//    val query = BSONDocument("_id" -> cid.get)
//
//    val customer: Future[List[CustomerModel]] = customerCollection.flatMap(_.find(query))
//      .cursor[CustomerModel]
//      .collect[List](-1, Cursor.FailOnError[List[CustomerModel]]())
//
//    customer andThen {
//      case Success(value) => {
//        value[0]
//      }
//      case Failure(e) => {
//        None
//      }
//    }

  }





  println("Controller Accessed")

}
