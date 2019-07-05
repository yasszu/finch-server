package app.controller

import app.repository.UserRepository
import com.twitter.finagle.http.Request
import com.twitter.finatra.http._
import javax.inject.{Inject, Singleton}

@Singleton
class UserController @Inject()(repository: UserRepository) extends Controller {

  get("/") { request: Request =>
    "<h1>Hello, world!</h1>"
  }

  get("/users/:id") { request: Request =>
    val id = request.params("id").toLong
    repository.find(id) map {
      case Some(user) => response.ok(user)
      case None => response.notFound(new Exception("Not Found"))
    } handle {
      case e: Exception => response.internalServerError(e)
    }

  }

}