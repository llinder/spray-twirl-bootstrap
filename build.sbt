organization  := "com.example"

version       := "1-1.2-RC4"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
    Seq( base / "src/main/webapp" )
}

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
    val sprayVersion = "1.2-RC4"
    val akkaVersion  = "2.2.3"
    Seq(
      "io.spray"                %     "spray-can"        % sprayVersion,
      "io.spray"                %     "spray-routing"    % sprayVersion,
      "io.spray"                %     "spray-testkit"    % sprayVersion,
      "io.spray"                %%    "twirl-api"        % "0.6.2",
      "com.typesafe.akka"       %%    "akka-actor"       % akkaVersion,
      "ch.qos.logback"          %     "logback-classic"  % "1.0.13",
      "org.specs2"              %%    "specs2"           % "2.2.3" % "test",
      "org.scalatest"           %%    "scalatest"        % "2.0" % "test",
      "org.seleniumhq.selenium" %     "selenium-java"    % "2.28.0" % "test"
    )
}

seq(Revolver.settings: _*)

seq(Twirl.settings: _*)

