package com.qa.ims.model

import java.util.{Calendar, Date}

import org.mongodb.scala.bson.BsonDateTime
import reactivemongo.api.bson.BSONString

case class OrderModel (
   _id: BSONString,
   date: BsonDateTime,
)

object Order {
  def apply(date: BsonDateTime): OrderModel = Order(date)
}
