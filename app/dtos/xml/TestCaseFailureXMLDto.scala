package dtos.xml

import ru.tinkoff.phobos.decoding.ElementDecoder
import ru.tinkoff.phobos.derivation.semiauto.{deriveElementDecoder, deriveElementEncoder}
import ru.tinkoff.phobos.encoding.ElementEncoder
import ru.tinkoff.phobos.syntax.attr

case class TestCaseFailureXMLDto(@attr message: String)

object TestCaseFailureXMLDto {
  implicit val elementEncoder: ElementEncoder[TestCaseFailureXMLDto] = deriveElementEncoder
  implicit val elementDecoder: ElementDecoder[TestCaseFailureXMLDto] = deriveElementDecoder
}
