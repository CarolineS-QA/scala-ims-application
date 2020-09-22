package com.qa.ims.controller

import java.util.Calendar

import com.qa.ims.configuration.MongoConfiguration.{orderCollection, orderReader, orderWriter, productCollection}
import com.qa.ims.controller.ProductController.findProductByName
import com.qa.ims.model.{OrderModel, ProductModel}
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONString, document}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object OrderController {

  // Create
  def createOrder(order: OrderModel): Future[Unit] = {
    val products = order.products
    var totalPrice = BigDecimal(0)
    products.foreach(
      products => {
        val productUnit: Unit = findProductByName(products)
        //totalPrice = Product.getAsOpt[String]("price")


        //totalPrice = findProductByName(products)
      }
    )

    orderCollection.flatMap(_.insert.one(OrderModel(BSONString(BSONObjectID.generate().stringify), "Chris123", List("Tesco Pork Bites", "Tesco Flapjacks"), Calendar.getInstance().getTime.toString(), BigDecimal(1.99))).map(_ => {}))
  }

  // Read
  def findAllOrders: Unit = {
    val findFuture: Future[List[OrderModel]] = orderCollection.flatMap(_.find(document())
      .cursor[OrderModel]()
      .collect[List](-1, Cursor.FailOnError[List[OrderModel]]()))
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.toString)
      case Failure(f) => {}
    }
  }

  def findOrderByBuyer(username: String) {
    val selector = BSONDocument("username" -> username)
    val findFuture = orderCollection.flatMap(_.find(selector).one)
    findFuture onComplete {
      case Success(orderOption) => println(orderOption.get)
      case Failure(f) => {}
    }
  }

  def deleteOrderByBuyer(username: String)  = {
    val selector = document("username" -> username)
    orderCollection.flatMap(_.delete.one(selector))
  }




}
