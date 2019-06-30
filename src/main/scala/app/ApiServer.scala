package app

import app.controller.UserController
import app.mysql.DDL
import app.mysql.Impl._
import com.twitter.finagle.Http
import com.twitter.finagle.mysql._
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.circe.generic.auto._
import io.finch.circe._

object ApiServer extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  lazy val userController = UserController()

  def createTables()(implicit client: Client): Future[Result] = {
    client.query(DDL.createUsersTable)
  }

  def main() {
    val server = Http.server.serve(":8080", userController.endpoints.toService)
    onExit{
      server.close()
    }
    Await.result(createTables())
    Await.ready(server)
  }

}