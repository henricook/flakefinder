package services

import cats.effect.IO
import dtos.xml.{JUnitXMLDto, TestSuiteXMLDto}
import play.api.{Configuration, Logger}

import javax.inject.Inject
import cats.implicits._
import doobie.implicits._
import dtos.persistence.TestSummaryDto
import models.config.DBConfig
import repositories.TestSuiteRepository

import javax.inject.Singleton

@Singleton
case class JUnitService @Inject() (testSuiteRepo: TestSuiteRepository, config: Configuration) {

  val dbConfig = config.get[DBConfig]("db")

  val logger: Logger = Logger(this.getClass())

  val xa = testSuiteRepo.xa

  def load(organisationId: String, buildId: String, xml: JUnitXMLDto): IO[Unit] = {
    logger.info(s"Parsing XML for organisationId $organisationId / buildId: $buildId")

    // Filter out suites with 0 tests
    val rootTestSuite =
      xml.testsuite.filter(_.testcase.size === 0) // This contains the name of the overall feature file
    val rootFeatureFileName = rootTestSuite.headOption.flatMap(_.file).getOrElse("Unknown").split("/").last
    val filteredTestSuites  = xml.testsuite.filter(_.testcase.size =!= 0)

    val result = testSuiteRepo.load(
      organisationId,
      buildId,
      rootFeatureFileName,
      xml.copy(testsuite = filteredTestSuites),
    ).transact(xa)

    logger.info(s"Done parsing XML for organisationId $organisationId / buildId: $buildId")

    result
  }

  def getSummary(organisationId: String): IO[List[TestSummaryDto]] = {
    testSuiteRepo.getSummary(organisationId).transact(xa)
  }
}
