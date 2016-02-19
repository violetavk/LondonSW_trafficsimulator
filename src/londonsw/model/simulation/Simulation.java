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
        map = new Map(10,10);
        ticker = new Ticker();
    }

    public void startSimulation() {
        ticker.start();
        // other things too...
    }
}
