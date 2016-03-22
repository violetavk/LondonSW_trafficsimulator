package londonsw.view.simulation;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.ResizeFactor;
import javafx.scene.control.Button;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Ambulance;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.Map;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import londonsw.model.simulation.components.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Optional;

public class SimulationScreen {

    private Map map;

    private int mapSceneIndex = 0;

    private int initCar = 0;

    private int flag = 0;

    private int systemState = 0;

    public SimulationScreen(Map map) {
        this.map = map;
    }

    public void drawScreen(Stage primaryStage) throws Exception {
        // the entire screen building and logic will go here
        // http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:papayawhip");

        //Title
        Label logo = new Label("LondonSW Traffic Simulation");
        logo.setFont(Font.font("System Bold Italic", FontWeight.EXTRA_BOLD, 20));
        logo.setAlignment(Pos.CENTER);
        logo.setTextAlignment(TextAlignment.CENTER);
        logo.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(logo);

        //Map
        Pane mapPane = new Pane();
        Map map = this.map;
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0 / map.getWidth(), 5.0/ map.getHeight()));
        GridPane mapGridPane = mapGridGUIDecorator.drawComponents();
        mapPane.setPadding(new Insets(0, 0, 5, 5));
        mapPane.getChildren().add(mapSceneIndex, mapGridPane);
        mapSceneIndex++;
        borderPane.setCenter(mapPane);

        //Start&Reset

        VBox simulationControl = new VBox();

        Label carNumberSituation = new Label();
        carNumberSituation.setText("There are " + String.valueOf(initCar) + " car in the system");
        simulationControl.getChildren().add(carNumberSituation);

        Button startSimulation = new Button("Start");
        Button resetSimulation = new Button("Reset");

        startSimulation.setPadding(new Insets(10, 10, 10, 10));
        startSimulation.setPrefSize(90, 30);
        simulationControl.getChildren().add(startSimulation);

        resetSimulation.setPadding(new Insets(10, 10, 10, 10));
        resetSimulation.setPrefSize(90, 30);
        simulationControl.getChildren().add(resetSimulation);
        // borderPane.setRight(simulationControl);

        //Add/Delete ambulance
        Button ambulanceAddDelete = new Button("Add/Delete Ambulance");
        ambulanceAddDelete.setPrefSize(160,30);
        simulationControl.getChildren().add(ambulanceAddDelete);

        //carSlider
        VBox sliderControl = new VBox();
        sliderControl.setPadding(new Insets(20, 20, 20, 20));
        Pane carLabel = new Pane();
        Label carNumber = new Label("Car Number");
        carLabel.getChildren().add(carNumber);
        sliderControl.getChildren().add(carLabel);

        Pane carSlider = new Pane();
        Slider slider = new Slider(1, 60, 30);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10);
        //slider.setBlockIncrement(1);

        /**
         * Using a slider control the number of cars in the system
         */
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                initCar = oldValue.intValue();
                int newCar = newValue.intValue() - oldValue.intValue();

                StackPane carStackPane = new StackPane();
                mapPane.getChildren().add(mapSceneIndex, carStackPane);
                mapSceneIndex++;
                //increase carNumber
                if (newCar >= 0) {
                    for (int i = 0; i < newCar; i++) {
                        generateCar(map, mapGridGUIDecorator, carStackPane);
                 }
                }
                //decrease carNumber
                else {

                }
                carNumberSituation.setText("There are " + String.valueOf(initCar) + " cars in the system");
            }
     });

        /**
         * "Add/Delete Ambulance" Button click control
         * the first click adds an ambulance in the system, the next click will delete the ambulance, next add...
         */
        ambulanceAddDelete.setOnMouseClicked(click -> {
            while (systemState==1) {
                System.out.println("Add/Delete ambulance");
                int ambulanceIndex = mapSceneIndex;
                if (flag == 0) {
                    StackPane ambulanceStackPane = new StackPane();
                    generateAmbulance(map, mapGridGUIDecorator, ambulanceStackPane);
                    mapPane.getChildren().add(ambulanceIndex, ambulanceStackPane);
                    mapSceneIndex++;
                    flag = 1;
                } else {
                    mapPane.getChildren().remove(ambulanceIndex);
                    flag = 0;
                }
            }
        });

        carSlider.getChildren().add(slider);
        sliderControl.getChildren().add(carSlider);

        simulationControl.getChildren().add(sliderControl);
        borderPane.setRight(simulationControl);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        //StackPane sp = new StackPane();

        /**
         * Set TickerInterval first
         */

        startSimulation.setOnMouseClicked(click->{
            TextInputDialog tickerSet = new TextInputDialog();
            tickerSet.setTitle("Set Ticker Interval");
            tickerSet.setHeaderText("Simulation Speed ");
            tickerSet.setContentText("Please input a ticker interval number between 100 and 1000:");
            Optional<String> tickerInterval = tickerSet.showAndWait();
            tickerInterval.ifPresent(systemSpeed->{
                Long speed = new Long(systemSpeed);
                System.out.println(systemSpeed);
                Ticker.setTickInterval(speed);
                System.out.println("Start Simulation");
                systemState = 1;
                StackPane carStackPane = new StackPane();
                initCar = (int) slider.getValue();
                carNumberSituation.setText("There are " + String.valueOf(initCar) + " cars in the system");
                for (int i = 0; i < initCar; i++) {
                    generateCar(map, mapGridGUIDecorator, carStackPane);
                }
                mapPane.getChildren().add(mapSceneIndex,carStackPane);
                mapSceneIndex++;
                }
            );

        });

        /**
         * To stop simulation
         */
        resetSimulation.setOnMouseClicked(click -> {
            System.out.println("Reset Simulation");
            systemState = 0;
            //StackPane sp = new StackPane();
            carNumberSituation.setText("There are 0 car in the system");
            mapPane.getChildren().remove(1, mapSceneIndex);
            mapSceneIndex = 1;
        });
    }

    /**
     * Car generator
     * @param map
     * @param mapGridGUIDecorator
     * @param sp
     * @return
     */
    public Car generateCar(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp) {

        Lane L1 = map.getRandomLane();
        //Lane L1 =map.getRoads().get(0).getLanes().get(1);

        Lane L2;

        if (L1 != null)
            for (int a = 0; a < map.getRoads().size(); a++) {
                for (int b = 0; b < map.getRoads().get(a).getNumberLanes(); b++) {
                    L2 = map.getRoads().get(a).getLanes().get(b);
                    for (int i = 0; i < L1.getLength(); i++) {
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
     * Ambulance generator
     * @param map
     * @param mapGridGUIDecorator
     * @param sp
     * @return
     */
    public Ambulance generateAmbulance(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp) {
        Lane AmbLane = map.getRandomLane();

        if (AmbLane != null && (!AmbLane.isFull())) {
            for (int x = 0; x < map.getRoads().size(); x++) {
                for (int y = 0; y < map.getRoads().get(x).getNumberLanes(); y++) {
                    AmbLane = map.getRandomLane();
                    for (int z = 0; z < AmbLane.getLength(); z++) {
                        if (AmbLane.isCellEmpty(z)) {
                            Ambulance A = new Ambulance(z, AmbLane);
                            VehicleGUIDecorator ambulanceGUIDecorator = new VehicleGUIDecorator(A);
                            ambulanceGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            ambulanceGUIDecorator.setColor(Color.RED);
                            ambulanceGUIDecorator.drawCar();
                            Pane alPane = new Pane();
                            alPane.getChildren().add(ambulanceGUIDecorator.getRectangle());
                            ambulanceGUIDecorator.setPane(alPane);
                            sp.getChildren().add(alPane);
                            ambulanceGUIDecorator.setVehicleState(1);
                            return A;

                        }
                    }

                }

            }

        }
        return null;
    }
}

