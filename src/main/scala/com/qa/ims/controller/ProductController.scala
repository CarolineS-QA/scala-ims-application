package com.qa.ims.controller

import com.qa.ims.configuration.MongoConfiguration.{productCollection, productReader, productWriter}
import com.qa.ims.model.ProductModel
import reactivemongo.api.Cursor
import reactivemongo.api.bson.compat.{legacyWriterNewValue, toDocumentWriter}
import reactivemongo.api.bson.{BSONDocument, document}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object ProductController {

  // Create
  def createProduct(product: ProductModel): Future[Unit] =
    productCollection.flatMap(_.insert.one(product).map(_ => {}))

  // Read
  def findAllProducts(): Unit = {
    val findFuture: Future[List[ProductModel]] = productCollection.flatMap(_.find(document(), None)
      .cursor[ProductModel]()
      .collect[List](-1, Cursor.FailOnError[List[ProductModel]]()))
    findFuture onComplete {
      case Success(productOption) => println(productOption.toString)
      case Failure(_) =>
    }
  }

  def findProductByName(name: String): ProductModel = {
    val selector = BSONDocument("name" -> name)
    val findFuture = productCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(productOption) =>
        println(productOption.get)
      case Failure(_) =>
        throw new NoSuchElementException
    }
    Await.result(findFuture, Duration.Inf).get
  }

  def findProductByCategory(category: String) {
    val selector = BSONDocument("category" -> category)
    val findFuture = productCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(_) =>
    }
  }

  def findProductById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = productCollection.flatMap(_.find(selector, None).one)
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
