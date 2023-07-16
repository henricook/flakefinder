package dtos.persistence

import dtos.xml.JUnitXMLDto

import java.time.Instant

case class TestCaseDto(
  organisation_id: String,
  suite_name: String,
  feature_file_name: String,
  timestamp: Instant,
  run_time: Double,
  build_id: String,
  test_name: String,
  test_time: Double,
  test_pass: Boolean,
  failure_message: Option[String],
)

object TestCaseDto {

  private def sanitiseTimestamp(timestamp: String) = if (timestamp.endsWith("Z")) timestamp else timestamp + "Z"

  def fromModel(
    xml: JUnitXMLDto,
    organisationId: String,
    buildId: String,
    featureFileName: String,
  ): List[TestCaseDto] = {
    xml.testsuite.flatMap { testSuite =>
      testSuite.testcase.map { testCase =>
        TestCaseDto(
          organisation_id   = organisationId,
          suite_name        = testSuite.name,
          feature_file_name = featureFileName,
          timestamp         = Instant.parse(sanitiseTimestamp(testSuite.timestamp)),
          run_time          = testSuite.time,
          build_id          = buildId,
          test_name         = testCase.name,
          test_time         = testCase.time,
          test_pass         = testCase.failure.isEmpty,
          failure_message   = testCase.failure.map(_.message),
        )
      }
    }
  }
}
