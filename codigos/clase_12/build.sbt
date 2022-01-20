import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "clase_12",
    libraryDependencies += scalaTest % Test
  )

lazy val example_01 = (project in file("example_01"))
  .settings(
    name := "example_01",
    libraryDependencies += scalaTest % Test
  )
lazy val example_02 = (project in file("example_02"))
  .settings(
    name := "example_02",
    libraryDependencies += scalaTest % Test
  )
lazy val example_03 = (project in file("example_03"))
  .settings(
    name := "example_03",
    libraryDependencies += scalaTest % Test
  )
lazy val example_04 = (project in file("example_04"))
  .settings(
    name := "example_04",
    libraryDependencies += scalaTest % Test
  )
lazy val example_05 = (project in file("example_05"))
  .settings(
    name := "example_05",
    libraryDependencies += scalaTest % Test
  )
lazy val exercise_01 = (project in file("exercise_01"))
  .settings(
    name := "exercise_01",
    libraryDependencies += scalaTest % Test
  )
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
