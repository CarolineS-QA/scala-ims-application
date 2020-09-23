package com.qa.ims.model

import java.util.{Calendar, Date}

import org.mongodb.scala.bson.BsonDateTime
import reactivemongo.api.bson.BSONString

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

case class OrderModel (
   _id: BSONString,
   username: String,
   products: ListBuffer[String],
   date: String,
   totalPrice: BigDecimal
)

object Order {
  @tailrec
  def apply(username: String, products: ListBuffer[String], date: String, totalPrice: BigDecimal): OrderModel = Order(username, products, date, totalPrice)
}
