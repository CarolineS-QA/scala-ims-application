package com.qa.ims.model

import reactivemongo.api.bson.BSONString

case class ProductModel (
   _id: BSONString,
   name: String,
   category: String,
   price: BigDecimal,
   inventory: Long
)

object Product {
  def apply(name: String, category: String, price: BigDecimal, inventory: Long): ProductModel = Product(name, category, price, inventory)
}
