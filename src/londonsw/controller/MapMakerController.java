package londonsw.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.input.DataFormat;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
//import javafx.scene.input.DragEvent;


import java.io.IOException;
//import java.util.Map;


public class MapMakerController extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {

    }


    public void goToMapCreationScreen(ActionEvent actionEvent) throws IOException {

        Parent mapCreation = FXMLLoader.load(getClass().getResource("../view/mapcreation/MapCreation" + ".fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(mapCreation));
    }

/**
    public void setOnDragDetected(ActionEvent actionEvent)throws IOException{
        Parent mapCreation = FXMLLoader.load(getClass().getResource("../view/mapcreation/MapCreation" + ".fxml"));
        Node node = mapCreation.lookup("#grass");
        Dragboard db=node.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        db.setContent((Map<DataFormat, Object>) node);
        actionEvent.consume();

    }
 **/

}
