package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.components.ITrafficLight;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightGUI;

/**
 * Separated GUI Logic from  test functionality.
 */
public class TrafficGUITestMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Ticker ticker = Ticker.getInstance();
        //ITrafficLight I = new TrafficLight();
        ticker.start();

        primaryStage.setTitle("TrafficLight Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 350, 250, null);
        /*
        circle = new Circle();
        circle.setCenterX(175.0f);
        circle.setCenterY(120.0f);
        circle.setRadius(25.0f);
        circle.setFill(Color.RED);
        */
        TrafficLightGUI GUI = new TrafficLightGUI();
        TrafficLightController con = new TrafficLightController();
        Circle circle = GUI.DrawLight(175.0f, 120.0f, 25.0f);
        //created new light but controller isnt able to simultaneously control both.Think it has something to do with threads.
        //Circle circle1 = GUI.DrawLight(262.50f, 120.0f);
        con.setCircle(circle);
        //con.setCircle(circle1);

        root.getChildren().add(circle);
        //root.getChildren().add(circle1);
        primaryStage.setScene(scene);
        primaryStage.show();
        /*
        Thread.sleep(100000);
        ticker.end();
        */
        
    }
}
