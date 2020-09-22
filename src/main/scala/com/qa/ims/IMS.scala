package com.qa.ims



import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.configuration.MongoConfiguration.customerCollection
import com.qa.ims.controller.CustomerController
import com.qa.ims.controller.CustomerController.{createCustomer, deleteByUsername, findAllCustomers, findCustomerById, findCustomerByName, findCustomerByUsername, updateCustomerByUsername}
import com.qa.ims.controller.ProductController.{findAllProducts, productCustomer}
import com.qa.ims.model.{Customer, CustomerModel, ProductModel}
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.{BSONDocumentReader, BSONString}
import reactivemongo.bson.BSONObjectID


object IMS {


  def main(args: Array[String]): Unit = {


    MongoConfiguration

    /// Customer CRUD

    // createCustomer(CustomerModel(BSONString(BSONObjectID.generate().stringify), "Chris123", "Chris", "Red", 24))

    // findCustomerById("5f6a21350100002d7167cb63")

    //updateCustomerByUsername("Chris123", "Christopher", "Radford", 27)

    //findAllCustomers

    //findCustomerByName("Chris")

    //findCustomerByUsername("Chris123")

    //deleteByUsername("Chris123")


    /// Product CRUD

    //productCustomer(ProductModel(BSONString(BSONObjectID.generate().stringify), "Pork Bites", "Food", BigDecimal(2.99), 1000L))

    findAllProducts






  }

}
