package dtos.xml

import ru.tinkoff.phobos.decoding._
import ru.tinkoff.phobos.derivation.semiauto._
import ru.tinkoff.phobos.encoding._
import ru.tinkoff.phobos.syntax._

case class TestSuiteXMLDto(
  @attr name: String,
  @attr timestamp: String,
  @attr time: Double,
  @attr file: Option[String],
  testcase: List[TestCaseXMLDto],
)

object TestSuiteXMLDto {
  implicit val elementEncoder: ElementEncoder[TestSuiteXMLDto] = deriveElementEncoder
  implicit val elementDecoder: ElementDecoder[TestSuiteXMLDto] = deriveElementDecoder
}
