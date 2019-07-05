package app.di

import com.twitter.inject.TwitterModule
import com.google.inject.Provides
import io.getquill.{FinagleMysqlContext, SnakeCase}
import javax.inject.Singleton

object QuillContextModule  extends TwitterModule {

  @Singleton
  @Provides
  def providesContext: FinagleMysqlContext[SnakeCase] = {
    new FinagleMysqlContext(SnakeCase, "ctx")
  }

}
