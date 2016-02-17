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
        map = new Map();
        ticker = Ticker.getInstance();
    }
}
