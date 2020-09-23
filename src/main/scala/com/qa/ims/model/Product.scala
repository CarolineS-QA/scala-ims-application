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

object Product {
  @tailrec
  def apply(name: String, category: String, price: BigDecimal, inventory: Long): ProductModel = Product(name, category, price, inventory)
}

