package controllers

import java.util.Calendar

import configuration.MongoConfiguration
import configuration.MongoConfiguration.{customerCollection, orderCollection}
import javax.inject.Inject
import model.CustomerModel

import scala.concurrent.{ExecutionContext, Future}
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, MessagesRequest, Request}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, JsNumber, JsObject, JsString, JsValue, Json, Reads, __}
import play.api.routing.Router.empty.routes
import reactivemongo.api.bson.{BSONObjectID, document}
import reactivemongo.api.commands.WriteResult

import scala.collection.mutable
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


class OrderController @Inject()(cc: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents  with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  MongoConfiguration

  def orderCreateForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.orderCreateForm(OrderForm.form))
  }

  def orderCreateFormAction(): Action[AnyContent] = Action.async  { implicit request =>
    val formData: OrderForm = OrderForm.form.bindFromRequest.get // Careful: BasicForm.form.bindFromRequest returns an Option
    val date = Calendar.getInstance().getTime.toString
    val id = BSONObjectID.generate().stringify


    // Total Price will go here


    val orderData = Json.obj("_id" -> id, "username" -> formData.username, "products" -> formData.products,
      "date" -> date, "totalPrice" -> formData.totalPrice)
    orderCollection.flatMap(_.insert.one(orderData)).map(lastError =>
      Ok(views.html.orderPage()))
  }

  def orderFindAll(): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[JsObject]] = orderCollection.map {
      _.find(Json.obj()).sort(Json.obj("created" -> 1)).cursor[JsObject](ReadPreference.primary)
    }
    // gather all the JsObjects in a list
    val futureOrdersList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futureOrdersJsonArray: Future[JsArray] =
      futureOrdersList.map { orders => Json.arr(orders) }

    // everything's ok! Let's reply with the array
    futureOrdersJsonArray.map { orders =>
      Ok(orders)
    }
  }

}
