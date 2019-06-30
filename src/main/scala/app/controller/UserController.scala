package app.controller

import app.model.{Page, User}
import app.repository.{UserRepository, UserRepositoryImpl}
import com.twitter.finagle.mysql._
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._

object UserController {

  def apply()(implicit client: Client): UserController = new UserController(UserRepositoryImpl)(client)

}

class UserController(val userRepository: UserRepository)(implicit val client: Client) {

  val page: Endpoint[Page] = (param("page").as[Int] :: param("count").as[Int]).as[Page]

  /**
    * GET /users
    */
  private val getUsers: Endpoint[Seq[User]] = get("users" :: page) { p: Page =>
    userRepository.findAll(p.page, p.count) map { users =>
      Ok(users)
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * GET /user/:id
    */
  private val getUser: Endpoint[User] = get("user" :: path[Long]) { id: Long =>
    userRepository.find(id) map {
      case Some(user) => Ok(user)
      case None => NotFound(new Exception("Not Found"))
    }
  } handle {
    case e: Exception => InternalServerError(e)
  }

  /**
    * POST /user
    */
  private val postUser: Endpoint[User] = post("user" :: jsonBody[User]) { u: User =>
    (for {
      userId <- userRepository.create(u.name, u.email, u.comment)
      user <- userRepository.find(userId)
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
  private val putUser: Endpoint[User] = put("user" :: path[Long] :: jsonBody[User]) { (id: Long, u: User) =>
    (for {
      _ <- userRepository.update(id, u.name, u.email, u.comment)
      user <- userRepository.find(id)
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
    userRepository.delete(id)
    Ok(Map("result" -> "success"))
  } handle {
    case e: Exception => InternalServerError(e)
  }

  val endpoints = getUser :+: getUsers :+: postUser :+: putUser :+: deleteUser

}