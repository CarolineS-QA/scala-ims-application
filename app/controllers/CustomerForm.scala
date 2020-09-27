package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}

case class CustomerForm(username: String, forename: String, surname: String, age: Int)

object CustomerForm {
  val form: Form[CustomerForm] = Form(
    mapping(
      "username" -> text,
      "forename" -> text,
      "surname" -> text,
      "age" -> number
    )(CustomerForm.apply)(CustomerForm.unapply)
  )
}