val playSlickVersion = "3.0.3"

val commonSettings = Seq(
  organization := "com.example",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.12.6",
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= Seq(
    guice,

    "com.typesafe.play" %% "play-slick" % playSlickVersion,
    "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,

    "mysql" % "mysql-connector-java" % "5.1.46",

    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "utf-8",
    "-explaintypes",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xcheckinit",
    "-Xfuture",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-extra-implicit",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused:implicits",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:locals",
    "-Ywarn-unused:params",
    "-Ywarn-unused:patvars",
    "-Ywarn-unused:privates",
    "-Ywarn-value-discard"
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := """PlaySlickTestExample""",
    parallelExecution in Test := false
  )
  .enablePlugins(PlayScala)
