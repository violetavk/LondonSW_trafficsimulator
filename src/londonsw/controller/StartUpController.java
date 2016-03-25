package londonsw.controller;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import javafx.stage.StageStyle;
import javafx.util.Pair;
import londonsw.model.simulation.Ticker;

//import java.awt.*;
import java.io.IOException;
import java.util.Optional;

/**
 *  This is the controller that gets called by the main SystemApp class. This controller initiates all GUI screens.
 */
@SuppressWarnings("Duplicates")
public class StartUpController extends Application {

    private static StartUpController instance;

    public StartUpController() { }

    public static StartUpController getInstance() {
        if(instance == null)
            instance = new StartUpController();
        return instance;
    }

    public void startSoftware(String[] args) {
        launch(args);
    }

    /**
     * This is the first method that gets called in the system. It loads the StartScreen fxml file, which contains
     * the START button.
     * @param primaryStage the stage that initially loads
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LondonSW Traffic Simulator");

        VBox vBox = new VBox();
        vBox.setPrefSize(600,400);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color:papayawhip");
        vBox.setAlignment(Pos.CENTER);

        Label londonSWLabel = new Label("London SW");
        londonSWLabel.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 20));
        Label trafficSimLabel = new Label("Traffic Simulator");
        trafficSimLabel.setFont(Font.font("System Bold Italic", FontWeight.EXTRA_BOLD, 22));
        trafficSimLabel.setPadding(new Insets(0,0,50,0));
        Button startButton = new Button("Start");
        startButton.setPrefSize(300,150);
        startButton.setStyle("-fx-base:Gold");
        startButton.setFont(Font.font("System Bold Italic", FontWeight.EXTRA_BOLD, 26));

        vBox.getChildren().add(londonSWLabel);
        vBox.getChildren().add(trafficSimLabel);
        vBox.getChildren().add(startButton);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.centerOnScreen();

        startButton.setOnMouseClicked(click -> {
            goToChooseModeScreen(primaryStage);
        });
    }

    /**
     * This is the method that gets called when the user hits the START button on the initial screen. It
     * loads the "Choose Mode" screen, which gives 2 options: Opening a pre-made map, or users building their own map.
     * @param primaryStage the stage to display the screen in
     */
    public void goToChooseModeScreen(Stage primaryStage) {
        VBox vBox = new VBox();
        vBox.setPrefSize(600,400);
        vBox.setSpacing(50);
        vBox.setStyle("-fx-background-color:papayawhip");
        vBox.setAlignment(Pos.CENTER);
        Platform.runLater(() -> vBox.requestFocus());

        Button openMap = new Button("Open a Pre-made Map");
        openMap.setPrefSize(300, 90);
        openMap.setStyle("-fx-base:Gold");
        openMap.setFont(Font.font("System Bold Italic", FontWeight.EXTRA_BOLD, 16));

        Button makeMap = new Button("Make a new Map");
        makeMap.setPrefSize(300, 90);
        makeMap.setStyle("-fx-base:Gold");
        makeMap.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 16));

        vBox.getChildren().add(openMap);
        vBox.getChildren().add(makeMap);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);

        openMap.setOnMouseClicked(click -> {
            try {
                goToSimulationMode(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        makeMap.setOnMouseClicked(click -> {
            goToMapMakerMode(primaryStage);
        });
    }

    /**
     * When the user click "Choose Pre-made map..." button, it will go to SimulationMode screen. It will first
     * prompt the user to open a file (only Map files are allowed to be opened), set a ticker interval speed,
     * and then it will go to draw the simulation mode screen.
     * @param primaryStage the click that caused this method invocation
     * @throws Exception
     */
    public void goToSimulationMode(Stage primaryStage) throws Exception {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Open Map");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map File (*.map)", "*.map"));
        File file = chooser.showOpenDialog(new Stage());


        if(file!=null)
        {
//            String mapName = file.getName();
            String mapName = file.getAbsolutePath();


            Dialog<Long> dialog = new Dialog<>();
            dialog.setTitle("Choose Ticker Interval Duration");
            dialog.setHeaderText("Choose a duration (in milliseconds) for\nthe ticker in the system.");
            dialog.setGraphic(null);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.initStyle(StageStyle.UNDECORATED);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 80, 10, 10));
            grid.add(new Label("Duration: "), 0, 0);
            Spinner<Double> spinner = new Spinner<Double>(100, 2000, Ticker.getTickInterval(), 100);
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
            result.ifPresent(aLong -> {
                Ticker.setTickInterval(aLong);
                Ticker.start();
            });

            //Decorate map to extend to GUI functionality
            SimulationController simulationController = new SimulationController(primaryStage);
            simulationController.setMapName(mapName);
            simulationController.drawScreen();
        }
    }

    /**
     * This method gets called when the user chooses to go to Map Maker mode. It will prompt the user for
     * the width and height that they want for their new map, in the range from 5 to 30 for both width and
     * height. It will then bring the user to the screen where they can build the map.
     * @param primaryStage the click event that caused this method invocation
     */
    public void goToMapMakerMode(Stage primaryStage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choose Map Size");
        dialog.setHeaderText("Choose new map's width and height");
        dialog.setGraphic(null);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ObservableList<Integer> choices = FXCollections.observableArrayList();
        for(int i = 5; i <= 30; i++) {
            choices.add(i);
        }

        ChoiceBox<Integer> widthBox = new ChoiceBox<>();
        widthBox.setItems(choices);
        widthBox.setMinWidth(100);
        Platform.runLater(() -> widthBox.requestFocus());
        ChoiceBox<Integer> heightBox = new ChoiceBox<>();
        heightBox.setItems(choices);
        heightBox.setMinWidth(100);

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthBox, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Button doneBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        doneBtn.setDisable(true);
        doneBtn.disableProperty().bind(
                widthBox.valueProperty().isNull()
                .or(heightBox.valueProperty().isNull())
        );

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK) {
                return new Pair<>(widthBox.getValue().toString(),heightBox.getValue().toString());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(widthAndHeight -> {
            int width = Integer.parseInt(widthAndHeight.getKey());
            int height = Integer.parseInt(widthAndHeight.getValue());

            MapMakerController mapMakerController = new MapMakerController(primaryStage);
            mapMakerController.setWidthAndHeight(width,height);
            try {
                mapMakerController.drawScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
