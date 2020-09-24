package com.qa.ims.model

import reactivemongo.api.bson.{BSONDocument, BSONString}

case class OrderModel (
   _id: BSONString,
   username: String,
   products: BSONDocument,
   date: String,
   totalPrice: BigDecimal
)
