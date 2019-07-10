package app

import app.controller.UserController
import app.mysql.{DDL, MySqlClientBuilder}
import com.twitter.finagle.mysql._
import com.twitter.finagle.{Http, ListeningServer}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.circe.generic.auto._
import io.finch.circe._

object ApiServer extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  implicit val mySqlClient: Client = MySqlClientBuilder.getClient

  lazy val userController: UserController = UserController()

  lazy val apiServer: ListeningServer = Http.server.serve(":8080", userController.endpoints.toService)

  def createTables()(implicit client: Client): Future[Result] = {
    client.query(DDL.createUsersTable)
  }

  def main() {
    onExit{
      apiServer.close()
    }
    Await.result(createTables())
    Await.ready(apiServer)
  }

}