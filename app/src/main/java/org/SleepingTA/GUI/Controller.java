package org.SleepingTA.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Controller implements Initializable {

    @FXML
    private HBox garden;

    @FXML
    private HBox hallway;

    @FXML
    private TextField numberOfChairs;

    @FXML
    private TextField numberOfStudents;

    @FXML
    private TextField numberOfTAs;

    @FXML
    private Button resetBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Label studentsComingLaterCount;

    @FXML
    private Label studentsWorkingCount;

    @FXML
    private Label taSleepingCount;

    @FXML
    private ToggleGroup taTimeRandomize;

    @FXML
    private Label taWorkingCount;

    @FXML
    private GridPane tasRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onResetBtn(ActionEvent event) {

    }

    @FXML
    void onStartBtn(ActionEvent event) {

    }

    @FXML
    void onStopBtn(ActionEvent event) {

    }

    @FXML
    void onTATimeRandomize(ActionEvent event) {

    }
}
