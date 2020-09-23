package com.qa.ims.service

import java.util.Calendar

import com.qa.ims.controller.CustomerController.{createCustomer, deleteByUsername, findAllCustomers, findCustomerById, findCustomerByName, updateCustomerByUsername}
import com.qa.ims.controller.OrderController.{createOrder, findAllOrders, findOrderByBuyer, findOrderById}
import com.qa.ims.controller.ProductController.{createProduct, deleteProductByName, findAllProducts, findProductByCategory, findProductById, findProductByName, updateProductByName}
import com.qa.ims.model.{CustomerModel, OrderModel, ProductModel}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.{readDouble, readInt, readLine, readLong}

object InputService {

  val input = readLine("Hello, which collection would you like to manage? \n 1). customer 2). product 3). order\n")

  if (input == "customer" || input == "1") {
    val action = readLine("What would you like to do with this collecion\n 1). create 2). read 3). update 4). delete\n")
    /// Customer CRUD
    action match {
      case "create" | "1" => {
        val username = readLine("Please enter a username: \n")
        val forename = readLine("Please enter a forename: \n")
        val surname = readLine("Please enter a surname: \n")
        println("Please enter an age: \n")
        val age = readInt()
        createCustomer(CustomerModel(BSONString(BSONObjectID.generate().stringify), username, forename, surname, age))
      }
      case "read" | "2" => {
        val readBy = readLine("Which read command would you like to use? \n 1). all 2). name 3). username 4). id \n")
        readBy match {
          case "all" | "1" => findAllCustomers
          case "name" | "2" => {
            val name = readLine("Which name would you like to search? \n")
            findCustomerByName(name)
          }
          case "username" | "3" => { // Currently throwing NoSuchElementException
            val username = readLine("Which username would you like to search? \n")
            findCustomerByName(username)
          }
          case "id" | "4" => { // Currently throwing NoSuchElementException
            val id = readLine("Which id would you like to search? \n")
            findCustomerById(id)
          }
          case _ => println("No such command, please try again")
        }
      }
      case "update" | "3" => {
        val username = readLine("Please enter the username of the customer you wish to update? \n")
        val forename = readLine("Please enter the new name of the customer: \n")
        val surname = readLine("Please enter the new name of the customer: \n")
        println("Please enter the new name of the customer: \n")
        val age = readInt()
        updateCustomerByUsername (username, forename, surname, age)
      }
      case "delete" | "4" => {
        val username = readLine("What is the username of the customer you wish to delete? \n")
        deleteByUsername(username)
      }
      case _ => println("No such command, please try again")
    }
    // findCustomerById("5f6a4ed6010000f2e1325a93") // Not working
  } else if (input == "product" || input == "2") {
    /// Product CRUD
    val action = readLine("What would you like to do with this collecion\n 1). create 2). read 3). update 4). delete\n")
    action match {
      case "create" | "1" => {
        val name = readLine("Please enter a product name: \n")
        val category = readLine("Please enter a product category: \n")
        println("Please enter the price: \n")
        val price = readDouble
        println("Please enter the inventory: \n")
        val inventory = readLong
        createProduct (ProductModel (BSONString (BSONObjectID.generate ().stringify), name, category, BigDecimal (price), inventory) )
      }
      case "read" | "2" => {
        val readBy = readLine("Which read command would you like to use? \n 1). all 2). name 3). category 4). id \n")
        readBy match {
          case "all" | "1" => findAllProducts
          case "name" | "2" => {
            val name = readLine("Which name would you like to search? \n")
            findProductByName(name)
          }
          case "category" | "3" => { // Currently throwing NoSuchElementException
            val category = readLine("Which category would you like to search? \n")
            findProductByCategory(category)
          }
          case "id" | "4" => { // Currently throwing NoSuchElementException
            val id = readLine("Which id would you like to search? \n")
            findProductById(id)
          }
          case _ => println("No such command, please try again")
        }
      }
      case "update" | "3" => {
        val name = readLine("Please enter the name of the product you wish to update: \n")
        val category = readLine("Please enter a product category: \n")
        println("Please enter the price: \n")
        val price = readDouble
        println("Please enter the inventory: \n")
        val inventory = readLong
        updateProductByName(name, category, price, inventory)
      }
      case "delete" | "4" => {
        val name = readLine("What is the name of the product you wish to delete? \n")
        deleteProductByName(name)
      }
      case _ => println("No such command, please try again")
      // findProductById("5f6a4ed6010000f2e1325a93") // Not working
    }
  } else if (input == "order" || input == "3") {
    /// Order CRUD
    val action = readLine("What would you like to do with this collecion\n 1). create 2). read 3). update 4). delete\n")
    action match {
      case "create" | "1" => {
        val username = readLine("Please enter the username of the customer making the purchase? \n")
        val productList = new ListBuffer[String]()
        orderProductLoop
        def orderProductLoop: ListBuffer[String] = {
          val products = readLine("Which product by name would you like to add? \n")
          val loop = readLine("Would you like to add another item? \n 1). y 2). n \n")
          loop match {
            case "y" | "1" => {
              productList += products
              println(productList)
              orderProductLoop
            }
            case "n" | "2" => {
              productList += products
              createOrder(OrderModel(BSONString(BSONObjectID.generate().stringify), username,
                productList, Calendar.getInstance().getTime.toString(), BigDecimal(1.99)))
              println(productList)
              productList
            }
            case _ => {
              println("No such command, please try again")
              orderProductLoop
            }
          }
        }
      }
      case "read" | "2" => {
        val readBy = readLine("Which read command would you like to use? \n 1). all 2). name 3). id \n")
        readBy match {
          case "all" | "1" => findAllOrders
          case "name" | "2" => {
            val name = readLine("Please enter the username of the buyer? \n")
            findOrderByBuyer(name)
          }
          case "id" | "3" => {
            val id = readLine("Please enter the id of the order? \n")
            findOrderById(id)
          }
          case _ => println("No such command, please try again")
        }
      }
      case "update" | "3" => {

      }
      case "delete" | "4" => {
        // deleteOrderByBuyer("Chris123") /// Pointless function as it'll clear all of the buyer's orders
      }
      case _ => println("No such command, please try again")
    }
  } else { println("Invalid Input, please try again...") }
}
