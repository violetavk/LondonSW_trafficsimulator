package londonsw.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartUpController extends Application{

    public Label londonSWlabel;
    public Label trafficSimLabel;
    public Button startButton;

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

    public void goToSimulationMode(ActionEvent actionEvent) throws IOException {
        // TODO This is just dummy code to take place of the next screens for now
        Parent simulationModeScreen = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(simulationModeScreen));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        /**
        alert.setTitle("Simulation Mode");
        alert.setHeaderText(null);
        alert.setContentText("This is a placeholder for the next screens. The user would load a map and simulate on it.");
        alert.showAndWait();
         **/
    }

    public void goToMapBuilderMode(ActionEvent actionEvent) {


    }
}
