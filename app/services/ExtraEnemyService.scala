package services

import scala.concurrent.ExecutionContext.Implicits.global
import models.dao.ExtraEnemyDAO
import javax.inject.Inject
import play.api.libs.json.Writes
import models.entity.ExtraEnemy
import models.entity.ExtraStage
import play.api.libs.json.JsValue
import play.api.libs.json.JsPath
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import models.entity.EnemyHabitat
import models.dao.EnemyHabitatDAO

class ExtraEnemyService @Inject()(enemyDao: ExtraEnemyDAO, habitatDao: EnemyHabitatDAO) {
  implicit val enemyWrites: Writes[ExtraEnemy] = (
    (JsPath \ "id").write[Option[Int]] and
    (JsPath \ "name").write[String]
  )(unlift(ExtraEnemy.unapply))

  implicit val enemiesWrites = new Writes[Seq[ExtraEnemy]] {
    def writes(enemies: Seq[ExtraEnemy]): JsValue = {
      Json.obj("enemies" -> enemies.toList)
    }
  }

  def getAll = enemyDao.all
  def getAllJson = getAll.map(all => Json.toJson(all))

  def insertEnemy(enemy: String) = {
    enemyDao.insert(ExtraEnemy(None, enemy))
  }

  /**
   * 出現場所ごとに取得
   */
  def getByHabitat(stage: String) = {
    val habitats = habitatDao.getByStage(stage)
    habitats.flatMap(h =>
      enemyDao.getByNames(h.map { habitat => habitat.enemy }).map(enemies => Json.toJson(enemies))
    )
  }

  /**
   * 出現場所を記録
   */
  def insertHabitat(enemy: String, stage: String) = habitatDao.insert(EnemyHabitat(None, stage, enemy))
}

