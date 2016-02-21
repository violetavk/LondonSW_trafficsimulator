package londonsw.view.simulation;

import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import londonsw.model.simulation.components.ITrafficLight;
import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;

public class TrafficLightAnimation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        LightColour state = LightColour.GREEN;
        ITrafficLight I = new TrafficLight();
        I.setDuration(10000);


        primaryStage.setTitle("TrafficLight Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 350, 250, null);

        Circle circle = new Circle();
        circle.setCenterX(175.0f);
        circle.setCenterY(120.0f);
        circle.setRadius(25.0f);

        FillTransition ft = new FillTransition();
        ft.setDuration(Duration.millis(I.getDuration()));
        ft.setShape(circle);
        ft.setRate(5);

        switch (state){

            case GREEN:

                ft.setFromValue(Color.GREEN);
                ft.setToValue(Color.RED);
                break;

            case RED:
                ft.setFromValue(Color.RED);
                ft.setToValue(Color.GREEN);
                break;
        }

        ft.play();
        root.getChildren().add(circle);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

