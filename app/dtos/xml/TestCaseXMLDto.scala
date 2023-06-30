package dtos.xml

import ru.tinkoff.phobos.decoding.ElementDecoder
import ru.tinkoff.phobos.derivation.semiauto.{deriveElementDecoder, deriveElementEncoder}
import ru.tinkoff.phobos.encoding.ElementEncoder
import ru.tinkoff.phobos.syntax.attr

case class TestCaseXMLDto(@attr name: String, @attr time: Double, failure: Option[TestCaseFailureXMLDto])

object TestCaseXMLDto {
  implicit val elementEncoder: ElementEncoder[TestCaseXMLDto] = deriveElementEncoder
  implicit val elementDecoder: ElementDecoder[TestCaseXMLDto] = deriveElementDecoder
}
