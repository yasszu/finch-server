package app.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http._


class UserController extends Controller {

  get("/") { request: Request =>
    "<h1>Hello, world!</h1>"
  }

}