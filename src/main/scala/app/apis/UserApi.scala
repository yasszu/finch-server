package app.apis

import app.models.User
import com.twitter.finagle.mysql._
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._

class UserApi()(implicit val client: Client) {

  val page: Endpoint[Page] = (param("page").as[Int] :: param("count").as[Int]).as[Page]

  /**
    * GET /users
    */
  private val getUsers: Endpoint[Seq[User]] = get("users" :: page) { p: Page =>
    User.findAll(p.page, p.count) map { users =>
      Ok(users)
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * GET /user/:id
    */
  private val getUser: Endpoint[User] = get("user" :: path[Long]) { id: Long =>
    User.find(id) map {
      case Some(user) => Ok(user)
      case None => NotFound(new Exception("Not Found"))
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * POST /user
    */
  private val postUser: Endpoint[User] = post("user" :: jsonBody[UserParam]) { up: UserParam =>
    (for {
      userId <- User.create(up.name, up.email, up.comment)
      user <- User.find(userId)
    } yield user) map {
      case Some(user) => Ok(user)
      case _ => NotFound(new Exception("Not Found"))
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * PUT /user/:id
    */
  private val putUser: Endpoint[User] = put("user" :: path[Long] :: jsonBody[UserParam]) { (id: Long, up: UserParam) =>
    (for {
      _ <- User.update(id, up.name, up.email, up.comment)
      user <- User.find(id)
    } yield user) map {
      case Some(user) => Ok(user)
      case _ => NotFound(new Exception("Not Found"))
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * DELETE /user/:id
    */
  private val deleteUser = delete("user" :: path[Long]) { id: Long =>
    User.delete(id)
    Ok(Map("result" -> "success"))
  } handle {
    case e: Exception => InternalServerError(e)
  }

  val endpoints = getUser :+: getUsers :+: postUser :+: putUser :+: deleteUser

}

object UserApi {

  def apply()(implicit client: Client): UserApi = new UserApi()(client)

}
