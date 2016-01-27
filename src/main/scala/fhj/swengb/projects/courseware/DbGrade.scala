package fhj.swengb.projects.courseware

import java.sql.{Timestamp, Statement, ResultSet, Connection}

import scala.collection.mutable.ListBuffer

object Grade extends Db.DbEntity[Grade] {
  def toDb(c: Connection)(a: Grade) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, a.gradeId)
    pstmt.setInt(2, a.assessmentID)
    pstmt.setInt(3, a.studentID)
    pstmt.setInt(4, a.lecturerID)
    pstmt.setTimestamp(5, a.gradeDate)
    pstmt.setFloat(6, a.gradePoints)
    pstmt.executeUpdate()
  }
  def fromDb(rs: ResultSet): List[Grade] = {
    val lb : ListBuffer[Grade] = new ListBuffer[Grade]()
    while (rs.next()) lb.append(Grade(rs.getInt("gradeId"), rs.getInt("assessmentID"), rs.getInt("studentID"),rs.getInt("lecturerID"), rs.getTimestamp("gradeDate"), rs.getFloat("gradePoints")))
    lb.toList
  }

  def dropTableSql: String = "drop table if exists grade"
  def createTableSql: String = "create table grade (gradeId Int, assessmentID Int, studentID Int, lecturerID Int, gradeDate Timestamp, gradePoints Float)"
  def reTable(stmt: Statement): Int = {
    stmt.executeUpdate(dropTableSql)
    stmt.executeUpdate(createTableSql)
  }
  def insertSql: String = "insert into grade (gradeId, assessmentID, studentID, lecturerID, gradeDate, gradePoints) VALUES (?, ?, ?, ?, ?, ?)"
  def queryAll(con: Connection): ResultSet = query(con)("select * from grade")
}

case class Grade(gradeId : Int, assessmentID : Int, studentID : Int, lecturerID : Int, gradeDate : Timestamp, gradePoints : Float) extends Db.DbEntity[Grade] {
  def toDb(c: Connection)(a: Grade) : Int = 0
  def fromDb(rs: ResultSet): List[Grade] = List()
  def dropTableSql: String = ""
  def createTableSql: String = ""
  override def reTable(stmt: Statement): Int = 0
  def insertSql: String = ""
}