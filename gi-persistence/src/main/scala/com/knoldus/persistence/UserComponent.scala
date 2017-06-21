package com.knoldus.persistence

import com.knoldus.persistence.mappings.UserMapping
import com.knoldus.utils.models.User
import scala.concurrent.Future

class UserComponent extends UserMapping with PostgresDbComponent {

  import driver.api._

  /**
    * This method is used for insert user into database
    *
    * @param user
    * @return
    */
  def insert(user: User): Future[Int] = {
    db.run(userInfo += user)
  }

  /**
    * Fetches user detail using email and password
    *
    * @param email
    * @param password
    * @return Future[Option[User]]
    */
  def getUserByEmailAndPassword(email: String, password: String): Future[Option[User]] = {
    db.run(userInfo.filter(user => user.email === email && user.password === password).result.headOption)
  }

  /**
    * This method is used for fetching user record with the help of userId
    *
    * @param userId
    * @return Option[User]
    */
  def getUserByUserId(userId: String): Future[Option[User]] = {
    db.run(userInfo.filter(user => user.id === userId).result.headOption)
  }

  /**
    * This method is used for fetching user record with the help of Access Token
    *
    * @param accessToken
    * @return Option[User]
    */
  def getUserByAccessToken(accessToken: String): Future[Option[User]] = {
    db.run(userInfo.filter(user => user.accessToken === accessToken).result.headOption)
  }

  /**
    * This method is used for fetching All user from DB
    *
    * @return
    */
  def getAllUser: Future[List[User]] = {
    db.run(userInfo.to[List].result)
  }
}
