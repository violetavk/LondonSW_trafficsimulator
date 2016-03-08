package londonsw.view.simulation;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import londonsw.controller.IntersectionController;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;


public class IntersectionDecoratorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Ticker ticker = Ticker.getInstance();

        Intersection intersection = new Intersection(new Coordinate(2, 2));
        TrafficLight t1 = new TrafficLight();
        TrafficLight t2 = new TrafficLight();
        TrafficLight t3 = new TrafficLight(LightColour.GREEN);
        TrafficLight t4 = new TrafficLight(LightColour.GREEN);
        intersection.setNorthTrafficLight(t1);
        intersection.setSouthTrafficLight(t2);
        intersection.setWestTrafficLight(t3);
        intersection.setEastTrafficLight(t4);

        IntersectionDecorator gui = new IntersectionDecorator(intersection);
        gui.setResizeFactor(new ResizeFactor(.5,.5));

        StackPane stackPane = gui.drawIntersection();
        gui.showIntersection(primaryStage,stackPane);
    }
}