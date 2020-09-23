package com.qa.ims.view

import com.qa.ims.controller.CustomerController.{createCustomer, deleteById, findAllCustomers, findCustomerById, findCustomerByName, updateCustomerByUsername}
import com.qa.ims.model.CustomerModel
import reactivemongo.api.bson.BSONString
import reactivemongo.bson.BSONObjectID

import scala.io.StdIn.{readInt, readLine}

object CustomerView {
  def customerInput() {
    val action = readLine("What would you like to do with this collection \n 1). create   2). read   3). update   4). delete\n")
    /// Customer CRUD
    action match {
      case "create" | "1" =>
        val username = readLine("Please enter a username: \n")
        val forename = readLine("Please enter a forename: \n")
        val surname = readLine("Please enter a surname: \n")
        println("Please enter an age: \n")
        val age = readInt()
        createCustomer(CustomerModel(BSONString(BSONObjectID.generate().stringify), username, forename, surname, age))
      case "read" | "2" =>
        val readBy = readLine("Which read command would you like to use? \n 1). all   2). name   3). username   4). id \n")
        readBy match {
          case "all" | "1" => findAllCustomers()
          case "name" | "2" =>
            val name = readLine("Which name would you like to search? \n")
            findCustomerByName(name)
          case "username" | "3" => // Currently throwing NoSuchElementException
            val username = readLine("Which username would you like to search? \n")
            findCustomerByName(username)
          case "id" | "4" =>
            val id = readLine("Which id would you like to search? \n")
            findCustomerById(id)
          case _ => println("No such command, please try again")
        }
      case "update" | "3" =>
        val username = readLine("Please enter the username of the customer you wish to update? \n")
        val forename = readLine("Please enter the new name of the customer: \n")
        val surname = readLine("Please enter the new name of the customer: \n")
        println("Please enter the new name of the customer: \n")
        val age = readInt()
        updateCustomerByUsername(username, forename, surname, age)
      case "delete" | "4" =>
        val id = readLine("Please enter the id of the customer you wish to delete? \n")
        deleteById(id)
      case _ => println("No such command, please try again")
    }
  }
}
