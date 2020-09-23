package com.qa.ims.view

import java.util.Calendar

import com.qa.ims.controller.CustomerController._
import com.qa.ims.controller.OrderController._
import com.qa.ims.controller.ProductController._
import com.qa.ims.model.{CustomerModel, OrderModel, ProductModel}
import com.qa.ims.view.CustomerView.customerInput
import com.qa.ims.view.OrderView.orderInput
import com.qa.ims.view.ProductView.productInput
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.StdIn.{readDouble, readInt, readLine, readLong}

object InputView {
  InputServiceLooper()
  def InputServiceLooper():Unit = {
    val input: String = readLine("Hello, which collection would you like to manage? \n 1). customer   2). product   3). order\n")

    if (input == "customer" || input == "1") {
      customerInput()
    } else if (input == "product" || input == "2") {
      productInput()
    } else if (input == "order" || input == "3") {
      orderInput()
    } else {
      println("Invalid Input, please try again...")
    }
    Thread.sleep(1000)
    fullLoop()

    @tailrec
    def fullLoop(): Any = {
      val fullLoopCheck = readLine("Is there anything else you would like to do? \n 1). y   2). n \n")
      fullLoopCheck match {
        case "y" | "1" =>
          InputServiceLooper()
        case "n" | "2" =>
          System.exit(1)
        case _ =>
          println("No such command, please try again")
          fullLoop()
      }
    }
  }
}

