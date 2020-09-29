package model

import reactivemongo.api.bson.BSONString

case class ProductModel (
   _id: BSONString,
   name: String,
   category: String,
   price: BigDecimal,
   inventory: Long
)
