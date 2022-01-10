package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
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

    "should return one item if item is found when accessing to [/todos/{id}] by GET method" in {
      val request = FakeRequest(GET, "/todos/1")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")      
      contentAsJson(response) mustBe Json.obj(
        "id" -> 1,
        "description" -> "test",
        "isItDone" -> true
      )
    }
    
    "should return Not_Found if item is not found when accessing to [/todos/{id}] by GET method" in {
      val request = FakeRequest(GET, "/todos/3")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
    }
  }
}
