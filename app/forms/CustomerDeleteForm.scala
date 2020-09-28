package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.{Json, OWrites}

case class CustomerDeleteForm(username: String)

object CustomerDeleteForm {
  implicit val customerWrite: OWrites[CustomerDeleteForm] = Json.writes[CustomerDeleteForm]
  val form: Form[CustomerDeleteForm] = Form(
    mapping(
      "username" -> text
    )(CustomerDeleteForm.apply)(CustomerDeleteForm.unapply)
  )
}

