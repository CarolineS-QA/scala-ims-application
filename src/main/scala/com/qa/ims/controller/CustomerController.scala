package com.qa.ims.controller

import com.qa.ims.model.CustomerModel
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}


class CustomerController {

  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = mongoClient.getDatabase("DbIMS")
  val collection: MongoCollection[CustomerModel] = database.getCollection("customer")

  def insertCustomer(doc: CustomerModel): Unit = {
    collection.insertOne(doc)

  }




}
