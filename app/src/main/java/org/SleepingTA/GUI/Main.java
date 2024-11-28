package org.SleepingTA.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, World!");
        Scene scene = new Scene(label, 300, 200);

        stage.setTitle("Hello, JavaFX!");

        stage.setScene(scene);
        stage.show();
    }

    public static void launchGUI() {
        Platform.runLater(() -> {
            launch();
        });
    }
}
