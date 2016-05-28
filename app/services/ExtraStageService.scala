package services

import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.Inject
import javax.inject.Singleton
import models.dao.ExtraStageDAO
import models.entity.ExtraStage
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Writes

class ExtraStageService @Inject()(dao: ExtraStageDAO) {

  implicit val stageWrites: Writes[ExtraStage] = (
    (JsPath \ "id").write[Option[Int]] and
    (JsPath \ "stage").write[String]
  )(unlift(ExtraStage.unapply))

  implicit val stagesWrites = new Writes[Seq[ExtraStage]] {
    def writes(stages: Seq[ExtraStage]): JsValue = {
      Json.obj("stages" -> stages.toList)
    }
  }

  def getAll = dao.all
  def getAllJson = getAll.map(all => Json.toJson(all))

  def inputStage(stage: String) = {
    dao.insert(ExtraStage(None, stage))
  }

  def deleteStage(stage: String) = dao.delete(stage)
}