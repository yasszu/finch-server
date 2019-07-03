package app.di

import app.repository.{UserRepository, UserRepositoryImpl}
import com.google.inject.Provides
import com.twitter.finagle.mysql._
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object UserModule extends TwitterModule {

  override val modules = Seq(AppModule)

  @Singleton
  @Provides
  def providesUserRepository(client: Client): UserRepository = {
    new UserRepositoryImpl(client)
  }

}
