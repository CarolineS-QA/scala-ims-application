package com.qa.ims



import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.configuration.MongoConfiguration.customerCollection
import com.qa.ims.controller.CustomerController
import com.qa.ims.controller.CustomerController.{createCustomer, findAllCustomers, findCustomerById, findCustomerByName, findCustomerByUsername, updateCustomerByUsername}
import com.qa.ims.model.{Customer, CustomerModel}
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.{BSONDocumentReader, BSONString}
import reactivemongo.bson.BSONObjectID


object IMS {


  def main(args: Array[String]): Unit = {

    println("1. Working")


    MongoConfiguration

    //createCustomer(CustomerModel(BSONString(BSONObjectID.generate().stringify), "Chris123", "Chris", "Red", 24))


    // findCustomerById("5f6a21350100002d7167cb63")

    updateCustomerByUsername("Chris123", "Christopher", "Radford", 27)

    findAllCustomers

    findCustomerByName("Chris")

    findCustomerByUsername("Chris123")







    println("2. Working")


  }

}
