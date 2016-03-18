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
import londonsw.view.simulation.VehicleGUIDecorator;

import java.io.File;

public class SimulationController extends Application{

    @FXML public String mapName;

    @FXML public MapLoadingController main;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    /**
     * this is start simulation trigger
     * when "start" button is clicked, simulation will start
     * @param actionEvent
     * @throws Exception
     */
    public void StartSimulation(ActionEvent actionEvent) throws Exception {

       //Map map = Map.loadMap(main.getMapNameFromStartUp());
        Map map= new Map(25,25);

        Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));
        Node node = simulationModeScreen.lookup("#Scene");

        Pane p = (Pane) node;

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor((map.getWidth()/5) / width, (map.getHeight()/5) / height));


        GridPane rootGP = mapGridGUIDecorator.drawComponents();


        Lane L1 = map.getRoads().get(0).getLanes().get(0);

        Car C1 = new Car(0, L1);

        VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);

        vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());

        vehicleGUIDecorator.drawCar();

        Pane carPane = new Pane();

        carPane.getChildren().add(vehicleGUIDecorator.getRectangle());

        p.getChildren().add(rootGP);

        p.getChildren().add(carPane);


        vehicleGUIDecorator.setVehicleState(1);

        System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle("Map Layout");
        stage.setScene(new Scene(simulationModeScreen));
    }

    /**
     * When the user click "reset" button, simulation stop, a default map will be set on the simulation screen
     * @param actionEvent
     * @throws Exception
     */
    public void resetSimulation(ActionEvent actionEvent)throws Exception {
        Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));

        Node node = simulationModeScreen.lookup("#Scene");
        Pane p = (Pane) node;

        //Create map
        //Map map = new Map(20,20);
        Map map=Map.loadMap(mapName);

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

    public void init(MapLoadingController mapLoadingController) {
        main=mapLoadingController;
    }

}
