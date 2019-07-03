package app.mysql

import java.net.InetSocketAddress

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._
import com.typesafe.config._

trait MySqlClient {

  val conf: Config
  val host: String
  val port: Int
  val user: String
  val password: String
  val db: String

  lazy val url = new InetSocketAddress(host, port)

  implicit lazy val mysqlClient: Client with Transactions = Mysql.client
    .withCredentials(user, password)
    .withDatabase(db)
    .newRichClient("%s:%d".format(url.getHostName, url.getPort))

}