package com.qa.ims.model

import reactivemongo.api.bson.BSONString

import scala.annotation.tailrec

case class ProductModel (
   _id: BSONString,
   name: String,
   category: String,
   price: BigDecimal,
   inventory: Long
)
