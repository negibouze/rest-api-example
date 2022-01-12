package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import models.{TodoListItem}
import models.{NewTodoListItem}

@Singleton
class TodoListController @Inject()(
  val cc: ControllerComponents
) extends BaseController {

  override protected def controllerComponents: ControllerComponents = cc

  private val todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", true)
  todoList += TodoListItem(2, "some other value", false)

  implicit val todoListJson = Json.format[TodoListItem]
  implicit val newTodoListJson = Json.format[NewTodoListItem]

  def getAll(): Action[AnyContent] = Action {
    if (todoList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(todoList))
    }
  }
  
  def getById(id: Long) = Action {
    val foundItem = todoList.find(_.id == id)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  def addItem() = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val todoListItem: Option[NewTodoListItem] =
      jsonObject.flatMap(
        Json.fromJson[NewTodoListItem](_).asOpt
      )

    todoListItem match {
      case Some(newItem) =>
        val nextId = todoList.map(_.id).max + 1
        val toBeAdded = TodoListItem(nextId, newItem.description, false)
        todoList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None => BadRequest
    }
  }

  def markAsDone(id: Long) = Action {
    val foundIndex = todoList.indexWhere(_.id == id)
    foundIndex match {
      case -1 => NotFound
      case _ =>
        val doneItem = todoList(foundIndex).copy(isItDone = true)
        todoList(foundIndex) = doneItem
        Accepted(Json.toJson(doneItem))
    }
  }

  def deleteItem(id: Long) = Action {
    val foundIndex = todoList.indexWhere(_.id == id)
    foundIndex match {
      case -1 => NotFound
      case _ =>
        todoList.filterInPlace(_.id != id)
        Accepted
    }
  }
}
