package app.repository

import app.model.User
import com.twitter.util.Future

trait UserRepository {

  def findAll(page: Int, limit: Int): Future[Seq[User]]

  def find(userId: Long): Future[Option[User]]

  def create(name: String, email: String, comment: String): Future[Long]

  def update(userId: Long, name: String, email: String, comment: String): Future[Long]

  def delete(userId: Long): Future[Long]

}
