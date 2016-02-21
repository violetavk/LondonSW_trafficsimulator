package londonsw.view;

import javafx.application.Application;
import javafx.stage.Stage;
import londonsw.model.simulation.components.*;
import londonsw.view.simulation.MapGridGUI;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MapGridGUI m = new MapGridGUI(10,10);

        Road R1 = new Road(new Coordinate(0,0),new Coordinate(9,0));
        Lane L1 = new Lane(new Coordinate(0,0), new Coordinate(9,0), MapDirection.EAST);
        //Intersection I1 = new Intersection(new Coordinate(0,4));

        //Road R2 = new Road(new Coordinate(5,0),new Coordinate(5,9));
        //Lane L2 = new Lane(new Coordinate(5,0),new Coordinate(5,9),MapDirection.SOUTH);

        R1.addLane(L1);
        //R2.addLane(L2);

        m.addComponent(R1);
        //m.addComponent(I1);
        //m.addComponent(R2);

        m.drawComponents(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}