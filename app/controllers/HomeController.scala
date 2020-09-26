package controllers

import configuration.MongoConfiguration
import configuration.MongoConfiguration.customerCollection
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, JsNumber, JsObject, JsString, JsValue, Json, Reads, __}


import scala.language.postfixOps
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
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

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


  def create(username: String, forename: String, surname: String, age: Int): Action[AnyContent] = Action.async {
    val json = Json.obj(
      "username" -> username,
      "forename" -> forename,
      "surname" -> surname,
      "age" -> age,
      "created" -> new java.util.Date().getTime())

    customerCollection.flatMap(_.insert.one(json)).map(lastError =>
      Ok("Mongo LastError: %s".format(lastError)))
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    import play.api.libs.json.Reads._
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert.one() with this JsValue.
     * (insert.one() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    val transformer: Reads[JsObject] =
      Reads.jsPickBranch[JsString](__ \ "username") and
        Reads.jsPickBranch[JsString](__ \ "forename") and
        Reads.jsPickBranch[JsString](__ \ "surname") and
        Reads.jsPickBranch[JsNumber](__ \ "age") reduce

    request.body.transform(transformer).map { result =>
      customerCollection.flatMap(_.insert.one(result)).map { lastError =>
        println(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid JSON")))
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
    val futurePersonsList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](-1, Cursor.FailOnError[List[JsObject]]()))

    // transform the list into a JsArray
    val futurePersonsJsonArray: Future[JsArray] =
      futurePersonsList.map { persons => Json.arr(persons) }

    // everything's ok! Let's reply with the array
    futurePersonsJsonArray.map { persons =>
      Ok(persons)
    }
  }
}

