package app.di

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.typesafe.config.{Config, ConfigFactory}
import javax.inject.Singleton

object AppModule extends TwitterModule {

  @Singleton
  @Provides
  def providesConfig: Config = ConfigFactory.load()

}
