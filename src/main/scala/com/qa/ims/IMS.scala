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
import com.qa.ims.service.InputService
import org.mongodb.scala.bson.{BsonDateTime, BsonObjectId}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import reactivemongo.api.bson.{BSONDocumentReader, BSONString}
import reactivemongo.bson.BSONObjectID

import scala.io.StdIn.{readDouble, readInt, readLine, readLong}


object IMS {


  def main(args: Array[String]): Unit = {

    // Configures connection to database
    MongoConfiguration

    // Implements interface and calls CRUD functions
    InputService

  }
}
