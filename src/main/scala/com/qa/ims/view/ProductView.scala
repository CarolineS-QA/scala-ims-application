package com.qa.ims.view

import com.qa.ims.controller.ProductController.{createProduct, deleteProductById, findAllProducts, findProductByCategory, findProductById, findProductByName, updateProductByName}
import com.qa.ims.model.ProductModel
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.io.StdIn.{readDouble, readLine, readLong}

object ProductView {
  @tailrec
  def productInput() {
    val action = readLine("What would you like to do with this collection \n 1). create   2). read   3). update   4). delete\n")
    action match {
      case "create" | "1" => productCreateInput()
      case "read" | "2" => productReadInput()
      case "update" | "3" => productUpdateInput()
      case "delete" | "4" => productDeleteInput()
      case _ =>
        println("No such command, please try again")
        productInput()
    }
  }

  def productCreateInput(): Future[Unit] = {
    val name = readLine("Please enter a product name: \n")
    val category = readLine("Please enter a product category: \n")
    println("Please enter the price: ")
    val price = readDouble
    println("Please enter the inventory: ")
    val inventory = readLong
    createProduct(ProductModel(BSONString(BSONObjectID.generate().stringify), name, category, BigDecimal(price), inventory))
  }

  @tailrec
  def productReadInput(): Unit = {
    val readBy = readLine("Which read command would you like to use? \n 1). all   2). name   3). category   4). id \n")
    readBy match {
      case "all" | "1" => findAllProducts()
      case "name" | "2" =>
        val name = readLine("Which name would you like to search? \n")
        findProductByName(name)
      case "category" | "3" =>
        val category = readLine("Which category would you like to search? \n")
        findProductByCategory(category)
      case "id" | "4" =>
        val id = readLine("Which id would you like to search? \n")
        findProductById(id)
      case _ =>
        println("No such command, please try again")
        productReadInput()
    }
  }

  def productUpdateInput(): Unit = {
    val name = readLine("Please enter the name of the product you wish to update: \n")
    val category = readLine("Please enter a product category: \n")
    println("Please enter the price: ")
    val price = readDouble
    println("Please enter the inventory: ")
    val inventory = readLong
    updateProductByName(name, category, price, inventory)
  }

  def productDeleteInput(): Unit = {
    val id = readLine("Please enter the id of the product you wish to delete? \n")
    deleteProductById(id)
  }

}
