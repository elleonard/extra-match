package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.data.Form
import play.api.data.Forms._
import services.ExtraEnemyService
import scala.concurrent.Future

/**
 * 敵情報を扱うコントローラ
 */
@Singleton
class EnemyController @Inject()(enemyService: ExtraEnemyService) extends Controller {
  val form = Form(tuple("enemy" -> text, "stage" -> text))

  def enemy = Action(implicit request => {
    Ok(views.html.enemy(""))
  })

  /**
   * DBに敵情報を登録する
   */
  def insertEnemy = Action(implicit request => {
    form.bindFromRequest.fold(
      error => BadRequest(views.html.enemy(" 登録に失敗しました")),
      data => {
        enemyService.insertEnemy(data._1)

        Ok(views.html.enemy(data._1+" をDBに登録しました"))
      }
    )
  })

  /**
   * DBに出現場所情報を登録する
   */
  def insertHabitat = Action(implicit request => {
    form.bindFromRequest.fold(
      error => BadRequest(views.html.enemy("")),
      data => {
        enemyService.insertHabitat(data._1, data._2)

        Ok(views.html.enemy(data+" をDBに登録しました"))
      }
    )
  })


  /**
   * 全取得
   */
  def all = Action.async(implicit request =>{
    val enemies = enemyService.getAllJson

    enemies.map(enemy => Ok(enemy))
  })

  /**
   * 出現場所で絞り込む
   */
  def getByStage(stage: String) = Action.async(implicit request => {
    val enemies = enemyService.getByHabitat(stage)

    enemies.map(enemy => Ok(enemy))
  })
}