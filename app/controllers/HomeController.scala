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


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration


  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def customerPage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerPage())
  }

  def productPage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.productPage())
  }
  
  def orderPage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.orderPage())
  }


}

