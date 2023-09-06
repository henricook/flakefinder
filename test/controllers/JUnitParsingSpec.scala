package controllers

import dtos.xml.JUnitXMLDto
import models.config.DBConfig
import org.mockito.MockitoSugar
import play.api.Configuration
import play.api.test._
import play.api.test.Helpers._
import services.JUnitService
import ru.tinkoff.phobos.decoding._
import ru.tinkoff.phobos.syntax._
import ru.tinkoff.phobos.derivation.semiauto._

import scala.io.Source
import scala.util.Random
import pprint.pprintln
import repositories.TestSuiteRepository

import org.scalatest.wordspec.AnyWordSpec

/** Add your spec here. You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class JUnitParsingSpec extends AnyWordSpec with MockitoSugar {

  "JUnitParser" should {

    "parse all junit sample files" in {

      val xml1 = XmlDecoder[JUnitXMLDto].decode(
        Source.fromResource("fixtures/valid-junit-1.xml").getLines().mkString("\n")
      ) match {
        case Left(err)  =>
          println("XML Parse error => " + err)
          throw new Exception("xml 1 parsing")
        case Right(xml) => xml
      }
      val xml2 = XmlDecoder[JUnitXMLDto].decode(
        Source.fromResource("fixtures/valid-junit-2.xml").getLines().mkString("\n")
      ).getOrElse(throw new Exception("xml2 parsing"))
      val xml3 = XmlDecoder[JUnitXMLDto].decode(
        Source.fromResource("fixtures/valid-junit-3.xml").getLines().mkString("\n")
      ).getOrElse(throw new Exception("xml3 parsing"))

      println("1 = " + xml1)

      println("Final parsed objects = ")
      pprint.pprintln(xml1)
      pprint.pprintln(xml2)
      pprint.pprintln(xml3)
    }
  }
}
