package app.mysql

import java.net.InetSocketAddress

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._
import com.typesafe.config._

trait MySqlClientBuilder {

  val conf: Config
  val host: String
  val port: Int
  val user: String
  val password: String
  val db: String

  lazy val url = new InetSocketAddress(host, port)

  def getClient: Client with Transactions

}

object MySqlClientBuilder extends MySqlClientBuilder {

  val conf: Config = ConfigFactory.load()
  val host: String = conf.getString("mysql.host")
  val port: Int = conf.getInt("mysql.port")
  val user: String = conf.getString("mysql.user")
  val password: String = conf.getString("mysql.password")
  val db: String = conf.getString("mysql.db")

  def getClient: Client with Transactions = Mysql.client
    .withCredentials(user, password)
    .withDatabase(db)
    .newRichClient("%s:%d".format(url.getHostName, url.getPort))

}
