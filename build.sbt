scalaVersion := "2.12.13"

scalacOptions ++= Seq(
  "-feature",
  "-language:reflectiveCalls",
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases")
)

// Chisel 3.5
addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.0" cross CrossVersion.full)
libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.5.0"
libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.5.0"

// Scaladoc
//lazy val copyDocAssetsTask = taskKey[Unit]("Copy doc assets")
//copyDocAssetsTask := {
//  println("Copying doc assets")
//  val sourceDir = file("./src/main/doc-resources")
//  val targetDir = (target in (Compile, doc)).value
//  IO.copyDirectory(sourceDir, targetDir)
//}
//copyDocAssetsTask := (copyDocAssetsTask triggeredBy (doc in Compile)).value
