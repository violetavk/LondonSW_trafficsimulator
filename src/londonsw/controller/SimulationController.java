package londonsw.controller;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
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
import javafx.scene.control.Label;

import java.io.File;

import static java.lang.String.*;

public class SimulationController extends Application{

    @FXML public String mapName;
public static void main(String[] args){
    launch(args);
}

   // @FXML public MapLoadingController main;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));
        Pane  pane= (Pane) simulationModeScreen.lookup("#Scene");

        Slider carSlider= (Slider) simulationModeScreen.lookup("#carSlider");
        carSlider.setMax(60);
        carSlider.setMin(1);
        carSlider.setValue(20);
        carSlider.setShowTickLabels(true);
        carSlider.setShowTickMarks(true);
        carSlider.setMajorTickUnit(30);
        carSlider.setMinorTickCount(5);
        carSlider.setBlockIncrement(5);
        pane.getChildren().add(carSlider);
        Label carLabel= (Label) simulationModeScreen.lookup("#carLabel");
        carSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(carSlider.getValue());
                carLabel.setText(String.format("%.1f",newValue));
            }
        });
        pane.getChildren().add(carSlider);
        pane.getChildren().add(carLabel);
        //pane.getChildren().addAll(carSlider,carLabel);

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();


    }

    /**
     * this is start simulation trigger
     * when "start" button is clicked, simulation will start
     * @param actionEvent
     * @throws Exception
     */
    public void StartSimulation(ActionEvent actionEvent) throws Exception {

       //Map map = Map.loadMap(main.getMapNameFromStartUp());

        //Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));
        //Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));
        //Pane  pane= (Pane) simulationModeScreen.lookup("#Scene");

        //pane.setVisible(false);

        Map map = Map.loadMap("drawMap1.map");

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(.25,.25));

        GridPane rootGP = mapGridGUIDecorator.drawComponents();

        StackPane sp = new StackPane();
        sp.getChildren().add(rootGP);


        generateCar(map,mapGridGUIDecorator,sp);

        //pane.getChildren().add(sp);

    }

    public Car generateCar(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp){

        Lane L1 =map.getRandomLane();
        //Lane L1 =map.getRoads().get(0).getLanes().get(1);

        Lane L2;

        if(L1!=null)
            for (int a=0; a<map.getRoads().size();a++)
            {
                for(int b=0; b<map.getRoads().get(a).getNumberLanes();b++)
                {
                    L2= map.getRoads().get(a).getLanes().get(b);
                    for (int i=0; i<L1.getLength();i++) {
                        if (L1.isCellEmpty(i)) {
                            Car C1 = new Car(i, L1);
                            //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
                            VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
                            vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            vehicleGUIDecorator.drawCar();
                            Pane carPane = new Pane();

                            carPane.setPickOnBounds(false); //allows me to click intersections

                            carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
                            //carPane.getChildren().add(vehicleGUIDecorator.getGroup());
                            sp.getChildren().add(carPane);
                            vehicleGUIDecorator.setPane(carPane);
                            vehicleGUIDecorator.setVehicleState(1);
                            System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                            return C1;

                        }
                    }
                }
            }

        return null;

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



}
