package fhj.swengb.projects.courseware

import java.io.File
import java.net.URL
import java.sql.{Connection, DriverManager}
import java.util.ResourceBundle
import javafx.application.Application
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.{FXML, FXMLLoader, Initializable}
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
  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }

  def execInitialLoad(): Unit = {
    // --------- initialize Lecturers
    Lecturer.reTable(Db.maybeConnection.get.createStatement())
    for (lecturer <- Data.lecturerList) {
      Lecturer.toDb(Db.maybeConnection.get)(lecturer)
    }
    lecturerLecturernameDropdown.getItems.addAll(Data.lecturerList)

    // --------- initialize Assessments
    Assessment.reTable(Db.maybeConnection.get.createStatement())
    for (assessment <- Data.assessmentList) {
      Assessment.toDb(Db.maybeConnection.get)(assessment)
    }
    lecturerAssessmentDropdown.getItems.addAll(Data.assessmentList)

    // --------- initialize Students
    Student.reTable(Db.maybeConnection.get.createStatement())
    for (student <- Data.studentList) {
      Student.toDb(Db.maybeConnection.get)(student)
    }
    lecturerStudentDropdown.getItems.addAll(Data.studentList)
  }

  def insertGrade(): Unit = {
    val currentLecturer = lecturerLecturernameDropdown.getValue
    if (currentLecturer != null) {

    }
  }

  def execView() {}

}