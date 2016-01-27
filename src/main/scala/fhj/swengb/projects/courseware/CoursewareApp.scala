package fhj.swengb.projects.courseware

import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
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
import javafx.scene.web.WebView
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.JavaConversions._
import scala.io.Source
import scala.util.Try
import scala.util.control.NonFatal

object CoursewareApp {
  def main (args: Array[String]) {
    Application.launch(classOf[CoursewareApp], args: _*)
  }
}

//starts the gui
class CoursewareApp extends javafx.application.Application {
  val Fxml = "/fhj/swengb/projects/courseware/Courseware.fxml"
  val Css = "/fhj/swengb/projects/courseware/Courseware.css"
  val loader = new FXMLLoader(getClass.getResource(Fxml))
  override def start(stage: Stage): Unit = try {
    stage.setTitle("Courseware")
    loader.load[Parent]()
    val scene = new Scene(loader.getRoot[Parent])
    stage.setScene(scene)
    stage.getScene.getStylesheets.add(Css)
    stage.show()
  } catch {
    case NonFatal(e) => {
      e.printStackTrace()
    }
  }
}

//access to fxml gui elements with fxID
class CoursewareAppController extends Initializable {
  @FXML var lecturerLecturernameDropdown : ChoiceBox[Lecturer] = _
  @FXML var lecturerAssessmentDropdown : ChoiceBox[Assessment] = _
  @FXML var lecturerStudentDropdown : ChoiceBox[Student] = _
  @FXML var lecturerPoints : TextArea = _
  @FXML var studentStudentDropdown : ChoiceBox[Student] = _
  @FXML var studentGrades : WebView = _
  @FXML var studentName : TextArea = _
  @FXML var studentNumber : TextArea = _

  //when the app starts the initialize function will be called
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    resetDatabase()
  }

  //access to database
  def resetDatabase(): Unit = {
    // --------- initialize Lecturers
    //recreate => drops and creates the table if table already exists
    Lecturer.reTable(Db.maybeConnection.get.createStatement())
    //for each lecturer one insert
    for (lecturer <- Data.lecturerList) {
      Lecturer.toDb(Db.maybeConnection.get)(lecturer)
    }
    //clears the dropdown menu to avoid duplicate entries
    lecturerLecturernameDropdown.getItems.clear()
    lecturerLecturernameDropdown.getItems.addAll(Lecturer.fromDb(Lecturer.queryAll(Db.maybeConnection.get)))

    // --------- initialize Assessments
    //recreate => drops and creates the table if table already exists
    Assessment.reTable(Db.maybeConnection.get.createStatement())
    //for each assessment one insert
    for (assessment <- Data.assessmentList) {
      Assessment.toDb(Db.maybeConnection.get)(assessment)
    }
    //clears the dropdown menu to avoid duplicate entries
    lecturerAssessmentDropdown.getItems.clear()
    lecturerAssessmentDropdown.getItems.addAll(Assessment.fromDb(Assessment.queryAll(Db.maybeConnection.get)))

    // --------- initialize Students
    //recreate => drops and creates the table if table already exists
    Student.reTable(Db.maybeConnection.get.createStatement())
    //for each student one insert
    for (student <- Data.studentList) {
      Student.toDb(Db.maybeConnection.get)(student)
    }
    //clears the dropdown menu to avoid duplicate entries
    lecturerStudentDropdown.getItems.clear()
    lecturerStudentDropdown.getItems.addAll(Student.fromDb(Student.queryAll(Db.maybeConnection.get)))

    // --------- initialize Grades
    //recreate => drops and creates the table if table already exists
    Grade.reTable(Db.maybeConnection.get.createStatement())
    //for each grade one insert
    for (grade <- Data.gradeList) {
      Grade.toDb(Db.maybeConnection.get)(grade)
    }

    // --------- clean up Student tab contents
    studentStudentDropdown.getItems.clear()
    studentStudentDropdown.getItems.addAll(Data.studentList)
    studentName.setText("")
    studentNumber.setText("")
    studentGrades.getEngine.loadContent("")
  }

  def insertGrade(): Unit = {
    try {
      //gets the values from the gui
      val currentLecturer = lecturerLecturernameDropdown.getValue
      val currentAssessment = lecturerAssessmentDropdown.getValue
      val currentStudent = lecturerStudentDropdown.getValue
      val currentPoints = lecturerPoints.getText.toFloat // throws java.lang.NumberFormatException if text is entered or is empty
      if (currentPoints < 0 | currentPoints > currentAssessment.assessmentMaxPoints) throw new java.lang.NumberFormatException()
      if (currentLecturer == null | currentAssessment == null | currentStudent == null) {
        throw new IllegalArgumentException()
      }
      val currentDate = new Date().getTime
      val currentTimestamp = new Timestamp(currentDate)
      val gradeID = util.Random.nextInt
      //inserts the data to the db
      Grade.toDb(Db.maybeConnection.get)(Grade(gradeID, currentAssessment.assessmentID, currentStudent.studentID, currentLecturer.lecturerID, currentTimestamp, currentPoints))
      val alert = new Alert(AlertType.INFORMATION)
      alert.setTitle("Successfully inserted")
      alert.setHeaderText(null)
      alert.setContentText("The grade has been successfully inserted.")
      alert.showAndWait()
    } catch {
      // see http://code.makery.ch/blog/javafx-dialogs-official/
      case e:java.lang.NumberFormatException => { // error with lecturerPoints.getText.toFloat or points are not between 0 and maxPoints
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

  def viewStudent(): Unit = {
    try {
      val currentStudent = studentStudentDropdown.getValue
      //when no student is selected
      if (currentStudent == null) throw new IllegalArgumentException()
      //shows the data in the label
      studentName.setText(currentStudent.studentLastname + " " + currentStudent.studentFirstname)
      studentNumber.setText(currentStudent.studentMatnum)
      //webview
      studentGrades.getEngine.loadContent("")
      //load the html report for the actual student
      val htmlReport = createReportHtmlString(currentStudent)
      studentGrades.getEngine.loadContent(htmlReport)
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

  def saveHTML() : Unit = {
    try {
      val currentStudent = studentStudentDropdown.getValue
      if (currentStudent == null) throw new IllegalArgumentException()
      val html = createReportHtmlString(currentStudent)
      //creates the html file
      val filePath = Paths.get(s"report_${currentStudent.studentLastname}_${currentStudent.studentFirstname}.html")
      Files.write(filePath, html.getBytes(StandardCharsets.UTF_8))

      val alert = new Alert(AlertType.INFORMATION)
      alert.setTitle("Report save")
      alert.setHeaderText(null)
      alert.setContentText(s"The report was saved to ${filePath.toAbsolutePath}")
      alert.showAndWait()
    } catch {
      case e:IllegalArgumentException => {
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("Student error")
        alert.setHeaderText("Student error")
        alert.setContentText("Please select a student!")
        alert.showAndWait()
      }
      case e => {
        val alert = new Alert(AlertType.ERROR)
        alert.setTitle("File error")
        alert.setHeaderText("File error")
        alert.setContentText("Error writing report to html file!")
        alert.showAndWait()
      }
    }
  }

  //creates the html file
  def createReportHtmlString(student: Student) : String = {
    val htmlStart = "<!DOCTYPE html><html>"
    //writes everything from bootstrap.min.css into the html file
    val htmlStyle = "<head><style>" + Source.fromURL(getClass.getResource("/fhj/swengb/projects/courseware/bootstrap.min.css")).mkString + "</style></head>"
    val htmlBodyStart = "<body>"
    val htmlHeadings = s"<h1>Grade report</h1><h2>${student.studentLastname} ${student.studentFirstname}</h2>"
    val htmlStudentInfo = s"<ul> <li>Last Name: ${student.studentLastname}</li> <li>First Name: ${student.studentFirstname} </li> <li>Matriculation Number: ${student.studentMatnum}</li> <li>Study programme: ${student.studentStudyprogramme}</li> </ul>"
    val htmlTableStart = "<table class=\"table table-hover table-striped\">"
    val htmlTableHead = "<thead><tr> <th>Assessment</th> <th>Points</th> <th>Max Points</th> <th>Passed or Failed</th> <th>Graded Date</th> </tr></thead>"
    val htmlTableBodyStart = "<tbody>"
    var htmlTableRows = new StringBuilder
    val selectGradesQuery = "select * from grade where studentID=" + student.studentID
    val selectedGrades = Grade.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectGradesQuery))
    if (selectedGrades.isEmpty) {
      val alert = new Alert(AlertType.INFORMATION)
      alert.setTitle("No grades")
      alert.setHeaderText(null)
      alert.setContentText("There are currently no grades for the selected student.")
      alert.showAndWait()
    }

    for (grade <- selectedGrades) {
      val selectAssessmentQuery = "select * from assessment where assessmentID=" + grade.assessmentID
      val selectLecturerQuery = "select * from lecturer where lecturerID=" + grade.lecturerID
      val assessment = Assessment.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectAssessmentQuery)).head
      val assessmentName = assessment.assessmentName
      val maxPoints = assessment.assessmentMaxPoints
      val minPointsForSufficient = assessment.assessmentMinPointsForSufficient
      val lecturer = Lecturer.fromDb(Db.maybeConnection.get.createStatement().executeQuery(selectLecturerQuery)).head
      val lecturerName = lecturer.lecturerLastname + " " + lecturer.lecturerFirstname
      val passedOrFailed = if (grade.gradePoints >= minPointsForSufficient) "-> passed" else "-> failed"
      //studentGrades.appendText(s"$assessmentName ($lecturerName): ${grade.gradePoints}/$maxPoints $failedOrPassed (minimum $minPointsForSufficient)\n")
      htmlTableRows.append(s"<tr> <td>$assessmentName ($lecturerName)</td> <td>${grade.gradePoints}</td> <td>$maxPoints</td> <td>$passedOrFailed</td> <td>${grade.gradeDate}</td></tr>")
    }

    val htmlTableBodyEnd = "</tbody>"
    val htmlTableEnd = "</table>"
    val htmlBodyEnd = "</body>"
    val htmlEnd = "</html>"
    return htmlStart+htmlStyle+htmlBodyStart+htmlHeadings+htmlStudentInfo+htmlTableStart+htmlTableHead+htmlTableBodyStart+htmlTableRows+htmlTableBodyEnd+htmlTableEnd+htmlBodyEnd+htmlEnd
  }
}