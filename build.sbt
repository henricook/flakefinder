name         := """flakefinder"""
organization := "com.henricook"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice

libraryDependencies ++= List(
  // Cats
  "org.typelevel" %% "cats-core"   % "2.10.0" withJavadoc (),
  "org.typelevel" %% "cats-effect" % "3.4.10",

  // XML Parsing
  "ru.tinkoff" %% "phobos-core" % "0.20.0",

  // Database
  "org.tpolecat"  %% "doobie-core"     % "1.0.0-RC1",
  "org.tpolecat"  %% "doobie-hikari"   % "1.0.0-RC1", // HikariCP transactor.
  "org.tpolecat"  %% "doobie-postgres" % "1.0.0-RC1", // Postgres driver 42.3.1 + type mappings.
  "org.postgresql" % "postgresql"      % "42.6.0",

  // Test
  "com.lihaoyi"            %% "pprint"             % "0.8.1"   % Test,
  "org.mockito"            %% "mockito-scala"      % "1.17.14" % Test,
  "org.scalatest"          %% "scalatest"          % "3.2.15"  % Test,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.henricook.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.henricook.binders._"
