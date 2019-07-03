package app.di

import java.net.InetSocketAddress

import com.google.inject.Provides
import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._
import com.twitter.inject.TwitterModule
import com.typesafe.config.{Config, ConfigFactory}
import javax.inject.Singleton

object AppModule extends TwitterModule {

  val conf: Config = ConfigFactory.load()
  val host: String = conf.getString("mysql.host")
  val port: Int = conf.getInt("mysql.port")
  val user: String = conf.getString("mysql.user")
  val password: String = conf.getString("mysql.password")
  val db: String = conf.getString("mysql.db")

  lazy val url = new InetSocketAddress(host, port)

  @Singleton
  @Provides
  def providesMySqlClient: Client = Mysql.client
    .withCredentials(user, password)
    .withDatabase(db)
    .newRichClient("%s:%d".format(url.getHostName, url.getPort))

}
