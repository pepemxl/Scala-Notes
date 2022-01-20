import Dependencies._

ThisBuild / scalaVersion     := "2.12.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.functionalprogramming"
ThisBuild / organizationName := "functionalprogramming"

lazy val root = (project in file("."))
  .settings(
    name := "FunctionalProgramming",
    libraryDependencies += scalaTest % Test
  )

lazy val part01 = (project in file("part01"))
  .settings(
    name := "Part01",
  )

lazy val part02 = (project in file("part02"))
  .settings(
    name := "Part02",
  )

lazy val part03 = (project in file("part03"))
  .settings(
    name := "Part03",
  )

lazy val part04 = (project in file("part04"))
  .settings(
    name := "Part04",
  )
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
