package londonsw.view.simulation;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightDecorator;

/**
 * Separated GUI Logic from  test functionality.
 */
public class TrafficLightDecoratorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Ticker ticker = Ticker.getInstance();

        TrafficLight t1 = new TrafficLight();

        TrafficLightDecorator gui1 = new TrafficLightDecorator(t1);

        primaryStage.setTitle("TrafficLight Animation");
        Group root1 = new Group();
        Scene scene = new Scene(root1, 350, 250, null);
        Circle circle = gui1.drawLight(175.0f, 120.0f, 25.0f);
        root1.getChildren().add(circle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
