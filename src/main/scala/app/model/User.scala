package app.model

case class User(
    id: Option[Long] = None,
    name: String,
    email: String,
    comment: String
)