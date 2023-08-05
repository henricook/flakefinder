package dtos.xml

import ru.tinkoff.phobos.decoding._
import ru.tinkoff.phobos.encoding._
import ru.tinkoff.phobos.syntax._
import ru.tinkoff.phobos.derivation.semiauto._

case class JUnitXMLDto(
  testsuite: List[TestSuiteXMLDto]
)

object JUnitXMLDto {
  implicit val xmlEncoder: XmlEncoder[JUnitXMLDto] = deriveXmlEncoder("testsuites")
  implicit val xmlDecoder: XmlDecoder[JUnitXMLDto] = deriveXmlDecoder("testsuites")
}
