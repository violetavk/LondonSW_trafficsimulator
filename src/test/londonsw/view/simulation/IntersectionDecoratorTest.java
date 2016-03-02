package londonsw.view.simulation;

import javafx.application.Application;
import javafx.stage.Stage;
import londonsw.controller.IntersectionController;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.TrafficLight;


public class IntersectionDecoratorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Ticker ticker = Ticker.getInstance();
        TrafficLightController trafficLightController = TrafficLightController.getInstance();
        IntersectionController intersectionController = IntersectionController.getInstance();
        ticker.start();

        Intersection intersection = new Intersection(new Coordinate(2, 2));
        TrafficLight t1 = new TrafficLight();
        TrafficLight t2 = new TrafficLight();
        TrafficLight t3 = new TrafficLight();
//        TrafficLight t4 = new TrafficLight();
        intersection.setNorthTrafficLight(t1);
        intersection.setSouthTrafficLight(t2);
        intersection.setWestTrafficLight(t3);
//
        IntersectionDecorator gui = new IntersectionDecorator(intersection);
        gui.showIntersection(primaryStage);
    }
}