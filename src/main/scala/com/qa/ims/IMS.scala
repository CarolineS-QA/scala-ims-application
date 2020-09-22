package com.qa.ims



import com.qa.ims.controller.CustomerController
import com.qa.ims.controller.CustomerController.{createCustomer, findCustomerByName}
import com.qa.ims.model.{Customer, CustomerModel}
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.BSONDocumentReader
import reactivemongo.bson.BSONObjectID


object IMS {



  def main(args: Array[String]): Unit = {

    println("1. Working")


    CustomerController

    //createCustomer(CustomerModel("Chris", "Red", 24))
    findCustomerByName("Chris")




    println("2. Working")


  }

}
