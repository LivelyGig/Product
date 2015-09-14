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
  jsDependencies += "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React",
  jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "jquery.js" commonJSName "JQuery",
  jsDependencies += "org.webjars" % "bootstrap" % "3.3.5"  / "bootstrap.js" commonJSName "Bootstrap"
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

lazy val webAppJS = webAppCrossProject.js
lazy val webAppJVM = webAppCrossProject.jvm.dependsOn(api).settings(
  (resources in Compile) += (fastOptJS in (webAppJS, Compile)).value.data 
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
