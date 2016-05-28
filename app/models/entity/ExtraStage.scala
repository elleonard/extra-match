package models.entity

import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.JsPath

import play.api.libs.json.JsValue

case class ExtraStage(
  id: Option[Int],
  stageName: String
) {
  override def toString: String = stageName
}
