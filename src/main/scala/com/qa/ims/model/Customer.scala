package com.qa.ims.model

import org.mongodb.scala.bson.ObjectId
import reactivemongo.api.bson.BSONObjectID


case class CustomerModel (
  _id: BSONObjectID,
  forename: String,
  surname: String,
  age: Int
)

object Customer {
  def apply(forename: String, surname: String, age: Int): CustomerModel = Customer(forename, surname, age)
}