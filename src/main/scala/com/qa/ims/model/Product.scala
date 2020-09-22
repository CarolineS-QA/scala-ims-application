package com.qa.ims.model

import reactivemongo.api.bson.BSONString

case class ProductModel (
   _id: BSONString,
   username: String,
   forename: String,
   surname: String,
   age: Int
)

object Product {
  def apply(username: String, forename: String, surname: String, age: Int): CustomerModel = Customer(username, forename, surname, age)
}
