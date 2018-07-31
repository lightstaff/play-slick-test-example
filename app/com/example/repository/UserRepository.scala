package com.example.repository

import scala.concurrent.Future

import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import com.example.core.Model.User

/** Repository for user
  *
  * @param dbConfigProvider database config provider
  */
@Singleton
class UserRepository @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
    extends TableSchema
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  /** Find all
    *
    * @return sequence group
    */
  def findAll: Future[Seq[User]] = db.run(users.result)

  /** Find by id
    *
    * @param id id
    * @return optional user
    */
  def findById(id: Int): Future[Option[User]] = db.run(users.filter(_.id === id).result.headOption)

  /** Create
    *
    * @param data user
    * @return created id
    */
  def create(data: User): Future[Int] = db.run((users returning users.map(_.id)) += data)

  /** Update
    *
    * @param data user
    * @return affected rows
    */
  def update(data: User): Future[Int] =
    db.run(
      users
        .filter(_.id === data.id)
        .map(u => (u.name, u.email, u.groupId))
        .update((data.name, data.email, data.groupId)))

  /** Delete
    *
    * @param id id
    * @return affected rows
    */
  def delete(id: Int): Future[Int] = db.run(users.filter(_.id === id).delete)
}
