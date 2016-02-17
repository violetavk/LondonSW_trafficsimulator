package londonsw.model.simulation;

import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Road;

import java.util.ArrayList;

/**
 * This is the graph structure that our map holds (Roads and Intersections)
 */
public class Map {
    private ArrayList<Road> roads;
    private ArrayList<Intersection> intersections;

    public Map() {
        roads = new ArrayList<Road>();
        intersections = new ArrayList<Intersection>();
    }

    public Map(ArrayList<Road> roads, ArrayList<Intersection> intersections) {
        this.roads = roads;
        this.intersections = intersections;
    }
}