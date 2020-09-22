package com.qa.ims.controller

import com.qa.ims.configuration.MongoConfiguration.{orderCollection, orderWriter}
import com.qa.ims.model.OrderModel

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object OrderController {

  // Create
  def createOrder(order: OrderModel): Future[Unit] =
    orderCollection.flatMap(_.insert.one(order).map(_ => {}))




}
