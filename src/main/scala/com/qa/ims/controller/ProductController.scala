package com.qa.ims.controller

import akka.stream.scaladsl.{Sink, Source}
import com.qa.ims.configuration.MongoConfiguration.{customerCollection, productCollection, productReader, productWriter}
import com.qa.ims.model.{CustomerModel, ProductModel}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.akkastream.State
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.compat.{legacyWriterNewValue, toDocumentReader, toDocumentWriter}
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}
import reactivemongo.api.bson.{BSON, BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID, Macros, document}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object ProductController {

  // Create
  def createProduct(product: ProductModel): Future[Unit] =
    productCollection.flatMap(_.insert.one(product).map(_ => {}))

  // Read
  def findAllProducts: Unit = {
    val findFuture: Future[List[ProductModel]] = productCollection.flatMap(_.find(document())
      .cursor[ProductModel]()
      .collect[List](-1, Cursor.FailOnError[List[ProductModel]]()))
    findFuture onComplete {
      case Success(productOption) => println(productOption.toString)
      case Failure(_) =>
    }
  }

  def findProductByName(name: String) {
    val selector = BSONDocument("name" -> name)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(_) => throw new NoSuchElementException
    }
  }

  def findProductByCategory(category: String) {
    val selector = BSONDocument("category" -> category)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(_) =>
    }
  }

  def findProductById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(_) =>
    }
  }

  def updateProductByName(name: String, category: String, price: BigDecimal, inventory: Long): Unit = {
    val selector = document("name" -> name)
    val modifier = document("name" -> name, "category" -> category, "price" -> price, "inventory" -> inventory)
    println(s"Updated, the product details are now: name = $name, category = $category, price = $price, inventory = $inventory")
    productCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteProductById(id: String): Future[WriteResult] = {
    val selector = document("_id" -> id)
    productCollection.flatMap(_.delete.one(selector))
  }



}
