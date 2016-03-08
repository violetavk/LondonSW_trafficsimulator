package londonsw.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.layout.AnchorPane;

import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.view.simulation.VehicleGUIDecorator;

//import java.awt.*;
import java.io.IOException;

/**
 *
 */
public class StartUpController extends Application{

    public Label londonSWlabel;
    public Label trafficSimLabel;
    public Button startButton;


    @FXML private TextField width;

    @FXML private TextField height;

    public void startSoftware(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/startup/StartScreen.fxml"));
        stage.setTitle("LondonSW Traffic Simulator");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public void goToChooseModeScreen(ActionEvent actionEvent) throws IOException {
        Parent chooseModeScreen = FXMLLoader.load(getClass().getResource("../view/startup/ChooseModeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chooseModeScreen));
    }

    /**
     * when the user click "Choose Pre-made map..." button, it will go to SimulationMode screen
     * @param actionEvent click action
     * @throws Exception
     */
    public void goToSimulationMode(ActionEvent actionEvent) throws Exception {

        Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));

        Node node = simulationModeScreen.lookup("#Scene");
        Pane p = (Pane) node;

        //Create map
        Map map = new Map(20,20);

        //Decorate map to extend to GUI functionality
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        //Always apply resize
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0/map.getWidth(),5.0/map.getHeight()));


        //Instantiate GridPane that will contain empty map with grass
        GridPane root = mapGridGUIDecorator.drawComponents();


        p.getChildren().add(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(simulationModeScreen));

    }

    public void goToMapBuilderMode(ActionEvent actionEvent) throws IOException {

        Parent setMapDimension = FXMLLoader.load(getClass().getResource("../view/startup/MapDimension" + ".fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(setMapDimension));

    }

    public void goToMapCreationScreen(ActionEvent actionEvent) throws Exception {

        //String mapWidth=test


        Parent mapCreation = FXMLLoader.load(getClass().getResource("../view/mapcreation/MapCreation" + ".fxml"));
        int w = Integer.parseInt(width.getText());
        int h= Integer.parseInt(height.getText());


        Map map = new Map(w,h);
        //Decorate map to extend to GUI functionality
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        //Always apply resize
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0/map.getWidth(),5.0/map.getHeight()));
        //Instantiate GridPane that will contain empty map with grass
        GridPane root = mapGridGUIDecorator.drawComponents();

        GridPane mapGrid =(GridPane) mapCreation.lookup("mapView");
        mapGrid.getChildren().add(root);


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(mapCreation));
    }
}
