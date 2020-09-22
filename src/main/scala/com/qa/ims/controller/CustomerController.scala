package com.qa.ims.controller



import com.qa.ims.configuration.MongoConfiguration.{customerCollection, customerReader, customerWriter}
import com.qa.ims.model.CustomerModel
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.compat.toDocumentReader
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object CustomerController {

  // Create
  def createCustomer(customer: CustomerModel): Future[Unit] =
    customerCollection.flatMap(_.insert.one(customer).map(_ => {}))

  // Read
  def findAllCustomers: Unit = {
    val findFuture: Future[List[CustomerModel]] = customerCollection.flatMap(_.find(document())
      .cursor[CustomerModel]()
      .collect[List](-1, Cursor.FailOnError[List[CustomerModel]]()))
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.toString)
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


  def findCustomerByUsername(username: String) {
    val selector = BSONDocument("username" -> username)

    val findFuture = customerCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(f) => {}
    }
  }



  def findCustomerById(id: String) {
    val cid = BSONObjectID.parse(id)
    val selector = BSONDocument("_id" -> cid.get)

    val findFuture = customerCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(f) => {}
    }
  }


/*
  def update(collection: BSONCollection, age: Int): Future[Option[CustomerModel]] = {
    implicit val reader = Macros.reader[CustomerModel]

    val result = collection.findAndUpdate(
      BSONDocument("forename" -> "Chris"),
      BSONDocument("$set" -> BSONDocument("age" -> 17)),
      fetchNewObject = true)

    result.map(_.result[CustomerModel])
  }
*/



/*
    def findCustomerById(id: String): CustomerModel = {
      val cid = BSONObjectID.parse(id)
      val query = BSONDocument("_id" -> cid.get)

      val customer: Future[List[CustomerModel]] = customerCollection.flatMap(_.find(query))
        .cursor[CustomerModel]
        .collect[List](-1, Cursor.FailOnError[List[CustomerModel]]())

     customer andThen {
        case Success(value) => {
          value[0]
        }
        case Failure(e) => {
          None
        }
      }
*/

}
