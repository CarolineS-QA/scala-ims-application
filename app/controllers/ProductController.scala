package controllers

import configuration.MongoConfiguration
import configuration.MongoConfiguration.customerCollection
import javax.inject.Inject
import model.CustomerModel

import scala.concurrent.{ExecutionContext, Future}
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, JsNumber, JsObject, JsString, JsValue, Json, Reads, __}
import reactivemongo.api.bson.document
import reactivemongo.api.commands.WriteResult

import scala.language.postfixOps
import scala.util.{Failure, Success}
// Reactive Mongo imports
import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference

import play.modules.reactivemongo.{ // ReactiveMongo Play2 plugin
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}

// BSON-JSON conversions/collection
import reactivemongo.play.json._, collection._


class ProductController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration


}
