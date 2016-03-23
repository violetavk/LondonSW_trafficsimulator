package londonsw.controller;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
 *
 */
@SuppressWarnings("Duplicates")
public class StartUpController extends Application{

    public Label londonSWlabel;
    public Label trafficSimLabel;
    public Button startButton;
    public Button simulationModeButton;
    public Button mapMakerButton;

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
        primaryStage.centerOnScreen();
    }


    public void goToChooseModeScreen(ActionEvent actionEvent) throws IOException {
        Parent chooseModeScreen = FXMLLoader.load(getClass().getResource("../view/startup/ChooseModeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chooseModeScreen));
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    /**
     * when the user click "Choose Pre-made map..." button, it will go to SimulationMode screen
     * @param actionEvent click action
     * @throws Exception
     */
    public void goToSimulationMode(ActionEvent actionEvent) throws Exception {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Open Map");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map File (*.map)", "*.map"));
        File file = chooser.showOpenDialog(new Stage());

        if(file!=null)
        {
            String mapName = file.getName();

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
                System.out.println("Set ticker interval to " + Ticker.getTickInterval());
            });

            //Decorate map to extend to GUI functionality
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            SimulationController simulationController = new SimulationController(stage);
            simulationController.setMapName(mapName);
            simulationController.drawScreen();
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

//        ButtonType doneButton = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
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

}
