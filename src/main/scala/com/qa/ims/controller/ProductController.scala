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
      case Failure(f) => {}
    }
  }

  def findProductByName(name: String) {
    val selector = BSONDocument("name" -> name)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption)
      case Failure(f) => {}
    }
  }

  /*

  implicit def artistHandler: BSONHandler[BSONDocument, Artist] = ???
  implicit def mapHandler: BSONHandler[BSONDocument, Map[String, Artist]] = ???

  def findProductPriceByName(name: String): String = {
    val query = BSONDocument("name" -> name)
    val price: String = ???

    // only fetch the name field for the result documents
    val projection = BSONDocument("price" -> price)

    val mapperPrice: Try[BSONDocument] = BSON.writeDocument(projection)
    val findPrice: Map[String, String] = BSON.readDocument[Map[String, String]]

    mapperPrice
    return findPrice


    println(projection.toString())
    projection.toString()
  }

*/
  def findProductByCategory(category: String) {
    val selector = BSONDocument("category" -> category)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(f) => {}
    }
  }

  def findProductById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = productCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(productOption) => println(productOption.get)
      case Failure(f) => {}
    }
  }

  def updateProductByName(name: String, category: String, price: BigDecimal, inventory: Long): Unit = {
    val selector = document("name" -> name)
    val modifier = document("name" -> name, "category" -> category, "price" -> price, "inventory" -> inventory)
    println(s"Updated, the product details are now: name = $name, category = $category, price = $price, inventory = $inventory")
    productCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteProductByName(name: String)  = {
    val selector = document("name" -> name)
    productCollection.flatMap(_.delete.one(selector))
  }



}
