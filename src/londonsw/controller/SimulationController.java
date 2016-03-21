package londonsw.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.*;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.view.simulation.SimulationScreen;
import londonsw.view.simulation.VehicleGUIDecorator;

import java.io.File;

public class SimulationController {

    private Stage primaryStage;
    private String mapName;

    public SimulationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void drawScreen() throws Exception {
        Map map = Map.loadMap(mapName);
        SimulationScreen screen = new SimulationScreen(map);
        screen.drawScreen(primaryStage);
    }

}
