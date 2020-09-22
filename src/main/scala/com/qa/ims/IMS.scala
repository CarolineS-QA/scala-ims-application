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


object IMS {


  def main(args: Array[String]): Unit = {


    MongoConfiguration

    /// Customer CRUD

    //CreateCustomer(CustomerModel(BSONString(BSONObjectID.generate().stringify), "Chris123", "Chris", "Red", 24))

    // findCustomerById("5f6a21350100002d7167cb63") // Not working

    //updateCustomerByUsername("Chris123", "Christopher", "Radford", 27)

    //findAllCustomers

    //findCustomerByName("Chris")

    //findCustomerByUsername("Chris123")

    //deleteByUsername("Chris123")


    /// Product CRUD

    createProduct(ProductModel(BSONString(BSONObjectID.generate().stringify), "Tesco Flapjacks", "Food", BigDecimal(2.99), 1000L))

    //findAllProducts

    //findProductByName("Pork Bites")

    //findProductByCategory("Food")

    // findProductById("5f6a4ed6010000f2e1325a93") // Not working

    //updateProductByName("Pork Bites", "Food", BigDecimal(4.99), 5000L)

    //deleteProductByName("Pork Bites")


    /// Order CRUD

    createOrder(OrderModel(BSONString(BSONObjectID.generate().stringify), "Chris123", List("Tesco Pork Bites", "Tesco Flapjacks"), "", BigDecimal(0)))

    //findAllOrders

    // findOrderByBuyer("Chris123")

    // deleteOrderByBuyer("Chris123")












  }

}
