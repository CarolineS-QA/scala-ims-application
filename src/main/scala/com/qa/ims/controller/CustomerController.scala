package com.qa.ims.controller



import com.qa.ims.configuration.MongoConfiguration.{customerCollection, customerReader, customerWriter}
import com.qa.ims.model.CustomerModel
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.compat.{legacyWriterNewValue, toDocumentReader, toDocumentWriter}
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
  def updateName(id: ObjectId, newName: String): Unit = {
    customerCollection.updateOne(equal("_id", id), set("forename", newName)).toFuture.onComplete {
      case Success(value) => println(s"The value has been updated to: $value")
      case Failure(error) => println(error)
    }
  }
*/

  def updateCustomerByUsername(username: String, forename: String, surname: String, age: Int): Unit = {
    val selector = document("username" -> username)
    val modifier = document("username" -> username, "forename" -> forename, "surname" -> surname, "age" -> age)

    customerCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteByUsername(personColl: BSONCollection) =
    personColl.delete.one(BSONDocument("username" -> "Chris123"))



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
