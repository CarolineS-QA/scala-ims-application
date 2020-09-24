package com.qa.ims.controller

import java.util.Calendar

import akka.util.Helpers.Requiring
import com.qa.ims.configuration.MongoConfiguration.{orderCollection, orderReader, orderWriter}
import com.qa.ims.controller.ProductController.findProductByName
import com.qa.ims.model.OrderModel
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONElement, BSONInteger, BSONObjectID, BSONString, document}
import reactivemongo.api.commands.WriteResult

import scala.Double.MinValue
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object OrderController {

  // Create
  def createOrder(username: String, productList: BSONDocument, productSeq: Seq[BSONElement]): Future[Unit] = {
    def findProductPriceByName(productSeq: Seq[BSONElement]): BigDecimal = {
      val products = productSeq.map { productTuple => productTuple.name -> BSONInteger.unapply(productTuple.value).get }

      @tailrec
      def calculate(totalPrice: BigDecimal, count: Int, products: Seq[(String, Int)]): BigDecimal = {
        if (count < 0) totalPrice
        else calculate(totalPrice + (findProductByName(products(count)._1).price * products(count)._2), count - 1, products)
      }
      calculate(0, products.length - 1, products)
    }
    findProductPriceByName(productSeq)
    orderCollection.flatMap(_.insert.one(
      OrderModel(BSONString(BSONObjectID.generate().stringify),
      username, productList, Calendar.getInstance().getTime.toString,
      findProductPriceByName(productSeq: Seq[BSONElement]))
    ).map(_ => {}))
  }

  // Read
  def findAllOrders(): Unit = {
    val findFuture: Future[List[OrderModel]] = orderCollection.flatMap(_.find(document(), None)
      .cursor[OrderModel]()
      .collect[List](-1, Cursor.FailOnError[List[OrderModel]]()))
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.toString)
      case Failure(_) =>
    }
  }

  def findOrderByBuyer(username: String) {
    val selector = BSONDocument("username" -> username)
    val findFuture = orderCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.get)
      case Failure(_) =>
    }
  }

  def findOrderById(id: String) {
    val selector = BSONDocument("_id" -> id)
    val findFuture = orderCollection.flatMap(_.find(selector, None).one)
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.get)
      case Failure(_) =>
    }
  }

  def updateOrderById(id: String, username: String, products: ListBuffer[String], price: BigDecimal): Unit = {
    val selector = document("_id" -> id)
    val modifier = document("username" -> username, "products" -> products, "date" -> Calendar.getInstance().getTime.toString, "totalPrice" -> price)
    orderCollection.flatMap(_.update.one(selector, modifier).map(_.n))
  }

  def deleteOrderById(id: String): Future[WriteResult] = {
    val selector = document("_id" -> id)
    orderCollection.flatMap(_.delete.one(selector))
  }
}
