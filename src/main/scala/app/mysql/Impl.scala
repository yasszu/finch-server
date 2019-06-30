package app.mysql

import com.typesafe.config._

object Impl extends MySqlClient {
  val conf: Config = ConfigFactory.load()
  val host: String = conf.getString("mysql.host")
  val port: Int = conf.getInt("mysql.port")
  val user: String = conf.getString("mysql.user")
  val password: String = conf.getString("mysql.password")
  val db: String = conf.getString("mysql.db")
}
