package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json.{Json, OWrites}

case class CustomerCreateForm(username: String, forename: String, surname: String, age: Int)

object CustomerCreateForm {
  implicit val customerWrite: OWrites[CustomerCreateForm] = Json.writes[CustomerCreateForm]
  val formSet: Form[CustomerCreateForm] = Form[CustomerCreateForm](
    mapping(
      "username" -> text,
      "forename" -> text,
      "surname" -> text,
      "age" -> number
    )(CustomerCreateForm.apply)(CustomerCreateForm.unapply)
  )
}
