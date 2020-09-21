package com.qa.ims

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.model.{Customer, CustomerModel}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

object IMS {

  def main(args: Array[String]): Unit = {

    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("DbIMS")
    val collection: MongoCollection[CustomerModel] = database.getCollection("customer")

    def insertCustomer(doc: CustomerModel): Unit = {
      collection.insertOne(doc)

    }

    val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    insertCustomer(Customer(_id = null, forename = "Chris", surname = "Red", age = 24))









    println("Working")




  }

}
