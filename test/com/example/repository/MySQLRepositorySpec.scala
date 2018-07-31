package com.example.repository

import scala.concurrent.ExecutionContext

import akka.Done
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.inject.guice.GuiceApplicationBuilder
import slick.jdbc.JdbcProfile

trait MySQLRepositorySpec
    extends PlaySpec
    with GuiceOneAppPerSuite
    with ScalaFutures
    with BeforeAndAfterAll
    with TableSchema
    with HasDatabaseConfigProvider[JdbcProfile] {

  implicit lazy val executor: ExecutionContext = fakeApplication.actorSystem.dispatcher

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(10, Seconds), interval = Span(1000, Millis))

  protected override lazy val dbConfigProvider: DatabaseConfigProvider =
    fakeApplication.injector.instanceOf[DatabaseConfigProvider]

  override lazy val fakeApplication: Application =
    new GuiceApplicationBuilder()
      .configure(Map(
        "slick.dbs.default.profile" -> "slick.jdbc.MySQLProfile$",
        "slick.dbs.default.db.driver" -> "com.mysql.jdbc.Driver",
        "slick.dbs.default.db.url" -> "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&",
        "slick.dbs.default.db.user" -> "root",
        "slick.dbs.default.db.password" -> "1234"
      ))
      .build()

  override def afterAll(): Unit = {
    val _ = fakeApplication.stop().mapTo[Done].futureValue
    ()
  }
}
