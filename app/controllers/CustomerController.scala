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

class CustomerController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration


  def customerCreateForms(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerForms(CustomerForm.form))
  }

  def customerCreateFormsAction(): Action[AnyContent] = Action.async  { implicit request =>
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

  def customerUpdateForms(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerUpdateForms(CustomerForm.form))
  }

  def customerUpdateFormsAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: CustomerForm = CustomerForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val username = formData.username
    val forename = formData.forename
    val surname = formData.surname
    val age = formData.age
    customerCollection.flatMap(_.update.one(Json.obj("username" -> username), Json.obj("forename" -> forename, "surname" -> surname, "age" -> age))).map(lastError =>
      Ok(views.html.customerPage()))
  }

  def customerDeleteForms(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerDeleteForms(CustomerDeleteForm.form))
  }

  def customerDeleteFormsAction(): Action[AnyContent] = Action.async { implicit request =>
    val formData: CustomerDeleteForm = CustomerDeleteForm.form.bindFromRequest.get
    val username = formData.username
    customerCollection.flatMap(_.delete.one(Json.obj("username" -> username))).map(_ => Ok(views.html.customerPage()))
  }
}