package com.example.core

/** Object for mode **/
object Model {

  /** Model for group
    *
    * @param id id
    * @param name name
    */
  final case class Group(id: Option[Int], name: String)

  /** Model for user
    *
    * @param id id
    * @param name name
    * @param email email
    * @param groupId group id
    */
  final case class User(id: Option[Int], name: String, email: String, groupId: Int)

}
