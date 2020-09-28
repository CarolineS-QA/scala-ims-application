package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.libs.json.{Json, OWrites}

case class CustomerForm(username: String, forename: String, surname: String, age: Int)

object CustomerForm {
  implicit val customerWrite: OWrites[CustomerForm] = Json.writes[CustomerForm]
  val form: Form[CustomerForm] = Form(
    mapping(
      "username" -> text,
      "forename" -> text,
      "surname" -> text,
      "age" -> number
    )(CustomerForm.apply)(CustomerForm.unapply)
  )
}
