package app.repository

import app.model.User
import com.twitter.finagle.mysql.Client
import com.twitter.finagle.mysql._
import com.twitter.util.Future
import javax.inject.Inject

class UserRepositoryImpl @Inject()(client: Client) extends UserRepository {

  def findAll(page: Int = 0, limit: Int = 100): Future[Seq[User]] = {
    client.select(s"SELECT * FROM users u WHERE u.del_flg = 0 ORDER BY u.id DESC LIMIT $page, $limit") { row =>
      val LongValue(id) = row("id").get
      val StringValue(name) = row("name").get
      val StringValue(email) = row("email").get
      val StringValue(comment) = row("comment").get
      User(Some(id), name, email, comment)
    }
  }

  def find(userId: Long): Future[Option[User]] = {
    val ps = client.prepare("SELECT * FROM users u WHERE u.id = ? AND u.del_flg = 0")
    ps(userId).map { result =>
      result.asInstanceOf[ResultSet].rows.map { row =>
        val LongValue(id) = row("id").get
        val StringValue(name) = row("name").get
        val StringValue(email) = row("email").get
        val StringValue(comment) = row("comment").get
        User(Some(id), name, email, comment)
      }.headOption
    }
  }

  def create(name: String, email: String, comment: String): Future[Long] = {
    val ps = client.prepare("INSERT INTO users (name, email, comment) VALUES (?, ?, ?)")
    ps(name, email, comment) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

  def update(userId: Long, name: String, email: String, comment: String): Future[Long] = {
    val ps = client.prepare("UPDATE users u SET u.name = ?, u.email = ?, u.comment = ? WHERE u.id = ?")
    ps(name, email, comment, userId) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

  def delete(userId: Long): Future[Long] = {
    val ps = client.prepare("UPDATE users u SET u.del_flg = 1 WHERE u.id = ?")
    ps(userId) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

}
