package models.dao

import javax.inject.Inject
import models.entity.ExtraStage
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import scala.concurrent.Future

class ExtraStageDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val extraStage = TableQuery[ExtraStageTable]

  def all: Future[Seq[ExtraStage]] = db.run(extraStage.result)

  def insert(stage: ExtraStage): Future[Unit] = db.run(extraStage += stage).map{ _ => () }

  def delete(stage: String): Future[Int] = db.run(extraStage.filter { s => s.stageName === stage }.delete)

  private class ExtraStageTable(tag: Tag) extends Table[ExtraStage](tag, "extra_stage") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def stageName = column[String]("stage_name")

    def * = (id.?, stageName) <> (ExtraStage.tupled, ExtraStage.unapply _)
  }
}
