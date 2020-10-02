package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.{Json, OWrites}

case class ProductDeleteForm(name: String)


object ProductDeleteForm {
  implicit val productWrite: OWrites[ProductForm] = Json.writes[ProductForm]
  val form: Form[ProductDeleteForm] = Form(
    mapping(
      "name" -> text
    )(ProductDeleteForm.apply)(ProductDeleteForm.unapply)
  )
}