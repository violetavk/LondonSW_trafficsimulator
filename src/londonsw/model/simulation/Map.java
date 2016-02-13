package londonsw.model.simulation;

import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Road;

/**
 * This is the graph structure that our map holds (Roads and Intersections)
 */
public class Map {
    private final Road[] roads;
    private final Intersection[] intersections;

    public Map(Road[] Roads, Intersection Intersections[]) {
        roads = Roads;
        intersections = Intersections;
    }
}