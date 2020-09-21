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



}
