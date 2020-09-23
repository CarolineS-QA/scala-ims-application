package com.qa.ims.view

import com.qa.ims.controller.ProductController.{createProduct, deleteProductById, findAllProducts, findProductByCategory, findProductById, findProductByName, updateProductByName}
import com.qa.ims.model.ProductModel
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.io.StdIn.{readDouble, readLine, readLong}

object ProductView {
  def productInput() {
    val action = readLine("What would you like to do with this collection \n 1). create   2). read   3). update   4). delete\n")
    action match {
      case "create" | "1" =>
        val name = readLine("Please enter a product name: \n")
        val category = readLine("Please enter a product category: \n")
        println("Please enter the price: \n")
        val price = readDouble
        println("Please enter the inventory: \n")
        val inventory = readLong
        createProduct(ProductModel(BSONString(BSONObjectID.generate().stringify), name, category, BigDecimal(price), inventory))
      case "read" | "2" =>
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
          case _ => println("No such command, please try again")
        }
      case "update" | "3" =>
        val name = readLine("Please enter the name of the product you wish to update: \n")
        val category = readLine("Please enter a product category: \n")
        println("Please enter the price: \n")
        val price = readDouble
        println("Please enter the inventory: \n")
        val inventory = readLong
        updateProductByName(name, category, price, inventory)
      case "delete" | "4" =>
        val id = readLine("Please enter the id of the product you wish to delete? \n")
        deleteProductById(id)
      case _ => println("No such command, please try again")
    }
  }
}
