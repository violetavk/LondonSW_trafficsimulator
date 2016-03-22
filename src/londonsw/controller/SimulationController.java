package londonsw.controller;

import javafx.stage.Stage;
import londonsw.model.simulation.Log;
import londonsw.model.simulation.Map;
import londonsw.view.simulation.SimulationScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the controller for simulations in our View. It draws the screen and generates the Log for the simulation.
 */
public class SimulationController {

    private Stage primaryStage;
    private String mapName;
    private String logFileName;

    /**
     * Creates a new SimulationController to control the simulation
     * @param primaryStage the stage in which to display the new screen
     */
    public SimulationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Sets the name of the map that the user wants to open
     * @param mapName name of map file that user wants to simulate vehicles on
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * Draws the screen where the simulation will be taking place, and creates the Log to log all activity
     * in the simulation
     * @throws Exception
     */
    public void drawScreen() throws Exception {
        Map map = Map.loadMap(mapName);
        SimulationScreen screen = new SimulationScreen(map);
        screen.drawScreen(primaryStage);
        generateLogFileName();
        Log log = new Log(getLogFileName());
    }

    /**
     * Get the name of the log file for that specific simulation
     * @return
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * Generates a file name for the log file for that simulation. It is in the format
     * Log_DATE.log, where DATE is the YEAR-MONTH-DAY-HOUR-MINUTES-SECONDS
     */
    public void generateLogFileName() {
        String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        logFileName = "Log_" + date;
    }

}
