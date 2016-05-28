package constants

object NavMenu {
  case object NM_HOME  extends NavMenu("Home","/")
  case object NM_ENEMY extends NavMenu("敵登録","/enemy")
  case object NM_STAGE extends NavMenu("場所登録","/stage")

  def getNavList = List(NM_HOME, NM_ENEMY, NM_STAGE)
}

sealed abstract class NavMenu(val name: String, val path: String) {
  override def toString = name
  def getPath = path
}