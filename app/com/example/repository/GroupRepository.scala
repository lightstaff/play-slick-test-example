package com.example.repository

import scala.concurrent.Future

import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import com.example.core.Model.Group

/** Repository for group
  *
  * @param dbConfigProvider database config provider
  */
@Singleton
class GroupRepository @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
    extends TableSchema
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  /** Find all
    *
    * @return sequence group
    */
  def findAll: Future[Seq[Group]] = db.run(groups.result)

  /** Find by id
    *
    * @param id id
    * @return optional group
    */
  def findById(id: Int): Future[Option[Group]] =
    db.run(groups.filter(_.id === id).result.headOption)

  /** Create
    *
    * @param data group
    * @return created id
    */
  def create(data: Group): Future[Int] = db.run((groups returning groups.map(_.id)) += data)

  /** Update
    *
    * @param data group
    * @return affected rows
    */
  def update(data: Group): Future[Int] =
    db.run(groups.filter(_.id === data.id).map(_.name).update(data.name))

  /** Delete
    *
    * @param id id
    * @return affected rows
    */
  def delete(id: Int): Future[Int] = db.run(groups.filter(_.id === id).delete)
}
