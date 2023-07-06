package dtos

import play.api.libs.json.{Format, Json}

case class JUnitContentDto(
  organisationId: String,
  buildId: String,
  xml: String,
)

object JUnitContentDto {
  implicit val format: Format[JUnitContentDto] = Json.format
}
