package app

import java.net.InetSocketAddress

import app.controller.UserController
import app.util._
import com.twitter.finagle.mysql._
import com.twitter.finagle.{Http, Mysql}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import com.typesafe.config._
import io.circe.generic.auto._
import io.finch.circe._

object ApiServer extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  val conf: Config = ConfigFactory.load()
  val host: String = conf.getString("mysql.host")
  val port: Int = conf.getInt("mysql.port")
  val user: String = conf.getString("mysql.user")
  val password: String = conf.getString("mysql.password")
  val db: String = conf.getString("mysql.db")

  lazy val url = new InetSocketAddress(host, port)

  implicit lazy val mysqlClient: Client with Transactions = Mysql.client
    .withCredentials(user, password)
    .withDatabase(db)
    .newRichClient("%s:%d".format(url.getHostName, url.getPort))

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