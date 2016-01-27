package fhj.swengb.projects.courseware

import java.sql.{Statement, ResultSet, Connection}

import scala.collection.mutable.ListBuffer

//implements the structure of the db.scala file
object Lecturer extends Db.DbEntity[Lecturer] {
  def toDb(c: Connection)(a: Lecturer) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, a.lecturerID)
    pstmt.setString(2, a.lecturerFirstname)
    pstmt.setString(3, a.lecturerLastname)
    pstmt.setString(4, a.lecturerCourse)
    pstmt.executeUpdate()
  }
  def fromDb(rs: ResultSet): List[Lecturer] = {
    val lb : ListBuffer[Lecturer] = new ListBuffer[Lecturer]()
    while (rs.next()) lb.append(Lecturer(rs.getInt("lecturerID"), rs.getString("lecturerLastname"), rs.getString("lecturerFirstname"),rs.getString("lecturerCourse")))
    lb.toList
  }

  def dropTableSql: String = "drop table if exists lecturer"
  def createTableSql: String = "create table lecturer (lecturerID Int, lecturerLastname string, lecturerFirstname string, lecturerCourse string)"
  def reTable(stmt: Statement): Int = {
    stmt.executeUpdate(dropTableSql)
    stmt.executeUpdate(createTableSql)
  }
  def insertSql: String = "insert into lecturer (lecturerID, lecturerLastname, lecturerFirstname, lecturerCourse) VALUES (?, ?, ?, ?)"
  def queryAll(con: Connection): ResultSet = query(con)("select * from lecturer")
}

case class Lecturer(lecturerID : Int, lecturerLastname : String, lecturerFirstname :String, lecturerCourse : String) extends Db.DbEntity[Lecturer] {
  def toDb(c: Connection)(a: Lecturer) : Int = 0
  def fromDb(rs: ResultSet): List[Lecturer] = List()
  def dropTableSql: String = ""
  def createTableSql: String = ""
  override def reTable(stmt: Statement): Int = 0
  def insertSql: String = ""
}