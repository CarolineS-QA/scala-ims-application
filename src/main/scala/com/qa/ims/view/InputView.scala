package com.qa.ims.view

import com.qa.ims.view.CustomerView.customerInput
import com.qa.ims.view.OrderView.orderInput
import com.qa.ims.view.ProductView.productInput

import scala.annotation.tailrec
import scala.io.StdIn.readLine

object InputView {
  inputServiceLooper()
  def inputServiceLooper():Unit = {
    val input: String = readLine("Hello, which collection would you like to manage? \n 1). customer   2). product   3). order\n")

    if (input == "customer" || input == "1") {
      customerInput()
    } else if (input == "product" || input == "2") {
      productInput()
    } else if (input == "order" || input == "3") {
      orderInput()
    } else {
      println("Invalid Input, please try again...")
      inputServiceLooper()
    }
    Thread.sleep(1000)
    fullLoop()

    @tailrec
    def fullLoop(): Any = {
      val fullLoopCheck = readLine("Is there anything else you would like to do? \n 1). y   2). n \n")
      fullLoopCheck match {
        case "y" | "1" =>
          inputServiceLooper()
        case "n" | "2" =>
          System.exit(1)
        case _ =>
          println("No such command, please try again")
          fullLoop()
      }
    }
  }
}

