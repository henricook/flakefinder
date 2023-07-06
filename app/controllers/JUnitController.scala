package controllers

import cats.effect.unsafe.implicits
import dtos.JUnitContentDto
import dtos.xml.JUnitXMLDto
import play.api.mvc._
import ru.tinkoff.phobos.decoding._
import services.JUnitService

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class JUnitController @Inject() (val controllerComponents: ControllerComponents, junitService: JUnitService)
    extends BaseController {

  def load(): Action[JUnitContentDto] =
    Action.async(parse.json[JUnitContentDto]) { implicit request: Request[JUnitContentDto] =>
      XmlDecoder[JUnitXMLDto].decode(request.body.xml) match {
        case Left(err)  => throw new Exception(s"Unexpected error: expected Right(...) but got Left($err)")
        case Right(xml) =>
          val result = junitService.load(request.body.organisationId, request.body.buildId, xml)

          result.unsafeToFuture()(implicits.global).map { _ =>
            Ok("> Loaded")
          }
      }
    }
}
