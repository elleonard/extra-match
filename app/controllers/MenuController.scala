package controllers

import play.api.mvc.Controller
import constants.NavMenu

/*
 * 上部メニューを扱うコントローラ
 */
object MenuController extends Controller {
  /**
   * 上部ナビゲーションメニューバー
   */
  def getNavMenu = NavMenu.getNavList
}
