package controllers

import play.api.data.Form
import play.api.data.Forms.{bigDecimal, longNumber, mapping, text}
import play.api.libs.json.{Json, OWrites}

case class ProductForm(name: String, category: String, price: BigDecimal, inventory: Long)


object ProductForm {
  implicit val productWrite: OWrites[ProductForm] = Json.writes[ProductForm]
  val form: Form[ProductForm] = Form(
    mapping(
      "name" -> text,
      "category" -> text,
      "price" -> bigDecimal,
      "inventory" -> longNumber
    )(ProductForm.apply)(ProductForm.unapply)
  )
}
