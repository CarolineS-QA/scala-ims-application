package com.qa.ims.view

import java.util.Calendar

import com.qa.ims.controller.OrderController.{createOrder, deleteOrderById, findAllOrders, findOrderByBuyer, findOrderById, updateOrderById}
import com.qa.ims.model.OrderModel
import reactivemongo.api.bson.{BSONDocument, BSONElement, BSONString}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.io.StdIn.readLine

object OrderView {
  @tailrec
  def orderInput() {
    val action = readLine("What would you like to do with this collection \n 1). create   2). read   3). update   4). delete\n")
    action match {
      case "create" | "1" => orderCreateInput()
      case "read" | "2" => orderReadInput()
      case "update" | "3" => orderUpdateInput()
      case "delete" | "4" => orderDeleteInput()
      case _ =>
        println("No such command, please try again")
        orderInput()
    }
  }

  def orderCreateInput(): Unit = {
    val username = readLine("Please enter the username of the customer making the purchase? \n")

    orderProductLoop

    @tailrec
    def orderProductLoop: Any = {
      val products = readLine("Which product by name would you like to add? \n")
      val quantity = readLine("How many would you like? \n") // Will need to comment out alongside any references if I decide to implement total price
      val loop = readLine("Would you like to add another item? \n 1). y   2). n \n")

      val productList: BSONDocument = BSONDocument(products -> quantity.toInt)
      loop match {
        case "y" | "1" =>
          orderProductLoop
        case "n" | "2" =>
          createOrder(username, productList, productList.elements)
          productList
        case _ =>
          println("No such command, please try again")
          orderProductLoop
      }
    }
  }

  @tailrec
  def orderReadInput(): Unit = {
    val readBy = readLine("Which read command would you like to use? \n 1). all   2). name   3). id \n")
    readBy match {
      case "all" | "1" => findAllOrders()
      case "name" | "2" =>
        val name = readLine("Please enter the username of the buyer? \n")
        findOrderByBuyer(name)
      case "id" | "3" =>
        val id = readLine("Please enter the id of the order? \n")
        findOrderById(id)
      case _ =>
        println("No such command, please try again")
        orderReadInput()
    }
  }

  def orderUpdateInput(): Unit = {
    val id = readLine("Please enter the id of the order? \n")
    val username = readLine("Please enter the username of the customer making the purchase? \n")
    val productList = new ListBuffer[String]()
    orderProductLoop

    @tailrec
    def orderProductLoop: ListBuffer[String] = {
      val products = readLine("Which product by name would you like to add? \n")
      val quantity = readLine("How many would you like? \n") // Will need to comment out alongside any references if I decide to implement total price
      val loop = readLine("Would you like to add another item? \n 1). y   2). n \n")
      loop match {
        case "y" | "1" =>
          productList += products + " x" + quantity
          println(productList)
          orderProductLoop
        case "n" | "2" =>
          productList += products + " x" + quantity
          updateOrderById(id, username, productList, BigDecimal(1.99))
          println(productList)
          productList
        case _ =>
          println("No such command, please try again")
          orderProductLoop
      }
    }
  }

  def orderDeleteInput(): Future[WriteResult] = {
    val id = readLine("What is the id of the order you wish to delete? \n")
    deleteOrderById(id)
  }
}
