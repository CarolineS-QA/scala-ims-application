package com.qa.ims.controller

import java.util.Calendar

import com.qa.ims.configuration.MongoConfiguration.{orderCollection, orderReader, orderWriter, productCollection}
import com.qa.ims.controller.ProductController.findProductByName
import com.qa.ims.model.{OrderModel, ProductModel}
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONString, document}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object OrderController {

  // Create
  def createOrder(order: OrderModel): Future[Unit] = {
    orderCollection.flatMap(_.insert.one(order).map(_ => {}))
  }

  // Read
  def findAllOrders: Unit = {
    val findFuture: Future[List[OrderModel]] = orderCollection.flatMap(_.find(document())
      .cursor[OrderModel]()
      .collect[List](-1, Cursor.FailOnError[List[OrderModel]]()))
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.toString)
      case Failure(_) =>
    }
  }

  def findOrderByBuyer(username: String) {
    val selector = BSONDocument("username" -> username)
    val findFuture = orderCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.get)
      case Failure(_) =>
    }
  }

  def findOrderById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = orderCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.get)
      case Failure(_) =>
    }
  }

  def updateOrderById(id: String, username: String, products: ListBuffer[String], price: BigDecimal): Unit = {
    val selector = document("_id" -> id)
    val modifier = document("username" -> username, "products" -> products, "date" -> Calendar.getInstance().getTime.toString(), "totalPrice" -> price)
    orderCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteOrderById(id: String): Future[WriteResult] = {
    val selector = document("_id" -> id)
    orderCollection.flatMap(_.delete.one(selector))
  }
}
