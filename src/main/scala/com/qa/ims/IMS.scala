package com.qa.ims



import java.time.Instant
import java.util.Calendar

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.configuration.MongoConfiguration.customerCollection
import com.qa.ims.controller.CustomerController
import com.qa.ims.controller.CustomerController.{createCustomer, deleteByUsername, findAllCustomers, findCustomerById, findCustomerByName, findCustomerByUsername, updateCustomerByUsername}
import com.qa.ims.controller.OrderController.{createOrder, deleteOrderByBuyer, findAllOrders, findOrderByBuyer}
import com.qa.ims.controller.ProductController.{createProduct, deleteProductByName, findAllProducts, findProductByCategory, findProductById, findProductByName, updateProductByName}
import com.qa.ims.model.{Customer, CustomerModel, OrderModel, ProductModel}
import org.mongodb.scala.bson.{BsonDateTime, BsonObjectId}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.{BSONDocumentReader, BSONString}
import reactivemongo.bson.BSONObjectID

import scala.io.StdIn.{readInt, readLine}


object IMS {


  def main(args: Array[String]): Unit = {

    MongoConfiguration

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
          val readBy = readLine("Which read command would you like to use? \n 1). all 2). name 3). username \n")
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
          }
        }

        // findCustomerById("5f6a21350100002d7167cb63") // Not working

      //updateCustomerByUsername ("Chris123", "Christopher", "Radford", 27)

        // findAllCustomers

        //findCustomerByName("Chris")

        //findCustomerByUsername("Chris123")

        //deleteByUsername("Chris123")

      }

    } else if (input == "product" || input == "2") {

      /// Product CRUD

      createProduct(ProductModel(BSONString(BSONObjectID.generate().stringify), "Tesco Flapjacks", "Food", BigDecimal(2.99), 1000L))

      //findAllProducts

      //findProductByName("Pork Bites")

      //findProductByCategory("Food")

      // findProductById("5f6a4ed6010000f2e1325a93") // Not working

      //updateProductByName("Pork Bites", "Food", BigDecimal(4.99), 5000L)

      //deleteProductByName("Pork Bites")
    } else if (input == "order" || input == "3") {

      /// Order CRUD

      createOrder(OrderModel(BSONString(BSONObjectID.generate().stringify), "Chris123",
        List("Tesco Flapjacks", "Tesco Pork Bites"),
        Calendar.getInstance().getTime.toString(), BigDecimal(1.99)))

      //findAllOrders

      // findOrderByBuyer("Chris123")

      // deleteOrderByBuyer("Chris123")
    } else { println("Invalid Input, please try again...") }











  }

}
