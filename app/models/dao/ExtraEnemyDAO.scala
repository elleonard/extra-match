package models.dao

import play.api.db.slick.DatabaseConfigProvider
import javax.inject.Inject
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import models.entity.ExtraEnemy
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class ExtraEnemyDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val extraEnemy = TableQuery[ExtraEnemyTable]

  def all: Future[Seq[ExtraEnemy]] = db.run(extraEnemy.result)

  def getByNames(names: Seq[String]): Future[Seq[ExtraEnemy]] = db.run(extraEnemy.filter { enemy => enemy.enemyName.inSet(names) }.sortBy { enemy => enemy.id.asc }.result)

  def insert(enemy: ExtraEnemy): Future[Unit] = db.run(extraEnemy += enemy).map{ _ => ()}

  private class ExtraEnemyTable(tag: Tag) extends Table[ExtraEnemy](tag, "extra_enemy") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def enemyName = column[String]("enemy_name")

    def * = (id.?, enemyName) <> (ExtraEnemy.tupled, ExtraEnemy.unapply _)
  }
}
