name := "skrushingiv"

organization := "org.skrushingiv"

scalaVersion := "2.11.8"

resolvers := Seq(
  Resolver.typesafeRepo("releases"))

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "joda-time"          % "joda-time"     % "2.8.1",
  "com.typesafe.play" %% "play-ws"       % "2.4.2",
  "com.typesafe.play" %% "play-json"     % "2.4.2",
  "com.chuusai"       %% "shapeless"     % "2.3.0",
  "org.reactivemongo" %% "reactivemongo" % "0.12.0",
  "org.specs2"        %% "specs2-core"   % "3.6.4" % "test",
  "org.specs2"        %% "specs2-mock"   % "3.6.4" % "test"
)

scalacOptions ++= Seq(
  "-feature",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

scalacOptions in Test ++= Seq("-Yrangepos")
