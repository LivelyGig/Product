// Turn this project into a Scala.js project by importing these settings

import sbt.Keys._
import spray.revolver.AppProcess
import spray.revolver.RevolverPlugin.Revolver

lazy val webAppRoot = project.in(file("webapp")).
  aggregate(webAppJS, webAppJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

  
val webAppCrossProject = crossProject.in(file("webapp")).settings(
  
  version := "0.1-SNAPSHOT",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "upickle" % "0.2.6",
    "com.lihaoyi" %%% "autowire" % "0.2.4",
    "com.lihaoyi" %%% "scalatags" % "0.4.5"
  )
).jsSettings(
  name := "Client",
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0",
    "com.github.japgolly.scalajs-react" %%% "core" % "0.9.2",
    "org.webjars" % "bootstrap" % "3.3.5"
  ),
  jsDependencies ++= Seq (
    "org.webjars" % "react" % "0.13.3" / "react-with-addons.js" commonJSName "React",
    "org.webjars" % "jquery" % "2.1.4" / "jquery.js" commonJSName "JQuery",
    "org.webjars" % "bootstrap" % "3.3.5"  / "bootstrap.js" commonJSName "Bootstrap"
  ) 
).jvmSettings(
  Revolver.settings:_*
).jvmSettings(
  name := "Server",
  scalaVersion := "2.10.4",
  libraryDependencies ++= Seq(
    "io.spray" %% "spray-can" % "1.3.1",
    "io.spray" %% "spray-routing" % "1.3.1",
    "com.typesafe.akka" %% "akka-actor" % "2.3.2",
    "org.webjars" % "bootstrap" % "3.2.0"
  )
)

// The following is taken from ochrons scalajs-spa-tutorial:
// configure a specific directory for scalajs output

val scalajsOutputDir = Def.settingKey[File]("scala.js output directory")

// make all JS builds use the output dir defined later
lazy val js2jvmSettings = Seq(fastOptJS, fullOptJS, packageJSDependencies) map { packageJSKey =>
  crossTarget in(webAppJS, Compile, packageJSKey) := scalajsOutputDir.value
}

lazy val webAppJS = webAppCrossProject.js.settings(
  fastOptJS in Compile := {
    // make a copy of the produced JS-file (and source maps) under the spaJS project as well,
    // because the original goes under the spaJVM project
    // NOTE: this is only done for fastOptJS, not for fullOptJS
    val base = (fastOptJS in Compile).value
    IO.copyFile(base.data, (classDirectory in Compile).value / "web" / "js" / base.data.getName)
    IO.copyFile(base.data, (classDirectory in Compile).value / "web" / "js" / (base.data.getName + ".map"))
    base
  }
)


lazy val webAppJVM = webAppCrossProject.jvm.dependsOn(api).settings(js2jvmSettings: _*).settings(
  // scala.js output is directed under "web/js" dir in the spaJVM project
  scalajsOutputDir := (classDirectory in Compile).value / "web" / "js",
  // reStart depends on running fastOptJS on the JS project
  Revolver.reStart <<= Revolver.reStart dependsOn (fastOptJS in(webAppJS, Compile))
)

lazy val api = project.in(file("api")).settings(
  scalaVersion := "2.10.0",
  resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io/",
    "json4s repo" at "http://repo.scala-sbt.org/scalasbt/repo/",
    "biosim repo" at "http://biosimrepomirror.googlecode.com/svn/trunk/",
    "basex repo" at "http://files.basex.org/maven/",
    "basex-xqj repo" at "http://xqj.net/maven/"
  ),
  //resolvers += Resolver.sftp("protegra repo", "ftp://ftp.protegra.com/") as("ptg2certanonftp", "#Pdsizgr8!"),
  libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-native" % "3.2.7",
    "org.json4s" %% "json4s-jackson" % "3.2.7",
    "com.biosimilarity.lift" % "specialK" % "1.1.8.0",
    //"com.protegra-ati" % "agentservices-store-ia" % "1.9.2-SNAPSHOT",
    "com.rabbitmq" % "amqp-client" % "2.6.1",
    "it.unibo.alice.tuprolog" % "tuprolog" % "2.1.1",
    "com.thoughtworks.xstream" % "xstream" % "1.4.2",
    "org.mongodb" %% "casbah" % "2.5.0",
    "org.basex" % "basex-api" % "7.5",
    "biz.source_code" % "base64coder" % "2010-09-21"
  )  
      
)
