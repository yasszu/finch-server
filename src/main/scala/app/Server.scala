package app

import java.net.InetSocketAddress

import app.apis.UserApi
import app.utils._
import com.twitter.app.Flag
import com.twitter.finagle.mysql._
import com.twitter.finagle.{Http, Mysql}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import com.typesafe.config._
import io.circe.generic.auto._
import io.finch.circe._

object Server extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  val conf: Config = ConfigFactory.load()
  val host: Flag[InetSocketAddress] = flag("db.host", new InetSocketAddress("localhost", 3306), "Mysql server address")
  val user: String = conf.getString("mysql.user")
  val password: String = conf.getString("mysql.password")
  val db: String = conf.getString("mysql.db")

  implicit lazy val mysqlClient: Client with Transactions = Mysql.client
    .withCredentials(user, password)
    .withDatabase(db)
    .newRichClient("%s:%d".format(host().getHostName, host().getPort))

  lazy val userApi = UserApi()

  def createTables()(implicit client: Client): Future[Result] = {
    client.query(DDL.createUsersTable)
  }

  def main() {
    val server = Http.server.serve(":8080", userApi.endpoints.toService)
    onExit{
      server.close()
    }
    Await.result(createTables())
    Await.ready(server)
  }

}