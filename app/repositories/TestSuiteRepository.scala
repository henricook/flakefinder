package repositories

import cats.effect.IO
import doobie.ConnectionIO
import doobie.util.transactor.Transactor
import dtos.xml.JUnitXMLDto
import models.config.DBConfig
import play.api.Configuration
import doobie.implicits._
import cats.implicits._
import doobie.util.meta.Meta
import doobie.util.update.Update
import doobie.postgres.implicits._
import dtos.persistence.{TestCaseDto, TestSummaryDto}

import java.time.Instant
import javax.inject.Inject

case class TestSuiteRepository @Inject() (config: Configuration) {

  val dbConfig = config.get[DBConfig]("db")

  val xa = Transactor.fromDriverManager[IO](
    driver = "org.postgresql.Driver",       // driver classname
    url    = s"jdbc:postgresql:${dbConfig.host}:5432/flakefinder", // connect URL (driver-specific)
    user   = dbConfig.username,             // user
    pass   = dbConfig.password,             // password
  )

  def load(organisationId: String, buildId: String, xml: JUnitXMLDto): ConnectionIO[Unit] = {
    val sql = """INSERT INTO test_cases (organisation_id, suite_name, timestamp, run_time, build_id, test_name, test_time, test_pass, failure_message) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""

    val dtos = TestCaseDto.fromModel(xml, organisationId, buildId)

    Update[TestCaseDto](sql).updateMany(dtos).void
  }

  def getSummary(organisationId: String): ConnectionIO[List[TestSummaryDto]] = {
      sql""" SELECT test_name,
             COUNT(CASE WHEN test_pass=true THEN 1 END) AS passes,
             COUNT(CASE WHEN test_pass=false THEN 1 END) AS failures,
             COUNT(DISTINCT(build_id)) AS unique_builds,
             failure_message AS most_common_failure_message
          FROM test_cases
          WHERE organisation_id=$organisationId
          GROUP BY test_name, failure_message
          ORDER BY failures DESC, unique_builds, failure_message DESC""".query[TestSummaryDto].to[List]
  }
}