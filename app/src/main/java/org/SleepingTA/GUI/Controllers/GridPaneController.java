package org.SleepingTA.GUI.Controllers;

import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GridPaneController {
    private static Map<String, String> iconPaths = org.SleepingTA.Utils.Misc.icons;

    public static int c(int n) {
        if (n <= 4)
            return 2;
        else if (n == 8)
            return 4;
        else if (n <= 10)
            return 3;
        return (int) Math.round((float) Math.sqrt(n)) + 1;
    }

    public static void initializeTaRoom(GridPane taRoom, int numberOfTAs, int redTas) {
        double taRoomWidth = 420;
        double taRoomHeight = 300;

        double originalImageWidthHeight = 190;

        Integer columns = c(numberOfTAs);
        Integer rows = (int) Math.ceil((double) numberOfTAs / columns);

        taRoom.setPrefSize(taRoomWidth, taRoomHeight);
        taRoom.setAlignment(Pos.CENTER);
        taRoom.setHgap(10);
        taRoom.setVgap(10);

        for (int i = 0; i < numberOfTAs; i++) {
            String taIcon = i < redTas ? "redTa" : "greenTa";
            Image image = new Image(iconPaths.get(taIcon));
            ImageView imageView = new ImageView(image);

            Integer row = i / columns;
            Integer col = i % columns;

            imageView.setPreserveRatio(true);
            imageView.setFitWidth(originalImageWidthHeight / (rows));
            imageView.setFitHeight(originalImageWidthHeight / (rows));

            taRoom.add(imageView, col, row);
        }
    }

    public static void clearTaRoom(GridPane taRoom) {
        taRoom.getChildren().clear();
    }
}
