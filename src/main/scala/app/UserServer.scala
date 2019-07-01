package app

import app.controller.UserController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

object UserServerMain extends UserServer

class UserServer extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[UserController]
  }
}
