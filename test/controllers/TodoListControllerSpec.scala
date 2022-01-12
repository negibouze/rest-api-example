package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._

class TodoListControllerSPec extends PlaySpec with GuiceOneAppPerTest {

  "TodoListController GET" must {

    "be able to access to [/todos] by GET method" in {
      val request = FakeRequest(GET, "/todos")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")      
    }

    "be returned one item if item is found when accessing to [/todos/{id}] by GET method" in {
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
    
    "be returned Not_Found if item is not found when accessing to [/todos/:id] by GET method" in {
      val request = FakeRequest(GET, "/todos/3")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
    }
  }

  "TodoListController POST" must {

    "be able to access to [/todos] by POST method" in {
      val request = FakeRequest(POST, "/todos")
        .withJsonBody(Json.obj("description" -> "I want to add new item."))
      val response = route(app, request).get

      status(response) mustBe CREATED
    }

    "be returned Bad_Request if request body is invalid when accessing to [/todos] by POST method" in {
      val request = FakeRequest(POST, "/todos")
        .withJsonBody(Json.obj("notExist" -> "This key is not exist"))
      val response = route(app, request).get

      status(response) mustBe BAD_REQUEST
    }
  }

  "TodoListController PATCH" must {

    "be able to access to [/todos/:id/done] by PATCH method" in {
      val request = FakeRequest(PATCH, "/todos/2/done")
      val response = route(app, request).get

      status(response) mustBe ACCEPTED
      contentType(response) mustBe Some("application/json")      
      contentAsJson(response) mustBe Json.obj(
        "id" -> 2,
        "description" -> "some other value",
        "isItDone" -> true
      )
    }

    "be returned Not_Found if item is not found when accessing to [/todos/:id/done] by PATCH method" in {
      val request = FakeRequest(PATCH, "/todos/999/done")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
    }
  }

  "TodoListController DELETE" must {

    "be able to access to [/todos/done] by DELETE method" in {
      val request = FakeRequest(DELETE, "/todos/done")
      val response = route(app, request).get

      status(response) mustBe ACCEPTED
    }

    "be able to access to [/todos/:id] by DELETE method" in {
      val request = FakeRequest(DELETE, "/todos/2")
      val response = route(app, request).get

      status(response) mustBe ACCEPTED
    }

    "be returned Not_Found if item is not found when accessing to [/todos/:id] by DELETE method" in {
      val request = FakeRequest(DELETE, "/todos/999")
      val response = route(app, request).get

      status(response) mustBe NOT_FOUND
    }
  }
}
