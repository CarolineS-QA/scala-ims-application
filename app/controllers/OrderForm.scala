package controllers


import play.api.data.{Form, Mapping}
import play.api.data.Forms.{bigDecimal, list, longNumber, mapping, optional, text, tuple}
import play.api.libs.json.{Json, OWrites}

case class OrderForm(username: String, products: Option[List[(String, Long)]], date: String, totalPrice: BigDecimal)
case class ProductListForm(name: String, quantity: Long)

object OrderForm {
  implicit val orderWrite: OWrites[OrderForm] = Json.writes[OrderForm]
  implicit val productListWrite: OWrites[ProductListForm] = Json.writes[ProductListForm]

  val productListForm: Mapping[(String, Long)] = tuple(
    "name" -> text,
    "quantity" -> longNumber
  )

  val form: Form[OrderForm] = Form(
    mapping(
      "username" -> text,
      "products" -> optional(list(productListForm)),
      "date" -> text,
      "totalPrice" -> bigDecimal
    )(OrderForm.apply)(OrderForm.unapply)
  )

}


//val form = Form(
//mapping(
//"username" -> text,
//"products" -> list(mapping(
//"name" -> text,
//"quantity" -> longNumber
//)(ProductListForm.apply)(ProductListForm.unapply)),
//"date" -> text,
//"totalPrice" -> bigDecimal
//)(OrderForm.apply, OrderForm.unapply)
//)