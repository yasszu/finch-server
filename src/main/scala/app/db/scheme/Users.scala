package app.db.scheme

case class Users(
    id: Long,
    name: String,
    email: String,
    comment: String
)