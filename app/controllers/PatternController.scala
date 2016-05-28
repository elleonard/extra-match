package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import javax.inject.Singleton
import javax.inject.Inject
import models.entity.InputExtraPattern
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Action
import play.api.mvc.Controller
import services.ExtraPatternService

/**
 * 敵行動パターンを扱うコントローラ
 */
@Singleton
class PatternController @Inject()(service: ExtraPatternService) extends Controller {

  val form = Form(mapping(
    "enemy" ->text,
    "pattern1" ->text,
    "pattern2" ->text,
    "pattern3" ->text,
    "pattern4" ->text,
    "pattern5" ->text,
    "pattern6" ->text
    )(InputExtraPattern.apply)(InputExtraPattern.unapply)
  )

  def insertPattern = Action(implicit request => {
    form.bindFromRequest.fold(
      error => BadRequest(views.html.index("DB登録に失敗しました", Seq())),
      data => {
        val f = service.insertPatterns(data.convert)

        Ok(views.html.index("DBに登録しました", Seq()))
      }
    )
  })

  def findPattern = Action.async(implicit request => {
    form.bindFromRequest.fold(
      error => Future(BadRequest("")),
      data => {
        val pattern = service.findPatterns(data.convert)

        pattern.map (Ok(_))
      }
    )
  })

}
