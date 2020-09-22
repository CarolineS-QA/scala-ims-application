package com.qa.ims.model

import java.util.{Calendar, Date}

import org.mongodb.scala.bson.BsonDateTime
import reactivemongo.api.bson.BSONString

case class OrderModel (
   _id: BSONString,
   username: String,
   products: List[String],
   date: String

)

object Order {
  def apply(username: String, products: List[String], date: String): OrderModel = Order(username, products, date)
}
