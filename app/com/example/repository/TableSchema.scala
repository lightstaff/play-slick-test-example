package com.example.repository

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import com.example.core.Model._

/** Schema for table **/
trait TableSchema {
  this: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  /** Table query for group **/
  protected val groups = TableQuery[GroupTable]

  /** Table query for user **/
  protected val users = TableQuery[UserTable]

  /** Table for group
    *
    * @param tag tag
    */
  protected class GroupTable(tag: Tag) extends Table[Group](tag, "group") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    override def * = (id.?, name) <> (Group.tupled, Group.unapply)
  }

  /** Table for user
    *
    * @param tag tag
    */
  protected class UserTable(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def email = column[String]("email")

    def groupId = column[Int]("group_id")

    def group =
      foreignKey("FK_user_TO_group", groupId, groups)(_.id,
                                                      onUpdate = ForeignKeyAction.Restrict,
                                                      onDelete = ForeignKeyAction.Restrict)

    override def * = (id.?, name, email, groupId) <> (User.tupled, User.unapply)
  }
}
