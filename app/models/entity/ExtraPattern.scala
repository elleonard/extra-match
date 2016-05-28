package models.entity

case class ExtraPattern(
  id: Option[Int],
  enemy: String,
  pattern1: String,
  pattern2: String,
  pattern3: String,
  pattern4: String,
  pattern5: String,
  pattern6: String
) {

  def this(enemy: String, patterns: List[String]){
    this(None, enemy, patterns(0), patterns(1), patterns(2),patterns(3),patterns(4),patterns(5))
  }

  val patterns = List(pattern1,pattern2,pattern3,pattern4,pattern5,pattern6)

  /**
   * パターンとして一致するかどうか
   */
  def matchPattern(input: List[String]): Boolean = {
    this.patterns.zip(input).filter(p => {
      p._1 != "?" && p._2 != "?" && p._1 != p._2
    }).isEmpty
  }

  /**
   * 渡されたものの下位互換かどうか
   */
  def isLowerPattern(input: List[String]) = {
    this.patterns.zip(input).filter(p =>
      p._1 != "?" && p._1 != p._2
    ).isEmpty
  }

  /**
   * ?を含まないフルパターンかどうか
   */
  def isFullPattern = {
    !this.patterns.contains("?")
  }
}

case class InputExtraPattern(
  enemy: String,
  pattern1: String,
  pattern2: String,
  pattern3: String,
  pattern4: String,
  pattern5: String,
  pattern6: String
) {
  val patterns = List(pattern1, pattern2, pattern3, pattern4, pattern5, pattern6)
  def convert = new ExtraPattern(enemy, patterns)
}
