package com.qa.ims.controller



import com.qa.ims.configuration.MongoConfiguration.{customerCollection, customerReader, customerWriter}
import com.qa.ims.model.CustomerModel
import reactivemongo.api.Cursor
import reactivemongo.api.bson.compat.{legacyWriterNewValue, toDocumentWriter}
import reactivemongo.api.bson.{BSONDocument, document}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


object CustomerController {

  // Create
  def createCustomer(customer: CustomerModel): Future[Unit] =
    customerCollection.flatMap(_.insert.one(customer).map(_ => {}))

  // Read
  def findAllCustomers(): Unit = {
    val findFuture: Future[List[CustomerModel]] = customerCollection.flatMap(_.find(document(), None)
      .cursor[CustomerModel]()
      .collect[List](-1, Cursor.FailOnError[List[CustomerModel]]()))
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.toString)
      case Failure(_) =>
    }
  }

  def findCustomerByName(forename: String) {
    val selector = BSONDocument("forename" -> forename)
    val findFuture = customerCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(_) =>
    }
  }

  def findCustomerByUsername(username: String) {
    val selector = BSONDocument("username" -> username)
    val findFuture = customerCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(_) =>
    }
  }

  def findCustomerById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = customerCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(customerOption) => println(customerOption.get)
      case Failure(_) =>
    }
  }

  def updateCustomerByUsername(username: String, forename: String, surname: String, age: Int): Unit = {
    val selector = document("username" -> username)
    val modifier = document("username" -> username, "forename" -> forename, "surname" -> surname, "age" -> age)
    customerCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteById(id: String): Future[WriteResult] = {
    val selector = document("_id" -> id)
    customerCollection.flatMap(_.delete.one(selector))
  }
}
