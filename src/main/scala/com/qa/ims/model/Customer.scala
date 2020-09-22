package com.qa.ims.model

import org.mongodb.scala.bson.{BsonObjectId, ObjectId}
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.bson.BSONString


case class CustomerModel (
  _id: BSONString,
  username: String,
  forename: String,
  surname: String,
  age: Int
)

object Customer {
  def apply(username: String, forename: String, surname: String, age: Int): CustomerModel = Customer(username, forename, surname, age)
}