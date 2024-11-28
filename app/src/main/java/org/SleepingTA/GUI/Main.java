package org.SleepingTA.GUI;

import org.SleepingTA.App;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // System.out.println(App.class.getResource("/fxml/main.fxml")); // works fine!
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1020, 720);

        stage.setTitle("The Students and their TAs!");
        stage.setScene(scene);
        stage.show();
    }

    public static void launchGUI() {
        Platform.runLater(() -> {
            launch();
        });
    }
}
