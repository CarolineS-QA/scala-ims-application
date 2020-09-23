package com.qa.ims.view

import java.util.Calendar

import com.qa.ims.controller.OrderController.{createOrder, deleteOrderById, findAllOrders, findOrderByBuyer, findOrderById, updateOrderById}
import com.qa.ims.model.OrderModel
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine

object OrderView {
  def orderInput() {
    val action = readLine("What would you like to do with this collection \n 1). create   2). read   3). update   4). delete\n")
    action match {
      case "create" | "1" =>
        val username = readLine("Please enter the username of the customer making the purchase? \n")
        val productList = new ListBuffer[String]()
        orderProductLoop

        @tailrec
        def orderProductLoop: ListBuffer[String] = {
          val products = readLine("Which product by name would you like to add? \n")
          val loop = readLine("Would you like to add another item? \n 1). y   2). n \n")
          loop match {
            case "y" | "1" =>
              productList += products
              println(productList)
              orderProductLoop
            case "n" | "2" =>
              productList += products
              createOrder(OrderModel(BSONString(BSONObjectID.generate().stringify), username,
                productList, Calendar.getInstance().getTime.toString, BigDecimal(1.99)))
              println(productList)
              productList
            case _ =>
              println("No such command, please try again")
              orderProductLoop
          }
        }
      case "read" | "2" =>
        val readBy = readLine("Which read command would you like to use? \n 1). all   2). name   3). id \n")
        readBy match {
          case "all" | "1" => findAllOrders()
          case "name" | "2" =>
            val name = readLine("Please enter the username of the buyer? \n")
            findOrderByBuyer(name)
          case "id" | "3" =>
            val id = readLine("Please enter the id of the order? \n")
            findOrderById(id)
          case _ => println("No such command, please try again")
        }
      case "update" | "3" =>
        val id = readLine("Please enter the id of the order? \n")
        val username = readLine("Please enter the username of the customer making the purchase? \n")
        val productList = new ListBuffer[String]()
        orderProductLoop

        @tailrec
        def orderProductLoop: ListBuffer[String] = {
          val products = readLine("Which product by name would you like to add? \n")
          val loop = readLine("Would you like to add another item? \n 1). y   2). n \n")
          loop match {
            case "y" | "1" =>
              productList += products
              println(productList)
              orderProductLoop
            case "n" | "2" =>
              productList += products
              updateOrderById(id, username, productList, BigDecimal(1.99))
              println(productList)
              productList
            case _ =>
              println("No such command, please try again")
              orderProductLoop

          }
        }
      case "delete" | "4" =>
        val id = readLine("What is the id of the order you wish to delete? \n")
        deleteOrderById(id)
      case _ => println("No such command, please try again")
    }
  }
}
