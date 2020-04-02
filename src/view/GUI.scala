package view

import javafx.event.ActionEvent
import model.DecisionTree
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.GridPane

import scala.collection.JavaConverters._


object GUI extends JFXApp {
  val dataFile: String = "data/animals.csv"
  val questionsFile: String = "data/questions.csv"

  val decisionTree: DecisionTree = new DecisionTree(dataFile, questionsFile)

  val questionLabel: Label = new Label(decisionTree.currentQuestionText())
  val paddingW: Label = new Label("")

  class GUIButton(val answer: Boolean) extends Button {
    minHeight = 50
    minWidth = 200
    onAction = (_: ActionEvent) => {
      if(decisionTree.decision() != ""){
        decisionTree.restart()
      }else {
        decisionTree.submitAnswer(answer)
      }
      if(decisionTree.currentQuestionText() == ""){
        questionLabel.text = "It's a " + decisionTree.decision()
      }else {
        questionLabel.text = decisionTree.currentQuestionText()
      }
    }
  }

  val yesButton: Button = new GUIButton(true) {
    text = "Yes"
  }

  val noButton: Button = new GUIButton(false) {
    text = "No"
  }

  val container: GridPane = new GridPane {
    hgap = 10.0
    vgap = 5.0
    add(questionLabel, 0, 0, 2, 1)
    add(yesButton, 0, 1, 1, 1)
    add(noButton, 1, 1, 1, 1)
  }

  container.children.asScala.foreach(_.setStyle("-fx-font: 24 Arial;"))

  this.stage = new PrimaryStage {
    title = "Guessing Game"
    scene = new Scene(container) {
    }
  }
}