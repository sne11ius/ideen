package controllers

import javax.inject.Inject
import models.Thing
import play.api.mvc.Action
import play.api.mvc.Results
import services.ThingService
import play.api.mvc.Controller
import argonaut.DecodeJson
import argonaut.Parse
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.Json._

class ThingController @Inject() (thingService: ThingService) extends Controller {

  // Using argonaut
  implicit def DecodeThing: DecodeJson[Thing] =
    DecodeJson(c => for {
      id <- (c --\ "id").as[Option[Long]]
      title <- (c --\ "title").as[String]
      description <- (c --\ "description").as[String]
    } yield Thing(
      id,
      title,
      description
    ))

  implicit val locationWrites = new Writes[Thing] {
    def writes(thing: Thing) = Json.obj(
      "id" -> thing.id,
      "title" -> thing.title,
      "description" -> thing.description
    )
  }

  def list = Action.async {
    thingService.list map {
      case things => Ok(toJson(things))
    }
  }

  def single(id: Long) = Action.async {
    thingService.find(id) map {
      case Some(thing) => {
        Ok(toJson(thing))
      }
      case None => NotFound
    }
  }

  def add = Action.async { implicit request =>
    request.mediaType match {
      case Some(someType) => {
        someType.mediaSubType match {
          case "json" => {
            request.body.asJson match {
              case Some(text) => {
                val option: Option[Thing] = Parse.decodeOption[Thing](text.toString())
                Parse.decodeOption[Thing](text.toString()) match {
                  case Some(thing) => {
                    val id = thingService.save(thing)
                    id map {
                      case someId => {
                        Created.withHeaders(LOCATION -> routes.ThingController.single(someId).absoluteURL)
                      }
                    }
                  }
                  case _ => Future.successful(BadRequest)
                }
              }
              case _ => Future.successful(BadRequest)
            }
          }
        }
      }
      case _ => {
        Future.successful(BadRequest)
      }
    }
  }
}
