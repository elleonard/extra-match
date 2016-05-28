package services

import scala.concurrent._
import scala.util.Success
import scala.util.Failure
import ExecutionContext.Implicits.global
import javax.inject.Inject
import models.dao.ExtraPatternDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.entity.ExtraPattern
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import play.api.libs.json.JsValue
import play.api.libs.json.Json

class ExtraPatternService @Inject()(dao: ExtraPatternDAO) {
  implicit val petternWrites: Writes[ExtraPattern] = (
    (JsPath \ "id").write[Option[Int]] and
    (JsPath \ "enemy").write[String] and
    (JsPath \ "pattern1").write[String] and
    (JsPath \ "pattern2").write[String] and
    (JsPath \ "pattern3").write[String] and
    (JsPath \ "pattern4").write[String] and
    (JsPath \ "pattern5").write[String] and
    (JsPath \ "pattern6").write[String]
  )(unlift(ExtraPattern.unapply))

  implicit val patternsWrites = new Writes[Seq[ExtraPattern]] {
    def writes(patterns: Seq[ExtraPattern]): JsValue = {
      Json.obj("patterns" -> patterns.toList)
    }
  }

  /**
   * 入力パターンから推測される敵の行動パターン一覧を取得する
   */
  def findPatterns(inputPattern: ExtraPattern) = {
    dao.findPatterns(inputPattern.enemy).map(p => Json.toJson(p.filter { p => p.matchPattern(inputPattern.patterns) }))
  }

  /**
   * 入力パターンを登録する
   */
  def insertPatterns(inputPattern: ExtraPattern) = {
    /* 下位互換を削除 */
    val lowersFuture = dao.findPatterns(inputPattern.enemy).map(p => p.filter { p => p.isLowerPattern(inputPattern.patterns) })
    lowersFuture.map(lowers => lowers.map { lower => dao.delete(lower.id.get) })
    dao.insert(inputPattern)
  }

  /**
   * ?を含まないフルパターンかどうか
   */
  def isFullPattern(inputPattern: List[String]): Boolean = {
    inputPattern.filter(_ == "?").isEmpty
  }
}