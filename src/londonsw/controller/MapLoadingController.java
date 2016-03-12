package londonsw.controller;

import javafx.fxml.FXML;

/**
 * Created by LiuJia on 3/11/16.
 */
public class MapLoadingController {
    @FXML StartUpController startUpController;
    @FXML SimulationController simulationController;
   // @FXML String mapName;

    @FXML public void initialize(){
        System.out.println("started");
        startUpController.init(this);
        simulationController.init(this);
    }


    public String getMapNameFromStartUp() {
        return StartUpController.mapName;
    }
}
