package com.qa.ims.configuration

import java.util.concurrent.TimeUnit

import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{Await, ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection, MongoConnectionOptions}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration

object MongoConfiguration {

  val uri: String = "mongodb://localhost:27017"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("DbIMS")

  def connection7(driver: AsyncDriver): Future[MongoConnection] = for {
    parsedUri <- MongoConnection.fromString(uri)
    con <- driver.connect(parsedUri)
  } yield con


  def troubleshootAuth() = {
    val strictUri = "mongodb://localhost:27017"
    val dbname = "DbIMS"
    val user = "root"
    val pass = "root"
    val driver = AsyncDriver()

    driver.connect(strictUri).flatMap {
      _.authenticate(dbname, user, pass)
    }.onComplete {
      case res => println(s"Auth: $res")
    }
  }

  troubleshootAuth()

  println(troubleshootAuth)


  def dbFromConnection(connection: MongoConnection): Future[BSONCollection] =
    connection.database("DbIMS").
      map(_.collection("customers"))




  val document1 = BSONDocument(
    "firstName" -> "Chris",
    "lastName" -> "Red",
    "age" -> 24)

  def testInsert(personColl: BSONCollection) = {
    val future2 = personColl.insert.one(document1)

    future2.onComplete {
      case Failure(e) => throw e
      case Success(writeResult) => {
        println(s"successfully inserted document: $writeResult")
      }
    }
  }






}
