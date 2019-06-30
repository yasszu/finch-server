package app.model

import com.twitter.finagle.mysql.Client
import com.twitter.finagle.mysql._
import com.twitter.util.Future

case class User(
    id: Option[Long],
    name: String,
    email: String,
    comment: String
)

object User {

  def findAll(page: Int = 0, limit: Int = 100)(implicit client: Client): Future[Seq[User]] = {
    val sql = s"SELECT * FROM users u WHERE u.del_flg = 0 ORDER BY u.id DESC LIMIT $page, $limit"
    client.select(sql) { row =>
      val LongValue(id) = row("id").get
      val StringValue(name) = row("name").get
      val StringValue(email) = row("email").get
      val StringValue(comment) = row("comment").get
      User(Some(id), name, email, comment)
    }
  }

  def find(userId: Long)(implicit client: Client): Future[Option[User]] = {
    val sql = "SELECT * FROM users u WHERE u.id = ? AND u.del_flg = 0"
    val ps = client.prepare(sql)
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

  def create(name: String, email: String, comment: String)(implicit client: Client): Future[Long] = {
    val sql = "INSERT INTO users (name, email, comment) VALUES (?, ?, ?)"
    val ps = client.prepare(sql)
    ps(name, email, comment) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

  def update(userId: Long, name: String, email: String, comment: String)(implicit client: Client): Future[Long] = {
    val sql = "UPDATE users u SET u.name = ?, u.email = ?, u.comment = ? WHERE u.id = ?"
    val ps = client.prepare(sql)
    ps(name, email, comment, userId) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

  def delete(userId: Long)(implicit client: Client): Future[Long] = {
    val sql = "UPDATE users u SET u.del_flg = 1 WHERE u.id = ?"
    val ps = client.prepare(sql)
    ps(userId) map { result =>
      result.asInstanceOf[OK].insertId
    }
  }

}
