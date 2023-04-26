// See README.md for license details.

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "ParkDongho"

//val chiselVersion = "5.0.0-RC1"
val chiselVersion = "3.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "chisel_examples",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs"        %% "chisel3"      % "3.6.0",
      "edu.berkeley.cs"        %% "chiseltest"   % "0.6.0" % "test",
      "io.github.chiselverify" %  "chiselverify" % "0.4.0"
    ),
    scalacOptions ++= Seq(
      "-unchecked", 
      "-deprecation", 
      "-language:reflectiveCalls", 
      "-feature", 
      "-Xcheckinit", 
      "-Xfatal-warnings", 
      "-Ywarn-dead-code", 
      "-Ywarn-unused", 
      "-Ymacro-annotations",
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full),
  )


