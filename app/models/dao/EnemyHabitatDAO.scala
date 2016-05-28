package models.dao

import javax.inject.Inject
import models.entity.EnemyHabitat
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class EnemyHabitatDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val enemyHabitat = TableQuery[EnemyHabitatTable]

  def getByStage(stage: String): Future[Seq[EnemyHabitat]] = db.run(enemyHabitat.filter { h => h.stageName === stage }.result)

  def insert(habitat: EnemyHabitat): Future[Unit] = db.run(enemyHabitat += habitat).map{ _ => ()}

  private class EnemyHabitatTable(tag: Tag) extends Table[EnemyHabitat](tag, "enemy_habitat"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def stageName = column[String]("stage_name")
    def enemyName = column[String]("enemy_name")

    def * = (id.?, stageName, enemyName) <> (EnemyHabitat.tupled, EnemyHabitat.unapply _)
  }
}
