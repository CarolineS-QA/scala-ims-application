package com.qa.ims.model

import reactivemongo.api.bson.BSONString

import scala.annotation.tailrec


case class CustomerModel (
  _id: BSONString,
  username: String,
  forename: String,
  surname: String,
  age: Int
)

object Customer {
  @tailrec
  def apply(username: String, forename: String, surname: String, age: Int): CustomerModel = Customer(username, forename, surname, age)
}