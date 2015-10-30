package com.livelygig.webapp.client
import com.livelygig.webapp.shared._
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import scala.util.Random
import scala.concurrent.Future
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scalatags.JsDom.all._
import upickle._
import autowire._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._

object Client extends autowire.Client[String, upickle.Reader, upickle.Writer]{
  override def doCall(req: Request): Future[String] = {
    dom.ext.Ajax.post(
      url = "/api/" + req.path.mkString("/"),
      data = upickle.write(req.args)
    ).map(_.responseText)
  }

  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}


@JSExport
object ClientMain { 
  
  @JSExport
  def main(): Unit = {
  
    val TodoList = ReactComponentB[Seq[String]]("TodoList")
      .render(props => {
        def createItem(itemText: String) = <.li(itemText)
        <.ul(props map createItem)
      })
      .build
      
    case class State(items: Seq[String], text: String)
    
    class Backend($: BackendScope[Unit, State]) {
    
      def updateList() = {
        Client[Api].list().call().foreach { items =>
          $.modState(s => State(items, ""))
        }
      }
      def onChange(e: ReactEventI) =
        $.modState(_.copy(text = e.target.value))
        
      def handleAdd(e: ReactEventI) = {
        e.preventDefault()
        Client[Api].addItem($.state.text).call().foreach { items =>
          $.modState(s => State(items, ""))
        
        }
      }
    }

    val ListApp = ReactComponentB[Unit]("ListApp")
      .initialState(State(Nil, ""))
      .backend(new Backend(_))
      .render((_,S,B) =>
        <.div(
          <.h3("TODO"),
          TodoList(S.items),
          <.form(^.onSubmit ==> B.handleAdd,
            <.input(^.onChange ==> B.onChange, ^.value := S.text),
            <.button("Add #", S.items.length + 1)
          )
        )
      ).componentDidMount(_.backend.updateList())
      .buildU

    React.render(ListApp(), dom.document.body)
  }
  

}
