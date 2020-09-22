package com.qa.ims.model

import org.mongodb.scala.bson.{BsonObjectId, ObjectId}
import reactivemongo.api.bson.BSONObjectID


case class CustomerModel (
  forename: String,
  surname: String,
  age: Int
)

object Customer {
  def apply(forename: String, surname: String, age: Int): CustomerModel = Customer(forename, surname, age)
}