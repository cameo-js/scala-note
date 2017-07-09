import sbt.addCompilerPlugin

name := "scala-note"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.2"

lazy val `scala-note` = (project in file("."))
  .dependsOn(`mastering-advanced-scala`)

lazy val `mastering-advanced-scala` = (project in file("mastering-advanced-scala"))
  .settings(commonSettings)

lazy val `scala-etc` = (project in file("scala-etc"))
  .settings(commonSettings)


lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.2",
  resolvers += Resolver.sonatypeRepo("releases"),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats" % "0.9.0",
    "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test"
  ),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

