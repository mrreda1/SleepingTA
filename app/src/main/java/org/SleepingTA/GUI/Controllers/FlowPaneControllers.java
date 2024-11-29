package org.SleepingTA.GUI.Controllers;

import java.util.Map;

import org.SleepingTA.App;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class FlowPaneControllers {
    private static Map<String, String> iconPaths = org.SleepingTA.Utils.Misc.icons;

    public static void initializeGarden(FlowPane garden, double numberOfStudents) {
        double gardenWidth = 500;
        double gardenHeight = 100;

        garden.setPrefSize(gardenWidth, gardenHeight);
        garden.setAlignment(Pos.CENTER);

        for (int i = 0; i < numberOfStudents; i++) {
            Image image = new Image(App.class.getResourceAsStream(iconPaths.get("student")));
            ImageView imageView = new ImageView(image);

            double originalImageWidth = 90;
            double originalImageHeight = 200;
            double originalImageSizeRatio = originalImageWidth / originalImageHeight;

            double studentImageWidth = originalImageWidth / 2.4;
            double studentImageHeight = studentImageWidth / originalImageSizeRatio;
            double studentsImageSizeThreshold = 13;

            // Reduce the size of the image if number of students is more than
            // `studentsImageSizeThreshold`
            if (numberOfStudents * studentImageWidth > gardenWidth) {
                studentImageWidth *= studentsImageSizeThreshold / numberOfStudents;
                studentImageHeight *= studentsImageSizeThreshold / numberOfStudents;
            }

            imageView.setPreserveRatio(true);
            imageView.setFitWidth(studentImageWidth);
            imageView.setFitHeight(studentImageHeight);

            garden.getChildren().add(imageView);
        }
    }

    public static void initializeHallway(FlowPane hallway, double numberOfChairs, double redChairs) {
        double hallwayWidth = 500;
        double hallwayHeight = 100;

        hallway.setPrefSize(hallwayWidth, hallwayHeight);
        hallway.setAlignment(Pos.CENTER);

        for (int i = 0; i < numberOfChairs; i++) {
            String chairIcon = i < redChairs ? "redChair" : "greenChair";
            Image image = new Image(App.class.getResourceAsStream(iconPaths.get(chairIcon)));
            ImageView imageView = new ImageView(image);

            double originalImageWidth = 356 / 2.8;
            double originalImageHeight = 480 / 2.8;
            double originalImageSizeRatio = originalImageWidth / originalImageHeight;

            double chairImageWidth = originalImageWidth / 2.4;
            double chairImageHeight = chairImageWidth / originalImageSizeRatio;
            double chairImageSizeThreshold = 9;

            // Reduce the size of the image if number of students is more than
            // `chairImageSizeThreshold`
            if (numberOfChairs * chairImageWidth > hallwayWidth) {
                chairImageWidth *= chairImageSizeThreshold / numberOfChairs;
                chairImageHeight *= chairImageSizeThreshold / numberOfChairs;
            }

            imageView.setPreserveRatio(true);
            imageView.setFitWidth(chairImageWidth);
            imageView.setFitHeight(chairImageHeight);

            hallway.getChildren().add(imageView);
        }
    }

    public static void clearFlowPane(FlowPane flowPane) {
        flowPane.getChildren().clear();
    }
}
