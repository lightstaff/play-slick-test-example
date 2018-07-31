package com.example.repository

import com.example.core.Model.{Group, User}

class UserRepositorySpec extends MySQLRepositorySpec {

  import profile.api._

  var existGroupId = 0

  private val repository = fakeApplication.injector.instanceOf[UserRepository]

  override def beforeAll(): Unit = {
    existGroupId =
      db.run((groups returning groups.map(_.id)) += Group(None, "test-group")).futureValue
    ()
  }

  override def afterAll(): Unit = {
    val _ = db.run(users.delete andThen groups.delete).futureValue

    super.afterAll()
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
