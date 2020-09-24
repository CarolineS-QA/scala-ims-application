package com.qa.ims.model

import reactivemongo.api.bson.{BSONDocument, BSONElement, BSONString}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

case class OrderModel (
   _id: BSONString,
   username: String,
   products: BSONDocument,
   date: String,
   totalPrice: BigDecimal
)
