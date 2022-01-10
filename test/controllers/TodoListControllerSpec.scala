package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class TodoListControllerSPec extends PlaySpec with GuiceOneAppPerTest {

  "TodoListController GET" must {

    "can access to [/todos] by GET method" in {
      val request = FakeRequest(GET, "/todos")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")      
    }
  }
}
