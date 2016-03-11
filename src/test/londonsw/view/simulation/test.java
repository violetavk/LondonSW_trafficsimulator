package londonsw.view.simulation;/**
 * Created by felix on 03/03/2016.
 */

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        double startPointX = 50;
        double startPointY = 50;

        double carDimensionX = 100;
        double carDimensionY = 100;

        Rectangle rectangleBackground = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX,
                carDimensionY);

        rectangleBackground.setFill(Color.TRANSPARENT);

        Rectangle rectangleCar = new Rectangle
                (
                        startPointX, startPointY,
                        carDimensionX,
                        (carDimensionY /2)
                );

        rectangleCar.setFill(Color.RED);

        Pane shadowPane = new Pane();

        shadowPane.getChildren().addAll(rectangleBackground,rectangleCar);

        Group root = new Group();

        root.getChildren().add(shadowPane);

        Scene scene = new Scene(root, 1000, 1000, Color.DARKGREEN);

        primaryStage.setTitle("PathTransition Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
