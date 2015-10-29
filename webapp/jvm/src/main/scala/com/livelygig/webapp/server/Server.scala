package com.livelygig.webapp.server
import com.livelygig.webapp.shared._
import upickle._
import spray.routing.SimpleRoutingApp
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext.Implicits.global
import spray.http.{MediaTypes, HttpEntity}
import com.livelygig.api._
import java.io._


object AutowireServer extends autowire.Server[String, upickle.Reader, upickle.Writer]{
  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}

object Server extends SimpleRoutingApp {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    startServer("0.0.0.0", port = 8080) {
      get{
        pathSingleSlash {
          getFromResource("web/index.html")
        } ~
        getFromResourceDirectory("web")
      } ~
      post {
        path("api" / Segments){ s =>
          extract(_.request.entity.asString) { e =>
            complete {
              AutowireServer.route[Api](ApiService)(
                autowire.Core.Request(s, upickle.read[Map[String, String]](e))
              )
            }
          }
        }
      }
    }
  }
  

}

object ApiService extends Api{
  val sharedList = new SharedList()
  
  def addItem(item: String): Seq[String] = {
    sharedList.addItem(item)
  }
  
  def removeItem(item: String): Seq[String] = {
    sharedList.removeItem(item)
  }
  
  def list(): Seq[String] = {
    sharedList.getItems()
  }
}
