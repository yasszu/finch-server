package app

import java.net.InetSocketAddress

import app.apis.UserApi
import app.utils.DDL
import com.twitter.finagle.mysql._
import com.twitter.finagle.{Http, Mysql}
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}
import io.circe.generic.auto._
import io.finch.circe._

object Server extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  val host = flag("db.host", new InetSocketAddress("localhost", 3306), "Mysql server address")
  val userName = flag("db.user", "<user>", "Mysql user name")
  val password = flag("db.password", "<password>", "Mysql password")
  val dbName = flag("db.name", "<dbname>", "Database name")

  implicit lazy val mysqlClient: Client with Transactions = Mysql.client
    .withCredentials(userName(), password())
    .withDatabase(dbName())
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