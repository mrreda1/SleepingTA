package org.SleepingTA.GUI;

import org.SleepingTA.Services;
import org.SleepingTA.GUI.Controllers.*;
import org.SleepingTA.Utils.Misc;
import org.SleepingTA.Student;
import java.net.URL;
import javafx.util.Duration;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

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
    private Label studentsWaitingCount;
    @FXML
    private Label taSleepingCount;
    @FXML
    private Label taWorkingCount;

    // Tooltips
    @FXML
    private Tooltip gardenTooltip;
    @FXML
    private Tooltip hallwayTooltip;
    @FXML
    private Tooltip taRoomTooltip;

    // Timeline for updating the UI
    private Timeline timeline;

    // Initial data to be used for to simulate the busy/free components
    private Map<String, Integer> initialData = new HashMap<String, Integer>();

    public void initialize(URL location, ResourceBundle resources) {
        // Set the default value for randomizing ta time option
        taTimeRandomizeYes.setSelected(true);

        // The number of seconds input, next to the radio buttons.
        numberOfTaWaitTime.setVisible(false);
        numberOfTaWaitTimeMsg.setVisible(false);

        var Tooltips = new Tooltip[] { gardenTooltip, hallwayTooltip, taRoomTooltip };

        // Set show/hide delays for the tooltips
        for (Tooltip tooltip : Tooltips) {
            tooltip.setShowDelay(Duration.millis(200));
            tooltip.setHideDelay(Duration.millis(200));
            tooltip.setShowDuration(Duration.INDEFINITE);
        }
    }

    @FXML
    void onStartBtn(ActionEvent event) {
        String numberOfStudentsInput = numberOfStudents.getText();
        String numberOfChairsInput = numberOfChairs.getText();
        String numberOfTaInput = numberOfTAs.getText();
        String numberOfTaWaitTimeInput = numberOfTaWaitTime.getText();
        Toggle taTimeRandomizeInput = taTimeRandomize.getSelectedToggle();

        Integer student = 0, ta = 0, chairs = 0, taWaitTime = 0;
        boolean randomize = true;
        boolean validation = true;

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
        Services.createStudentThreads(taWaitTime);

        initialData.put("students", student);
        initialData.put("chairs", chairs);
        initialData.put("tas", ta);

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateUI()));

        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    private void updateUI() {
        var data = Services.getInfo(false);

        taWorkingCount.setText(data.get("workingTA").toString());
        taSleepingCount.setText(data.get("sleepingTA").toString());
        studentsWaitingCount.setText(data.get("waitingStudents").toString());
        studentsComingLaterCount.setText(data.get("laterStudents").toString());

        FlowPaneControllers.clearFlowPane(hallway);
        FlowPaneControllers.clearFlowPane(garden);
        GridPaneController.clearGridPane(tasRoom);

        FlowPaneControllers.initializeGarden(garden, data.get("laterStudents"));
        FlowPaneControllers.initializeHallway(hallway, initialData.get("chairs"), data.get("waitingStudents"));
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
        if (this.timeline != null)
            this.timeline.stop();

        for (Thread thread : Thread.getAllStackTraces().keySet())
            if (thread.getName().startsWith("Student-") && thread instanceof Student)
                ((Student) thread).terminate();

        FlowPaneControllers.clearFlowPane(hallway);
        FlowPaneControllers.clearFlowPane(garden);
        GridPaneController.clearGridPane(tasRoom);

        // Current State outputs
        taWorkingCount.setText("0");
        taSleepingCount.setText("0");
        studentsWaitingCount.setText("0");
        studentsComingLaterCount.setText("0");
    }

    @FXML
    void onResetBtn(ActionEvent event) {
        // Validation messages
        numberOfStudentsVal.setVisible(false);
        numberOfChairsVal.setVisible(false);
        numberOfTAsVal.setVisible(false);
        numberOfTaWaitTimeVal.setVisible(false);
        taTimeRandomizeVal.setVisible(false);

        // Data Inputs
        numberOfStudents.clear();
        numberOfChairs.clear();
        numberOfTAs.clear();
        numberOfTaWaitTime.clear();
    }

    @FXML
    void onExitBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onTATimeRandomize(ActionEvent event) {

    }
}
