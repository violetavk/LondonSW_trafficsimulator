package londonsw.model.simulation.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;
import londonsw.view.simulation.MapGridGUIDecorator;
import org.reactfx.EventStreams;

import java.time.Duration;
import java.util.ArrayList;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Ticker t = Ticker.getInstance();

        Map map = drawTestMap();

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(9/ width,9/height));    //TODO HARDCODE

        GridPane rootGP = mapGridGUIDecorator.drawComponents();

        Lane L1 = map.getRandomLane();

        //Lane L1 = map.getRoads().get(0).getLanes().get(roadIndex);

        Car C1 = new Car(0,L1);

        CarGUIDecorator CarGUI = new CarGUIDecorator(C1);

        CarGUI.setGridPane(rootGP);

        CarGUI.setResizeFactor(mapGridGUIDecorator.getResizeFactor());

        Pane carPane = new Pane();

        CarGUI.drawCar();

        carPane.getChildren().add(CarGUI.getRectangle());

        StackPane sp = new StackPane();

        sp.getChildren().add(rootGP);

        sp.getChildren().add(carPane);

        Scene scene = new Scene(sp);

        //t.start();

        EventStreams.ticks(Duration.ofMillis(Ticker.getTickInterval()*2))   //needs to be greater than Ticker.getTickInterval
                .subscribe(
                        tick -> {
                            try {
                                VehicleController.moveVehicle(C1, CarGUI, 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        primaryStage.setResizable(false);

    }

    public Map drawTestMap() throws Exception {

        Map map = new Map(10,10);

        Road r1 = new Road(new Coordinate(1,0), new Coordinate(8,0));
        Road r2 = new Road(new Coordinate(0,1), new Coordinate(0,8));
        Road r3 = new Road(new Coordinate(9,1), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(1,9), new Coordinate(8,9));

        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST,r1));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST,r1));
        r1.addLane(new Lane(r1.getEndLocation(),r1.getStartLocation(),MapDirection.EAST.WEST,r1));

        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH,r2));
        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH,r2));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH,r3));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH,r3));

        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST,r4));
        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST,r4));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(0,0));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);
        Intersection i2 = new Intersection(new Coordinate(9,0));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(0,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public static void main(String[] args) {
        launch(args);
    }
}