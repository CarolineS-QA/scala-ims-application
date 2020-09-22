package com.qa.ims

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.controller.CustomerController.insertCustomer
import com.qa.ims.model.{Customer, CustomerModel}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

object IMS {

  def main(args: Array[String]): Unit = {

    println("1. Working")

    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("DbIMS")
    val collection: MongoCollection[CustomerModel] = database.getCollection("customer")

    println("2. Working")



    println("3. Working")


    insertCustomer(Customer(_id = 0, forename = "Chris", surname = "Red", age = 24))


    println("4. Working")

    mongoClient.close()

    println("5. Working")

  }

}
