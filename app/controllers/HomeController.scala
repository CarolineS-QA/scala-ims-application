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

  def customerForms(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerForms(CustomerForm.form))
  }

  def customerFormsPost(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: CustomerForm = CustomerForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    customerCollection.flatMap(_.insert.one(formData)).map(lastError =>
      Ok(views.html.customerPage()))
  }

  def customerFindAll(): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[JsObject]] = customerCollection.map {
      _.find(Json.obj()).sort(Json.obj("created" -> 1)).cursor[JsObject](ReadPreference.primary)
    }
    // gather all the JsObjects in a list
    val futureCustomersList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futureCustomersJsonArray: Future[JsArray] =
      futureCustomersList.map { customers => Json.arr(customers) }

    // everything's ok! Let's reply with the array
    futureCustomersJsonArray.map { customers =>
      Ok(customers)
    }

  }


  def findByUsername(username: String): Action[AnyContent] = Action.async {
    // let's do our query
    val cursor: Future[Cursor[JsObject]] = customerCollection.map {
      // find all people with name `name`
      _.find(Json.obj("username" -> username)).
        // sort them by creation date
        sort(Json.obj("created" -> -1)).
        // perform the query and get a cursor of JsObject
        cursor[JsObject](ReadPreference.primary)
    }

    // gather all the JsObjects in a list
    val futureCustomersList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futureCustomersJsonArray: Future[JsArray] =
      futureCustomersList.map { customers => Json.arr(customers) }

    // everything's ok! Let's reply with the array
    futureCustomersJsonArray.map { customers =>
      Ok(customers)
    }
  }
}

