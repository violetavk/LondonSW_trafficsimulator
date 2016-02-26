package londonsw.model.simulation;

/**
 * This is the main class that runs the simulation
 * It contains a Map and a Ticker
 * It keep track of the cars in the simulation (linked-list, etc.)
 */
public class Simulation {

    private Map map;
    private Ticker ticker;

    public Simulation() {
        map = Map.loadMap("FixedMap.map");
        // TODO map might need to be loaded from somewhere, not set to a default value!
        ticker = new Ticker();
    }

    public void startSimulation() {
        ticker.start();
        // other things too...
    }
}
