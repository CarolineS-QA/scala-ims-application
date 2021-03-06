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
import reactivemongo.api.bson.{BSONObjectID, document}
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.compat.bson2json.fromDocumentWriter

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


class CustomerController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration


  def customerCreateForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerCreateForm(CustomerForm.form))
  }

  def customerCreateFormAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: CustomerForm = CustomerForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val id = BSONObjectID.generate().stringify
    customerCollection.flatMap(_.insert.one(Json.obj("_id" -> id, "username" -> formData.username,
      "forename" -> formData.forename, "surname" -> formData.surname, "age" -> formData.age))).map(lastError =>
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

  def customerUpdateForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerUpdateForm(CustomerForm.form))
  }

  def customerUpdateFormAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: CustomerForm = CustomerForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val username = formData.username
    val id = formData._id
    customerCollection.flatMap(_.update(Json.obj("username" -> username),Json.obj(
    "username" -> username, "forename" -> formData.forename, "surname" -> formData.surname, "age" -> formData.age))
      .map(formData => Ok(views.html.customerPage())))
  }

  def customerDeleteForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.customerDeleteForm(CustomerDeleteForm.form))
  }

  def customerDeleteFormAction(): Action[AnyContent] = Action.async { implicit request =>
    val formData: CustomerDeleteForm = CustomerDeleteForm.form.bindFromRequest.get
    val id = formData._id
    val selector = document("_id" -> id)
    customerCollection.flatMap(_.remove(selector)).map(selector => Ok(views.html.customerPage()))
  }
}