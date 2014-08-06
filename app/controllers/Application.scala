package controllers

import play.api._
import play.api.mvc._
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {
  private val logger = Logger(getClass())

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def nonblocking = Action.async {
    val loooongTimeTaskFuture = Future {
      List.range(0,10).foreach { i => 
        Thread.sleep(1000)
        logger.info("Count: " +  i)
      }
    }
    val dummyFuture = Future { "Dummy Future" }

    dummyFuture.map(Ok(_))
  }

  def blocking = Action.async {
    val loooongTimeTaskFuture = Future {
      List.range(0,10).foreach { i =>
        Thread.sleep(1000)
        logger.info("Count: " +  i)
      }
    }
    loooongTimeTaskFuture.map(_ => Ok("Finish a looooong task."))
  }
}

