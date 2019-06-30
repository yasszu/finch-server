package app.repository

import app.model.User
import com.twitter.finagle.mysql.Client
import com.twitter.util.Future

trait UserRepository {

  def findAll(page: Int = 0, limit: Int = 100)(implicit client: Client): Future[Seq[User]]

  def find(userId: Long)(implicit client: Client): Future[Option[User]]

  def create(name: String, email: String, comment: String)(implicit client: Client): Future[Long]

  def update(userId: Long, name: String, email: String, comment: String)(implicit client: Client): Future[Long]

  def delete(userId: Long)(implicit client: Client): Future[Long]

}
