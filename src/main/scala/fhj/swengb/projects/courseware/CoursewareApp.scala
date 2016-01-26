package fhj.swengb.projects.courseware

import java.io.File
import java.net.URL
import java.sql.{Timestamp, Connection, DriverManager}
import java.util.{Date, ResourceBundle}
import javafx.application.Application
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.TreeItem.TreeModificationEvent
import javafx.scene.control._
import javafx.scene.input.{MouseButton, MouseEvent}
import javafx.scene.layout.{AnchorPane, BorderPane}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.JavaConversions._
import scala.util.Try
import scala.util.control.NonFatal


object CoursewareApp {
  def main (args: Array[String]) {
    Application.launch(classOf[CoursewareApp], args: _*)
  }
}

class CoursewareApp extends javafx.application.Application {
  val Fxml = "/fhj/swengb/projects/courseware/Courseware.fxml"
  //val Css = "/fhj/swengb/projects/courseware/Courseware.css"
  val loader = new FXMLLoader(getClass.getResource(Fxml))
  override def start(stage: Stage): Unit = try {
    stage.setTitle("Courseware")
    loader.load[Parent]()
    val scene = new Scene(loader.getRoot[Parent])
    stage.setScene(scene)
    //stage.getScene.getStylesheets.add(Css)
    stage.show()

  } catch {
    case NonFatal(e) => {
      e.printStackTrace()
    }
  }

}

class CoursewareAppController extends Initializable {
  @FXML var lecturerLecturernameDropdown : ChoiceBox[Lecturer] = _
  @FXML var lecturerAssessmentDropdown : ChoiceBox[Assessment] = _
  @FXML var lecturerStudentDropdown : ChoiceBox[Student] = _
  @FXML var lecturerPoints : TextArea = _
  @FXML var studentStudentDropdown : ChoiceBox[Student] = _
  @FXML var studentGrades : TextArea = _
  @FXML var studentName : TextArea = _
  @FXML var studentNumber : TextArea = _
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    resetDatabase()
  }

  def resetDatabase(): Unit = {
    // --------- initialize Lecturers
    Lecturer.reTable(Db.maybeConnection.get.createStatement())
    for (lecturer <- Data.lecturerList) {
      Lecturer.toDb(Db.maybeConnection.get)(lecturer)
    }
    lecturerLecturernameDropdown.getItems.clear()
    lecturerLecturernameDropdown.getItems.addAll(Data.lecturerList)

    // --------- initialize Assessments
    Assessment.reTable(Db.maybeConnection.get.createStatement())
    for (assessment <- Data.assessmentList) {
      Assessment.toDb(Db.maybeConnection.get)(assessment)
    }
    lecturerAssessmentDropdown.getItems.clear()
    lecturerAssessmentDropdown.getItems.addAll(Data.assessmentList)

    // --------- initialize Students
    Student.reTable(Db.maybeConnection.get.createStatement())
    for (student <- Data.studentList) {
      Student.toDb(Db.maybeConnection.get)(student)
    }
    lecturerStudentDropdown.getItems.clear()
    lecturerStudentDropdown.getItems.addAll(Data.studentList)

    // --------- initialize Grades
    Grade.reTable(Db.maybeConnection.get.createStatement())
    for (grade <- Data.gradeList) {
      Grade.toDb(Db.maybeConnection.get)(grade)
    }

    // --------- clean up Student tab contents
    studentStudentDropdown.getItems.clear()
    studentStudentDropdown.getItems.addAll(Data.studentList)
    studentName.setText("")
    studentNumber.setText("")
    studentGrades.setText("")
  }

  def insertGrade(): Unit = {
    try {
      val currentLecturer = lecturerLecturernameDropdown.getValue
      val currentAssessment = lecturerAssessmentDropdown.getValue
      val currentStudent = lecturerStudentDropdown.getValue
      val currentPoints = lecturerPoints.getText.toFloat // throws java.lang.NumberFormatException if text is entered or is empty
      if (currentLecturer == null | currentAssessment == null | currentStudent == null) {
        throw new IllegalArgumentException()
      }
      val currentDate = new Date().getTime
      val currentTimestamp = new Timestamp(currentDate)
      val gradeID = util.Random.nextInt
      Grade.toDb(Db.maybeConnection.get)(Grade(gradeID, currentAssessment.assessmentID, currentStudent.studentID, currentLecturer.lecturerID, currentTimestamp, currentPoints))
      val alert = new Alert(AlertType.INFORMATION)
      alert.setTitle("Successfully inserted")
      alert.setHeaderText(null)
      alert.setContentText("The grade has been successfully inserted.")
      alert.showAndWait()
    } catch {
      // see http://code.makery.ch/blog/javafx-dialogs-official/
      case e:java.lang.NumberFormatException => {
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Points error")
        alert.setHeaderText("Points error")
        alert.setContentText("Please enter a valid number in the Points field!")
        alert.showAndWait()
      }
      case e:IllegalArgumentException => {
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Dropdown error")
        alert.setHeaderText("Dropdown error")
        alert.setContentText("Please select a value for each drop down menu!")
        alert.showAndWait()
      }
      case e => e.printStackTrace()
    }
  }





  def execView(): Unit = {
    try {
      val currentStudent = studentStudentDropdown.getValue
      if (currentStudent == null) throw new IllegalArgumentException()
      val selectQuery = "select * from grade where studentID=" + currentStudent.studentID
      val selectedGrades = Grade.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectQuery))
      if (selectedGrades.isEmpty) {
        val alert = new Alert(AlertType.INFORMATION)
        alert.setTitle("No grades")
        alert.setHeaderText(null)
        alert.setContentText("There are currently no grades for the selected student.")
        alert.showAndWait()
      }
      studentName.setText(currentStudent.studentLastname + " " + currentStudent.studentFirstname)
      studentNumber.setText(currentStudent.studentMatnum)
      studentGrades.clear()
      for (grade <- selectedGrades) {
        val selectAssessmentQuery = "select * from assessment where assessmentID=" + grade.assessmentID
        val selectLecturerQuery = "select * from lecturer where lecturerID=" + grade.lecturerID
        val assessment = Assessment.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectAssessmentQuery)).head
        val assessmentName = assessment.assessmentName
        val maxPoints = assessment.assessmentMaxPoints
        val minPointsForSufficient = assessment.assessmentMinPointsForSufficient
        val lecturer = Lecturer.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectLecturerQuery)).head
        val lecturerName = lecturer.lecturerLastname + " " + lecturer.lecturerFirstname
        val failedOrPassed = if (grade.gradePoints >= minPointsForSufficient) "-> passed" else "-> failed"
        studentGrades.appendText(s"$assessmentName ($lecturerName): ${grade.gradePoints}/$maxPoints $failedOrPassed (minimum $minPointsForSufficient)\n")
      }


    } catch {
      case e:IllegalArgumentException => {
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Student error")
        alert.setHeaderText("Student error")
        alert.setContentText("Please select a student!")
        alert.showAndWait()
      }
      case e => e.printStackTrace()
    }
  }

}