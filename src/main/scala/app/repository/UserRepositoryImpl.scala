package app.repository

import app.model.User
import com.twitter.util.Future
import io.getquill._
import javax.inject.{Inject, Singleton}

@Singleton
class UserRepositoryImpl @Inject() (ctx: FinagleMysqlContext[SnakeCase]) extends UserRepository {

  import ctx._

  override def findAll(page: Int, limit: Int): Future[Seq[User]] = ???

  override def find(userId: Long): Future[Option[User]] = {
    val q = quote {
      query[User].filter(u => u.id == lift(userId))
    }
    ctx.run(q).map(_.headOption)
  }

  override def create(name: String, email: String, comment: String): Future[Long] = ???

  override def update(userId: Long, name: String, email: String, comment: String): Future[Long] = ???

  override def delete(userId: Long): Future[Long] = ???

}
