package com.qa.ims.model

import java.util.{Calendar, Date}

import org.mongodb.scala.bson.BsonDateTime
import reactivemongo.api.bson.BSONString

case class OrderModel (
   _id: BSONString,
   date: String,
)

object Order {
  def apply(date: String): OrderModel = Order(date)
}
