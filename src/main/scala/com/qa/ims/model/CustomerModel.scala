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
