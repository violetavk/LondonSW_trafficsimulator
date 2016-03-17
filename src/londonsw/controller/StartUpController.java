package londonsw.controller;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.layout.AnchorPane;

import javafx.util.Pair;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.view.simulation.VehicleGUIDecorator;

//import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class StartUpController extends Application{

    public Label londonSWlabel;
    public Label trafficSimLabel;
    public Button startButton;
    public Button simulationModeButton;
    public Button mapMakerButton;


    @FXML private TextField width;

    @FXML private TextField height;

    @FXML public  MapLoadingController main;

    @FXML public static String mapName;

    public void startSoftware(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/startup/StartScreen.fxml"));
        primaryStage.setTitle("LondonSW Traffic Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
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
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        mapName=file.getName();

        if(file!=null)
        {
            Map map = Map.loadMap(file.getName());

            // Map map = new Map(20,20);

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
        else
        {
            //TODO: no file selected
        }
    }

    public void goToMapMakerMode(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choose Map Size");
        dialog.setHeaderText("Choose new map's width and height");
        dialog.setGraphic(null);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ButtonType doneButton = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(doneButton, ButtonType.CANCEL);

        ObservableList<Integer> choices = FXCollections.observableArrayList();
        for(int i = 5; i <= 50; i++) {
            choices.add(i);
        }

        ChoiceBox<Integer> widthBox = new ChoiceBox<>();
        widthBox.setItems(choices);
        widthBox.setMinWidth(100);
        ChoiceBox<Integer> heightBox = new ChoiceBox<>();
        heightBox.setItems(choices);
        heightBox.setMinWidth(100);

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthBox, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == doneButton) {
                return new Pair<>(widthBox.getValue().toString(),heightBox.getValue().toString());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(widthAndHeight -> {
            int width = Integer.parseInt(widthAndHeight.getKey());
            int height = Integer.parseInt(widthAndHeight.getValue());
            System.out.println("Width = " + width + ", Height = " + height);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            MapMakerController mapMakerController = new MapMakerController(stage);
            mapMakerController.setWidthAndHeight(width,height);
            try {
                mapMakerController.drawScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



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

    public void init(MapLoadingController mapLoadingController) {
        main=mapLoadingController;
    }

}
