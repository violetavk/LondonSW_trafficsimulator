package londonsw.view.simulation;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import londonsw.controller.TrafficLightController;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.ResizeFactor;
import javafx.scene.text.FontWeight;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.Road;
import londonsw.model.simulation.components.vehicles.Ambulance;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import rx.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


@SuppressWarnings("Duplicates")
public class SimulationScreen {

    private Map map;
    private int initCar = 0;
    private int flag = 0;
    private int systemState = 0;
    private int maxCarSize;
    Subscriber<Long> timeLabelSubscriber;

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
        StackPane mapStackPane = new StackPane();
        Map map = this.map;
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        ResizeFactor rf = ResizeFactor.getSuggestedResizeFactor(map.getWidth(), map.getHeight());
        mapGridGUIDecorator.setResizeFactor(rf);
        GridPane mapGridPane = mapGridGUIDecorator.drawComponents();
        mapStackPane.setPadding(new Insets(0, 0, 5, 5));

        mapStackPane.getChildren().add(mapGridPane);
        borderPane.setCenter(mapStackPane);

        //Start&Reset
        VBox simulationControl = new VBox();

        simulationControl.setPadding(new Insets(10,10,10,10));
        simulationControl.setSpacing(10);
        simulationControl.setAlignment(Pos.TOP_CENTER);

        Label carNumberSituation = new Label();
        carNumberSituation.setFont(Font.font("System Bold Italic",FontWeight.BOLD,13));
        carNumberSituation.setText("Number of cars: " + String.valueOf(initCar));
        simulationControl.getChildren().add(carNumberSituation);

        Label tickerSituation = new Label();
        tickerSituation.setFont(Font.font("System Bold Italic",FontWeight.BOLD,13));
        tickerSituation.setText("Ticker Interval: " + Ticker.getTickInterval() + " ms");
        simulationControl.getChildren().add(tickerSituation);

        Label trafficLightLabel = new Label();
        trafficLightLabel.setFont(Font.font("System Bold Italic",FontWeight.BOLD,13));
        trafficLightLabel.setText("Traffic Light Duration: " + TrafficLightController.getInstance().getDurationLength()/1000 + " ticks");
        simulationControl.getChildren().add(trafficLightLabel);

        Label timeLabel = new Label();
        timeLabel.setFont(Font.font("System Bold Italic",FontWeight.BOLD,13));
        timeLabel.setText("Times ticked: 0");
        simulationControl.getChildren().add(timeLabel);

        Button startSimulation = new Button("Start");
        startSimulation.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 13));
        startSimulation.setStyle("-fx-base:Gold");

        Button resetSimulation = new Button("Reset");
        resetSimulation.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 13));
        resetSimulation.setStyle("-fx-base:Gold");

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 16));
        backButton.setStyle("-fx-base:Gold");

        startSimulation.setPadding(new Insets(10, 10, 10, 10));
        startSimulation.setPrefSize(90, 30);
        simulationControl.getChildren().add(startSimulation);

        resetSimulation.setPadding(new Insets(10, 10, 10, 10));
        resetSimulation.setPrefSize(90, 30);
        simulationControl.getChildren().add(resetSimulation);
        resetSimulation.setDisable(true);

        Button ambulanceAddDelete = new Button("Add/Delete Ambulance");
        ambulanceAddDelete.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 13));
        ambulanceAddDelete.setStyle("-fx-base:Gold");
        ambulanceAddDelete.setPrefSize(180, 30);
        simulationControl.getChildren().add(ambulanceAddDelete);

        Button trafficLightInterval = new Button("Set Traffic Light Duration");
        trafficLightInterval.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 13));
        trafficLightInterval.setStyle("-fx-base:Gold");
        trafficLightInterval.setPrefSize(180, 30);
        simulationControl.getChildren().add(trafficLightInterval);

        //carSlider
        VBox sliderControl = new VBox();
        sliderControl.setPadding(new Insets(10, 10, 10, 10));
        Pane carLabel = new Pane();
        Label carNumber = new Label("Car Number");
        carNumber.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 13));
        carLabel.getChildren().add(carNumber);
        sliderControl.getChildren().add(carLabel);

        Pane carSlider = new Pane();
        carSlider.setPadding(new Insets(10,10,10,10));
        Slider slider = new Slider();
        int maxSize = determineMaxCarSize(map);
        slider.setPrefWidth(250);
        slider.setMax(maxSize);
        slider.setMin(1);
        slider.setDisable(true);
        slider.setShowTickMarks(true);
        if(maxSize <= 20) {
            slider.setMajorTickUnit(5);
            slider.setMinorTickCount(2);
            slider.setBlockIncrement(1);
        }
        else if(maxSize <= 50) {
            slider.setMajorTickUnit(10);
            slider.setMinorTickCount(4);
            slider.setBlockIncrement(1);
        }
        else if(maxSize <= 100) {
            slider.setMajorTickUnit(20);
            slider.setMinorTickCount(10);
            slider.setBlockIncrement(1);
        }
        else {
            slider.setMajorTickUnit(20);
            slider.setMinorTickCount(2);
            slider.setBlockIncrement(1);
        }

        carSlider.getChildren().add(slider);
        sliderControl.getChildren().add(carSlider);
        simulationControl.getChildren().add(sliderControl);
        simulationControl.getChildren().add(backButton);
        borderPane.setRight(simulationControl);

        /**
         * Back to ChooseSimulationMode Screen
         */
        backButton.setOnMouseClicked(click->{
            ArrayList<Vehicle> vehicles = VehicleController.getVehicleList();
            int size = vehicles.size();
            for(int i = 0; i < size; i++) {
                VehicleController.removeVehicle(0);
            }
            Ticker.end();
            try {
                Parent chooseModeScreen = FXMLLoader.load(getClass().getResource("../startup/ChooseModeScreen.fxml"));
                primaryStage.setScene(new Scene(chooseModeScreen));
                primaryStage.centerOnScreen();
                primaryStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /**
         * Allows the user to change the traffic light interval
         */
        trafficLightInterval.setOnMouseClicked(click -> {
            Dialog<Long> dialog = new Dialog<Long>();
            dialog.setTitle("Choose Traffic Light Duration");
            dialog.setHeaderText("Choose a duration (in milliseconds) for\nthe traffic lights in the system.");
            dialog.setGraphic(null);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 80, 10, 10));
            grid.add(new Label("Duration: "), 0, 0);
            Spinner<Double> spinner = new Spinner<Double>(1000, 10000, TrafficLightController.getInstance().getDurationLength(), 1000);
            grid.add(spinner, 1, 0);
            dialog.getDialogPane().setContent(grid);
            Platform.runLater(() -> spinner.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if(dialogButton == ButtonType.OK) {
                    double value =  spinner.getValue();
                    return (long) value;
                }
                return null;
            });

            Optional<Long> result = dialog.showAndWait();
            result.ifPresent((aLong -> {
                TrafficLightController.getInstance().setDurationLength(aLong);
                TrafficLightController.getInstance().setTrafficLightDuration(aLong);
                trafficLightLabel.setText("Traffic Light Duration: " + TrafficLightController.getInstance().getDurationLength()/1000 + " ticks");
            }));

        });

        /**
         * Using a slider control the number of cars in the system
         */
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initCar = oldValue.intValue();
                int newCar = newValue.intValue() - oldValue.intValue();

                //increase carNumber
                if (newCar >= 0) {
                    for (int i = 0; i < newCar; i++) {
                        generateCar(map, mapGridGUIDecorator, mapStackPane);
                    }
                }
                //decrease carNumber
                else {
                    int toDelete = newCar * -1;
                    for(int i = 0; i < toDelete; i++) {
                        ArrayList<Vehicle> vehicles = VehicleController.getVehicleList();
                        if(vehicles.size() == 0) return;
                        Random rand = new Random();
                        int min = 0;
                        int max = vehicles.size();
                        int randomIndex = rand.nextInt((max - min)) + min;
                        VehicleController.removeVehicle(randomIndex);
                    }
                }
                ArrayList<Vehicle> vehicles = VehicleController.getVehicleList();
                carNumberSituation.setText("Number of cars: " + vehicles.size());
            }
        });

        /**
         * "Add/Delete Ambulance" Button click control
         * the first click adds an ambulance in the system, the next click will delete the ambulance, next add...
         */
        ambulanceAddDelete.setOnMouseClicked(click -> {
                if (flag == 0) {

                    StackPane ambulanceStackPane = new StackPane();
                    generateAmbulance(map, mapGridGUIDecorator, ambulanceStackPane);
                    mapStackPane.getChildren().add(ambulanceStackPane);
                    ambulanceAddDelete.setText("DELETE");

                    flag = 1;
                } else {
                    //mapPane.getChildren().remove(ambulanceIndex);
                    //mapPane.getChildren().remove(ambulanceIndex);
                    flag = 0;

                    ambulanceAddDelete.setText("ADD");

                }
        });

        borderPane.setPickOnBounds(false);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        /**
         * Starts the simulation
         */
        startSimulation.setOnMouseClicked(click->{

            systemState = 1;
            slider.setDisable(false);
            initCar = (int) slider.getValue();
            carNumberSituation.setText("Number of cars: " + initCar);

            for (int i = 0; i < initCar; i++) {
                generateCar(map, mapGridGUIDecorator, mapStackPane);
            }

            startSimulation.setDisable(true);
            resetSimulation.setDisable(false);
            Platform.runLater(() -> slider.requestFocus());
            startTimeLabelTicker(timeLabel);
        });


        /**
         * To stop simulation
         */
        resetSimulation.setOnMouseClicked(click -> {
            systemState = 0;
            slider.setDisable(true);
            startSimulation.setDisable(false);
            resetSimulation.setDisable(true);

            ArrayList<Vehicle> vehicles = VehicleController.getVehicleList();
            int size = vehicles.size();
            for(int i = 0; i < size; i++) {
                VehicleController.removeVehicle(0);
            }
            carNumberSituation.setText("Number of cars: " + VehicleController.getVehicleList().size());
            Platform.runLater(() -> startSimulation.requestFocus());
            endTimeLabelTicker();
            timeLabel.setText("Times ticked: 0");
        });
    }

    /**
     * Creates a subscriber that listens to the ticker to update the "times ticked" label
     * @param timeLabel the label to update on every tick
     */
    private void startTimeLabelTicker(Label timeLabel) {
        timeLabelSubscriber = new Subscriber<Long>() {
            int timesTicked = 0;
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Long aLong) {
                timeLabel.setText("Times ticked: " + timesTicked);
                timesTicked++;
            }
        };
        Ticker.subscribe(timeLabelSubscriber);
    }

    /**
     * Stops the time ticker label from listening to the ticker
     */
    private void endTimeLabelTicker() {
        timeLabelSubscriber.unsubscribe();
    }


    /**
     * Determines the maximum number of cars that should go in the system
     * @param map the map the simulation is happening on
     * @return an upper bound on the number of cars that should be in the system
     */
    public int determineMaxCarSize(Map map){
        int numberSlots = 0;
        ArrayList<Road> roads = map.getRoads();
        for(Road i:roads){
            ArrayList<Lane> lanes = i.getLanes();
            for(Lane l:lanes){
                numberSlots += l.getLength();
            }
        }
        maxCarSize = (int)(0.6 * numberSlots);
        return maxCarSize;
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
        if (L1 != null) {
            for (int a = 0; a < map.getRoads().size(); a++) {
                for (int b = 0; b < map.getRoads().get(a).getNumberLanes(); b++) {
                    Lane L2 = map.getRoads().get(a).getLanes().get(b);
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
//                            System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                            return C1;

                        }
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

