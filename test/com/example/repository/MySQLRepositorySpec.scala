package com.example.repository

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.inject.guice.GuiceApplicationBuilder
import slick.jdbc.JdbcProfile

trait MySQLRepositorySpec
    extends PlaySpec
    with GuiceOneAppPerSuite
    with TableSchema
    with HasDatabaseConfigProvider[JdbcProfile] {

  protected override lazy val dbConfigProvider: DatabaseConfigProvider =
    fakeApplication().injector.instanceOf[DatabaseConfigProvider]

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(Map(
        "slick.dbs.default.profile" -> "slick.jdbc.MySQLProfile$",
        "slick.dbs.default.db.driver" -> "com.mysql.jdbc.Driver",
        "slick.dbs.default.db.url" -> "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&",
        "slick.dbs.default.db.user" -> "root",
        "slick.dbs.default.db.password" -> "1234"
      ))
      .build()
}
