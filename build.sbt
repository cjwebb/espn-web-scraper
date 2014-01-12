name := "epsn-web-scraper"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.3",
  "org.jsoup" % "jsoup" % "1.7.2",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.5"
)
