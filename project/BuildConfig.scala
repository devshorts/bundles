import sbt._
import sbt.Keys._

object BuildConfig {
  object Dependencies {
    val testDeps = Seq(
      "org.scalatest" %% "scalatest" % versions.scalatest,
      "org.mockito" % "mockito-all" % versions.mockito
    ).map(_ % "test")
  }

  object Revision {
    lazy val revision = System.getProperty("revision", "SNAPSHOT")
  }

  object versions {
    val mockito = "1.10.19"
    val scalatest = "3.0.1"
  }

  def commonSettings(currentVersion: String) = {
    Seq(
      organization := "com.onoffswitch",

      version := s"${currentVersion}-${BuildConfig.Revision.revision}",

      scalaVersion := "2.12.2",

      scalacOptions ++= Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-language:experimental.macros",
        "-unchecked",
        "-Ywarn-nullary-unit",
        "-Xfatal-warnings",
        "-Xlint",
        "-Ywarn-dead-code",
        "-Xfuture"
      ),

      scalacOptions in doc ++= scalacOptions.value.filterNot(_ == "-Xfatal-warnings")
    )
  }
}
