package fhj.swengb.projects.courseware

import java.sql.{Statement, ResultSet, Connection}

import scala.collection.mutable.ListBuffer

object Assessment extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, a.assessmentID)
    pstmt.setString(2, a.assessmentName)
    pstmt.setInt(3, a.assessmentType)
    pstmt.setFloat(4, a.assessmentMaxPoints)
    pstmt.setFloat(5, a.assessmentMinPointsForSufficient)
    pstmt.executeUpdate()
  }
  def fromDb(rs: ResultSet): List[Assessment] = {
    val lb : ListBuffer[Assessment] = new ListBuffer[Assessment]()
    while (rs.next()) lb.append(Assessment(rs.getInt("assessmentID"), rs.getString("assessmentName"), rs.getInt("assessmentType"),rs.getFloat("assessmentMaxPoints"), rs.getFloat("assessmentMinPointsForSufficient")))
    lb.toList
  }

  def dropTableSql: String = "drop table if exists assessment"
  def createTableSql: String = "create table assessment (assessmentID Int, assessmentName String, assessmentType Int, assessmentMaxPoints Float, assessmentMinPointsForSufficient Float)"
  def reTable(stmt: Statement): Int = {
    stmt.executeUpdate(dropTableSql)
    stmt.executeUpdate(createTableSql)
  }
  def insertSql: String = "insert into assessment (assessmentID, assessmentName, assessmentType, assessmentMaxPoints, assessmentMinPointsForSufficient) VALUES (?, ?, ?, ?, ?)"
  def queryAll(con: Connection): ResultSet = query(con)("select * from assessment")
}

case class Assessment(assessmentID : Int, assessmentName : String, assessmentType : Int, assessmentMaxPoints : Float, assessmentMinPointsForSufficient : Float) extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = 0
  def fromDb(rs: ResultSet): List[Assessment] = List()
  def dropTableSql: String = ""
  def createTableSql: String = ""
  override def reTable(stmt: Statement): Int = 0
  def insertSql: String = ""
}