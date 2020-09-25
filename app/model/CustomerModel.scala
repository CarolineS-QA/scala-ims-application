package model

import play.api.libs.json.{Json, Writes}
import reactivemongo.api.bson.BSONString

case class CustomerModel (
  _id: BSONString,
  username: String,
  forename: String,
  surname: String,
  age: Int
)

