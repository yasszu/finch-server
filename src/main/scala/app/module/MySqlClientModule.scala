package app.module

import java.net.InetSocketAddress

import com.google.inject.Provides
import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
import javax.inject.Singleton

object MySqlClientModule extends TwitterModule {

  override val modules = Seq(AppModule)

  @Singleton
  @Provides
  def providesMySqlClient(conf: Config): Client = {
    val host: String = conf.getString("mysql.host")
    val port: Int = conf.getInt("mysql.port")
    val user: String = conf.getString("mysql.user")
    val password: String = conf.getString("mysql.password")
    val db: String = conf.getString("mysql.db")
    val url = new InetSocketAddress(host, port)
    Mysql.client
      .withCredentials(user, password)
      .withDatabase(db)
      .newRichClient("%s:%d".format(url.getHostName, url.getPort))
  }

}
