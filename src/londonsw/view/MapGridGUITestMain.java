package londonsw.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;
import londonsw.view.simulation.CarGUI;
import londonsw.view.simulation.MapGridGUI;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MapGridGUI m = new MapGridGUI(8,8);
        Ticker ticker = Ticker.getInstance();

        Road R1 = new Road(new Coordinate(0,5),new Coordinate(7,5));
        Lane L1 = new Lane(new Coordinate(0,5), new Coordinate(7,5), MapDirection.EAST,R1);
        CarGUI C1 = new CarGUI(0,L1);
        //Intersection I1 = new Intersection(new Coordinate(0,4));

        //Road R2 = new Road(new Coordinate(5,0),new Coordinate(5,9));
        //Lane L2 = new Lane(new Coordinate(5,0),new Coordinate(5,9),MapDirection.SOUTH);

        R1.addLane(L1);
        //R2.addLane(L2);

        m.addComponent(R1);
        //m.addComponent(I1);
        //m.addComponent(R2);

        //Create CarGUI

        //GridPane rootGP = m.drawInitialLayout();
        m.drawComponents();

        GridPane rootGP = m.getGridPane();

        Scene scene = new Scene(rootGP);

        C1.setGridPane(rootGP);

        C1.drawCar(new Coordinate(0,5));
        C1.moveCar(6);

        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}