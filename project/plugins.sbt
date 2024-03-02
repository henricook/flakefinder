addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.2")

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")

// Workaround for some nonsense scala-xml bug with play framework, ref: https://github.com/playframework/playframework/issues/11522
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always