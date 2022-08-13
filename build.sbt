ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "AnimeQuotes",
    scalaVersion := "3.1.3",
      libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.19",
      "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.19",
      "com.typesafe.akka" % "akka-http_2.13" % "10.2.9",
      "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.9")
  )
