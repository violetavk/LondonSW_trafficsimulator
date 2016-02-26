package londonsw.model.simulation.components;

import java.util.ArrayList;

/**
 * Created by felix on 23/02/2016.
 */
public interface IRoad {
    ArrayList<Lane> getLanes();
    void addLane(Lane lane);
    Lane getLaneAtIndex(int index);
    Coordinate getEndLocation();
    int getNumberLanes();
    Intersection getIntersection();
    void setIntersection(Intersection intersection);
    int getLength();
    boolean runsVertically();
}