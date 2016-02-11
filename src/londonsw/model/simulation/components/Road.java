package londonsw.model.simulation.components;

import java.util.ArrayList;

/**
 * Each road is connected to at most 2 intersections (one at each end)
 * Each road is composed of a number of lanes (currently 2, one for each direction)
 * These are like the edges in our directed graph
 * Each has a start-location and an end-location
 */
public class Road {

    Coordinate startLocation;
    Coordinate endLocation;
    ArrayList<Lane> lanes;

    public Road() {

    }

    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    public Coordinate getStartLocation() {
        return startLocation;
    }

    public Coordinate getEndLocation() {
        return endLocation;
    }

    public int getNumberLanes() {
        return lanes.size();
    }


}
