package app.repository

import app.entity.User
import app.db.scheme.Users
import com.twitter.util.Future
import io.getquill._
import javax.inject.{Inject, Singleton}

@Singleton
class UserRepositoryImpl @Inject()(ctx: FinagleMysqlContext[SnakeCase]) extends UserRepository {

  import ctx._

  override def findAll(page: Int, limit: Int): Future[Seq[User]] = {
    val q = quote {
      query[Users]
        .map(u => User(u.id, u.name, u.email, u.comment))
        .drop(lift(page))
        .take(lift(limit))
    }
    ctx.run(q)
  }

  override def find(userId: Long): Future[Option[User]] = {
    val q = quote {
      query[Users]
        .filter(u => u.id == lift(userId))
        .map(u => User(u.id, u.name, u.email, u.comment))
    }
    ctx.run(q).map(_.headOption)
  }

  override def create(name: String, email: String, comment: String): Future[Long] = ???

  override def update(userId: Long, name: String, email: String, comment: String): Future[Long] = ???

  override def delete(userId: Long): Future[Long] = ???

}
