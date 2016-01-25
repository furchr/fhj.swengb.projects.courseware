package fhj.swengb.projects.courseware

import java.io.File
import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.TreeItem.TreeModificationEvent
import javafx.scene.control.{TextArea, ScrollPane, TreeItem, TreeView}
import javafx.scene.input.{MouseButton, MouseEvent}
import javafx.scene.layout.{AnchorPane, BorderPane}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.JavaConversions._
import scala.util.control.NonFatal


/**
  * Created by neX on 25.01.2016.
  */
object CoursewareApp {
  def main (args: Array[String]) {
    Application.launch(classOf[CoursewareApp], args: _*)
  }
}

class CoursewareApp extends javafx.application.Application {
  val Fxml = "/fhj/swengb/projects/courseware/Courseware.fxml"
  // FXML Pfadvariable
  //val Css = "/fhj/swengb/projects/courseware/Courseware.css"
  // FXML Pfadvariable
  val loader = new FXMLLoader(getClass.getResource(Fxml)) // FXML Loader Funktion mit Variable aufrufen
  override def start(stage: Stage): Unit = try {
      stage.setTitle("Courseware") // Titelleiste in Anzeigefenster beschriften
      loader.load[Parent]()
      val scene = new Scene(loader.getRoot[Parent]) // neue Scene aufrufen
      stage.setScene(scene)
      //stage.getScene.getStylesheets.add(Css)
      stage.show()

    } catch { // Errorhandling für FXML gui
      case NonFatal(e) => {
        e.printStackTrace()
      }
    }

}