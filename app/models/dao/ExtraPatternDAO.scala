package models.dao

import scala.concurrent.Future

import javax.inject.Inject
import models.entity.ExtraPattern
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

class ExtraPatternDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val extraPattern = TableQuery[ExtraPatternTable]

  /**
   * 全取得
   */
  def all: Future[Seq[ExtraPattern]] = db.run(extraPattern.result)

  /**
   * 敵の名前で絞込
   */
  def findPatterns(enemy: String): Future[Seq[ExtraPattern]] = {
    db.run(extraPattern.filter( record => {
      record.enemyName === enemy
    }).result)
  }

  /**
   * 登録
   */
  def insert(pattern: ExtraPattern): Future[Unit] = db.run(extraPattern += pattern).map{ _ => () }
  /**
   * 削除
   */
  def delete(id: Int): Future[Int] = db.run(extraPattern.filter(_.id === id).delete)

  private class ExtraPatternTable(tag: Tag) extends Table[ExtraPattern](tag, "extra_pattern"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def enemyName = column[String]("enemy_name")
    def pattern1 = column[String]("pattern1")
    def pattern2 = column[String]("pattern2")
    def pattern3 = column[String]("pattern3")
    def pattern4 = column[String]("pattern4")
    def pattern5 = column[String]("pattern5")
    def pattern6 = column[String]("pattern6")

    def * = (id.?, enemyName, pattern1, pattern2, pattern3, pattern4, pattern5, pattern6) <> (ExtraPattern.tupled, ExtraPattern.unapply _)
  }
}
