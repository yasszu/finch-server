package app

package object apis {

  case class Locale(
      language: String,
      country: String
  )

  case class Time(
      locale: Locale,
      time: String
  )

  case class UserParam(
      name: String,
      email: String,
      comment: String
  )

}