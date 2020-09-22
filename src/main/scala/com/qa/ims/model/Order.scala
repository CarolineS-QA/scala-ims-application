package com.qa.ims.model

import java.util.{Calendar, Date}

import org.mongodb.scala.bson.BsonDateTime
import reactivemongo.api.bson.BSONString

case class OrderModel (
   _id: BSONString,
   username: String,
   date: String
)

object Order {
  def apply(username: String, date: String): OrderModel = Order(username ,date)
}
