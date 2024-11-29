package org.SleepingTA.GUI;

import org.SleepingTA.Services;
import org.SleepingTA.GUI.Controllers.*;
import org.SleepingTA.Utils.Misc;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Controller implements Initializable {

    // Buttons
    @FXML
    private Button resetBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private Button exitBtn;

    // Boxes
    @FXML
    private FlowPane garden;
    @FXML
    private FlowPane hallway;
    @FXML
    private GridPane tasRoom;

    // Data Inputs
    @FXML
    private TextField numberOfChairs;
    @FXML
    private Label numberOfChairsVal;
    @FXML
    private TextField numberOfStudents;
    @FXML
    private Label numberOfStudentsVal;
    @FXML
    private TextField numberOfTAs;
    @FXML
    private Label numberOfTAsVal;

    // Radio Button Group
    @FXML
    private ToggleGroup taTimeRandomize;
    @FXML
    private RadioButton taTimeRandomizeYes;
    @FXML
    private RadioButton taTimeRandomizeNo;
    @FXML
    private Label taTimeRandomizeVal;

    // Seconds input
    @FXML
    private TextField numberOfTaWaitTime;
    @FXML
    private Label numberOfTaWaitTimeVal;
    @FXML
    private Label numberOfTaWaitTimeMsg;

    // Current State outputs
    @FXML
    private Label studentsComingLaterCount;
    @FXML
    private Label studentsWorkingCount;
    @FXML
    private Label taSleepingCount;
    @FXML
    private Label taWorkingCount;

    private Map<String, Integer> initialData = new HashMap<String, Integer>();

    private Timeline timeline;

    public void initialize(URL location, ResourceBundle resources) {
        taTimeRandomizeYes.setSelected(true);
        // The number of seconds input, next to the radio buttons.
        numberOfTaWaitTime.setVisible(false);
        numberOfTaWaitTimeMsg.setVisible(false);
    }

    @SuppressWarnings("unused")
    @FXML
    void onStartBtn(ActionEvent event) {
        boolean validation = true;
        String numberOfStudentsInput = numberOfStudents.getText();
        String numberOfChairsInput = numberOfChairs.getText();
        String numberOfTaInput = numberOfTAs.getText();
        String numberOfTaWaitTimeInput = numberOfTaWaitTime.getText();
        Toggle taTimeRandomizeInput = taTimeRandomize.getSelectedToggle();

        Integer student = 0, ta = 0, chairs = 0, taWaitTime;
        boolean randomize = true;

        if (!Misc.isInteger(numberOfStudentsInput)) {
            numberOfStudentsVal.setVisible(true);
            validation = false;
        } else {
            numberOfStudentsVal.setVisible(false);
            student = Integer.parseInt(numberOfStudentsInput);
        }

        if (!Misc.isInteger(numberOfChairsInput)) {
            numberOfChairsVal.setVisible(true);
            validation = false;
        } else {
            numberOfChairsVal.setVisible(false);
            chairs = Integer.parseInt(numberOfChairsInput);
        }

        if (!Misc.isInteger(numberOfTaInput)) {
            numberOfTAsVal.setVisible(true);
            validation = false;
        } else {
            numberOfTAsVal.setVisible(false);
            ta = Integer.parseInt(numberOfTaInput);
        }

        if (taTimeRandomizeInput == null) {
            taTimeRandomizeVal.setVisible(true);
            validation = false;
        } else {
            taTimeRandomizeVal.setVisible(false);
            randomize = taTimeRandomizeYes.isSelected();

            if (!randomize) {
                if (!Misc.isInteger(numberOfTaWaitTimeInput)) {
                    numberOfTaWaitTimeVal.setVisible(true);
                    validation = false;
                } else {
                    numberOfTaWaitTimeVal.setVisible(false);
                    taWaitTime = Integer.parseInt(numberOfTaWaitTimeInput);
                }
            }
        }

        if (!validation)
            return;

        Services.takeUserInput(student, chairs, ta);
        Services.createStudentThreads(0);

        initialData.put("students", student);
        initialData.put("chairs", chairs);
        initialData.put("tas", ta);

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateUI()));

        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void updateUI() {
        var data = Services.getInfo(false);

        FlowPaneControllers.clearFlowPane(hallway);
        FlowPaneControllers.clearFlowPane(garden);
        GridPaneController.clearTaRoom(tasRoom);

        FlowPaneControllers.initializeGarden(garden, data.get("laterStudents"));
        FlowPaneControllers.initializeHallway(hallway, initialData.get("chairs"), data.get("waitingStudents"));

        System.out.println(data.get("workingTA"));
        System.out.println(initialData.get("tas"));
        GridPaneController.initializeTaRoom(tasRoom, initialData.get("tas"), data.get("workingTA"));
    }

    @FXML
    void onTimeRandomize(ActionEvent event) {
        if (taTimeRandomizeNo.isSelected()) {
            numberOfTaWaitTime.setVisible(true);
            numberOfTaWaitTimeMsg.setVisible(true);
        } else {
            numberOfTaWaitTime.setVisible(false);
            numberOfTaWaitTimeMsg.setVisible(false);
        }
    }

    @FXML
    void onStopBtn(ActionEvent event) {
        this.onResetBtn(event);
        this.timeline.stop();
    }

    @FXML
    void onResetBtn(ActionEvent event) {
        FlowPaneControllers.clearFlowPane(hallway);
        FlowPaneControllers.clearFlowPane(garden);
        GridPaneController.clearTaRoom(tasRoom);

        numberOfStudentsVal.setVisible(false);
        numberOfChairsVal.setVisible(false);
        numberOfTAsVal.setVisible(false);
        numberOfTaWaitTimeVal.setVisible(false);
        taTimeRandomizeVal.setVisible(false);
    }

    @FXML
    void onExitBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onTATimeRandomize(ActionEvent event) {

    }
}
