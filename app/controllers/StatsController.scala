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
class StatsController @Inject() (val controllerComponents: ControllerComponents, junitService: JUnitService)
    extends BaseController {

  def getSummary(organisationId: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val result = junitService.getSummary(organisationId)

    result.unsafeToFuture()(implicits.global).map { testsSummary =>
      Ok(
        views.html.stats(organisationId, testsSummary)
      )
    }
  }
}
