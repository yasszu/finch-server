package app.utils

object DDL {

  val createUsersTable =
    """CREATE TABLE IF NOT EXISTS `users` (
       `id` bigint(20) NOT NULL AUTO_INCREMENT,
       `name` varchar(255) NOT NULL,
       `email` varchar(255) NOT NULL,
       `comment` text,
       `del_flg` tinyint(4) NOT NULL DEFAULT '0',
       `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (`id`)
       ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8"""

}
