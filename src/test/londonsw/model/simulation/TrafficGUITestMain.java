package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import londonsw.model.simulation.components.ITrafficLight;
import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightGUI;

public class TrafficGUITestMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Ticker ticker = Ticker.getInstance();
        LightColour state = LightColour.GREEN;
        ITrafficLight I = new TrafficLight();
        ticker.start();


        primaryStage.setTitle("TrafficLight Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 350, 250, null);

//        circle = new Circle();
//        circle.setCenterX(175.0f);
//        circle.setCenterY(120.0f);
//        circle.setRadius(25.0f);
//        circle.setFill(Color.RED);
        TrafficLightGUI TGUI = new TrafficLightGUI();
        Circle circle = TGUI.DrawLight();
        root.getChildren().add(circle);
        primaryStage.setScene(scene);
        primaryStage.show();

        /* TODO: Figure out how to solve seperation of functionality problem */


    }
}
