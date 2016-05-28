package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import services.ExtraStageService
import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.Controller
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Action
import services.ExtraStageService
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * ステージを扱うコントローラ
 */
@Singleton
class StageController @Inject()(service: ExtraStageService) extends Controller {
  val form = Form(single(
    "stage" -> text
    )
  )

  def stage = Action(implicit request => {
    Ok(views.html.stage(""))
  })

  def insertStage = Action(implicit request => {
    form.bindFromRequest.fold(
      error => BadRequest(views.html.stage("")),
      data => {
        val f = service.inputStage(data)

        Ok(views.html.stage(data+" をDBに登録しました"))
      }
    )
  })

  def deleteStage = Action(implicit request => {
    form.bindFromRequest.fold(
      error => BadRequest(views.html.stage("")),
      data => {
        val f = service.deleteStage(data)

        Await.ready(f, Duration.Inf)

        f.value.get match{
          case Success(_) => Ok(views.html.stage(data+" をDBから削除しました"))
          case Failure(_) => Ok(views.html.stage(data+" の削除に失敗しました"))
        }
      }
    )
  })

  def all = Action.async(implicit request => {
    val stages = service.getAllJson

    stages.map(stage => Ok(stage))
  })

  def stageListJs(id: String, tag: String) = Action {
    Ok(views.js.stagelist(id, tag))
  }
}