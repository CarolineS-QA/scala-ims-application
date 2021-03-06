package controllers

import configuration.MongoConfiguration
import configuration.MongoConfiguration.{customerCollection, productCollection}
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


class ProductController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration

  def productCreateForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.productCreateForm(ProductForm.form))
  }

  def productCreateFormAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: ProductForm = ProductForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val id = BSONObjectID.generate().stringify
    productCollection.flatMap(_.insert.one(Json.obj("_id" -> id, "name" -> formData.name,
      "category" -> formData.category, "price" -> formData.price, "inventory" -> formData.inventory)))
      .map(lastError => Ok(views.html.productPage()))
  }

  def productFindAll(): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[JsObject]] = productCollection.map {
      _.find(Json.obj()).sort(Json.obj("created" -> 1)).cursor[JsObject](ReadPreference.primary)
    }
    // gather all the JsObjects in a list
    val futureProductsList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futureProductsJsonArray: Future[JsArray] =
      futureProductsList.map { products => Json.arr(products) }

    // everything's ok! Let's reply with the array
    futureProductsJsonArray.map { products =>
      Ok(products)
    }
  }

  def findByProductName(name: String): Action[AnyContent] = Action.async {
    // let's do our query
    val cursor: Future[Cursor[JsObject]] = productCollection.map {
      // find all people with name `name`
      _.find(Json.obj("name" -> name)).
        // sort them by creation date
        sort(Json.obj("created" -> -1)).
        // perform the query and get a cursor of JsObject
        cursor[JsObject](ReadPreference.primary)
    }

    // gather all the JsObjects in a list
    val futureProductsList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futureProductsJsonArray: Future[JsArray] =
      futureProductsList.map { products => Json.arr(products) }

    // everything's ok! Let's reply with the array
    futureProductsJsonArray.map { products =>
      Ok(products)
    }
  }

  def productUpdateForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.productUpdateForm(ProductForm.form))
  }

  def productUpdateFormAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: ProductForm = ProductForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val id = formData._id
    productCollection.flatMap(_.update(Json.obj("_id" -> id), Json.obj("name" -> formData.name,
    "category" -> formData.category, "price" -> formData.price, "inventory" -> formData.inventory))
      .map(formData => Ok(views.html.productPage())))
  }

  def productDeleteForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.productDeleteForm(ProductDeleteForm.form))
  }

  def productDeleteFormAction(): Action[AnyContent] = Action.async { implicit request =>
    val formData: ProductDeleteForm = ProductDeleteForm.form.bindFromRequest.get
    val id = formData._id
    val selector = document("_id" -> id)
    productCollection.flatMap(_.remove(selector)).map(selector => Ok(views.html.productPage()))
  }
}
