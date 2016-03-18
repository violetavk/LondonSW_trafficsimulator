package londonsw.view.simulation;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import londonsw.model.simulation.Map;

public class SimulationScreen {

    private Map map;

    public SimulationScreen(Map map) {
        this.map = map;
    }

    public void drawScreen(Stage primaryStage) throws Exception {
        // the entire screen building and logic will go here
        // http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm

        BorderPane borderPane = new BorderPane();

//        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        // resize factor & stuff
//        mapGridGUIDecorator.drawComponents();

        // TODO stuff here




        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}
