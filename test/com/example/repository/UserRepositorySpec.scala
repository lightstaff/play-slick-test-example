package com.example.repository

import scala.concurrent.ExecutionContext

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import com.example.core.Model.{Group, User}

class UserRepositorySpec extends MySQLRepositorySpec with ScalaFutures with BeforeAndAfterAll {

  import profile.api._

  implicit lazy val executor: ExecutionContext = fakeApplication().actorSystem.dispatcher

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(10, Seconds), interval = Span(1000, Millis))

  var existGroupId = 0

  private val repository = fakeApplication().injector.instanceOf[UserRepository]

  override def beforeAll(): Unit = {
    existGroupId =
      db.run((groups returning groups.map(_.id)) += Group(None, "test-group")).futureValue
    ()
  }

  override def afterAll(): Unit = {
    val _ = db.run(users.delete andThen groups.delete).futureValue
    ()
  }

  "user repository" should {
    "create" in {
      val createUser = User(None, "test-user", "test@test.com", existGroupId)
      val createdId = repository.create(createUser).futureValue

      repository.findById(createdId).futureValue match {
        case Some(createdUser) => createdUser mustBe createUser.copy(id = Option(createdId))
        case None              => fail("not found user")
      }
    }
  }
}
