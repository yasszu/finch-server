name := "finch-server"

organization := "yasszu"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-mysql" % "17.12.0",
  "com.twitter" %% "twitter-server" % "17.12.0",
  "com.github.finagle" %% "finch-core" % "0.16.0-M5",
  "com.github.finagle" %% "finch-circe" % "0.16.0-M5",
  "io.circe" %% "circe-generic" % "0.9.0-M2"
)

mainClass in assembly := Some("app.Server")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
