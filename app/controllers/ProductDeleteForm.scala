package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.{Json, OWrites}

case class ProductDeleteForm(_id: String)


object ProductDeleteForm {
  implicit val productWrite: OWrites[ProductForm] = Json.writes[ProductForm]
  val form: Form[ProductDeleteForm] = Form(
    mapping(
      "_id" -> text,
    )(ProductDeleteForm.apply)(ProductDeleteForm.unapply)
  )
}