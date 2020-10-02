package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.{Json, OWrites}

case class OrderDeleteForm(_id: String)


object OrderDeleteForm {
  implicit val orderWrite: OWrites[OrderDeleteForm] = Json.writes[OrderDeleteForm]
  val form: Form[OrderDeleteForm] = Form(
    mapping(
      "_id" -> text
    )(OrderDeleteForm.apply)(OrderDeleteForm.unapply)
  )
}
