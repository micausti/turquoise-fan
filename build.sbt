ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "turquoise-fan"
  )

// Doodle is currently published for Scala 2.13 and Scala 3
libraryDependencies += "org.creativescala" %% "doodle" % "0.11.2"
