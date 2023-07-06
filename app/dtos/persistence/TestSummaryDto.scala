package dtos.persistence

import dtos.xml.JUnitXMLDto

import java.time.Instant

case class TestSummaryDto(
  test_name: String,
  feature_file_name: String,
  passes: Int,
  failures: Int,
  unique_builds: Int,
  average_run_time_seconds: Double,
  last_run_time_seconds: Double,
  most_common_failure_message: Option[String],
)
