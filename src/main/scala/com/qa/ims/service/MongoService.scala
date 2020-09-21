package com.qa.ims.service

import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

class MongoService {

  def getClient(client: String): MongoClient = {
    MongoClient(client)
  }

  def getDatabase(client: MongoClient, database: String): MongoDatabase = {
    client.getDatabase(database)
  }

  def getCollection(database: MongoDatabase, collection: String): MongoCollection[Document] = {
    database.getCollection(collection)
  }
}
