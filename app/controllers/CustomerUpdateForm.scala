package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json.{Json, OWrites}

case class CustomerUpdateForm(forename: String, surname: String, age: Int)

object CustomerUpdateForm {
  implicit val customerWrite: OWrites[CustomerUpdateForm] = Json.writes[CustomerUpdateForm]
  val form: Form[CustomerUpdateForm] = Form(
    mapping(
      "forename" -> text,
      "surname" -> text,
      "age" -> number
    )(CustomerUpdateForm.apply)(CustomerUpdateForm.unapply)
  )
}
