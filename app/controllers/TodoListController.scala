package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class TodoListController @Inject()(
  val cc: ControllerComponents
) extends BaseController {

  override protected def controllerComponents: ControllerComponents = cc

  def getAll(): Action[AnyContent] = Action {
    NoContent
  }
}
