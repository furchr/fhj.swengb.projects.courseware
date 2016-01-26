package fhj.swengb.projects.courseware

import java.sql.{Statement, ResultSet, Connection}

import scala.collection.mutable.ListBuffer

object Student extends Db.DbEntity[Student] {
  def toDb(c: Connection)(a: Student) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, a.studentID)
    pstmt.setString(2, a.studentMatnum)
    pstmt.setString(3, a.studentLastname)
    pstmt.setString(4, a.studentFirstname)
    pstmt.setString(5, a.studentStudyprogramme)
    pstmt.executeUpdate()
  }
  def fromDb(rs: ResultSet): List[Student] = {
    val lb : ListBuffer[Student] = new ListBuffer[Student]()
    while (rs.next()) lb.append(Student(rs.getInt("studentID"), rs.getString("studentMatnum"), rs.getString("studentLastname"),rs.getString("studentFirstname"), rs.getString("studentStudyprogramme")))
    lb.toList
  }

  def dropTableSql: String = "drop table if exists student"
  def createTableSql: String = "create table student (studentID Int, studentMatnum String, studentLastname String, studentFirstname String, studentStudyprogramme String)"
  def reTable(stmt: Statement): Int = {
    stmt.executeUpdate(dropTableSql)
    stmt.executeUpdate(createTableSql)
  }
  def insertSql: String = "insert into student (studentID, studentMatnum, studentLastname, studentFirstname, studentStudyprogramme) VALUES (?, ?, ?, ?, ?)"
  def queryAll(con: Connection): ResultSet = query(con)("select * from student")
}

case class Student(studentID : Int, studentMatnum : String, studentLastname : String, studentFirstname : String, studentStudyprogramme : String) extends Db.DbEntity[Student] {
  def toDb(c: Connection)(a: Student) : Int = 0
  def fromDb(rs: ResultSet): List[Student] = List()
  def dropTableSql: String = ""
  def createTableSql: String = ""
  override def reTable(stmt: Statement): Int = 0
  def insertSql: String = ""
}