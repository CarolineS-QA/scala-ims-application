package controllers


import play.api.data.Form
import play.api.data.Forms.{bigDecimal, list, longNumber, mapping, text}
import play.api.libs.json.{Json, OWrites}

case class OrderForm(username: String, products: ProductListForm, date: String, totalPrice: BigDecimal)
case class ProductListForm(name: String, quantity: Long)

object OrderForm {
  implicit val orderWrite: OWrites[OrderForm] = Json.writes[OrderForm]
  implicit val productListWrite: OWrites[ProductListForm] = Json.writes[ProductListForm]

  val form: Form[OrderForm] = Form(
    mapping(
      "username" -> text,
      "products" -> mapping(
        "name" -> text,
        "quantity" -> longNumber
      )(ProductListForm.apply)(ProductListForm.unapply),
      "date" -> text,
      "totalPrice" -> bigDecimal
    )(OrderForm.apply)(OrderForm.unapply)
  )
}

