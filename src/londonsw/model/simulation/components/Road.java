package londonsw.model.simulation.components;

import java.util.ArrayList;

/**
 * Each road is connected to at most 2 intersections (one at each end)
 * Each road is composed of a number of lanes (currently 2, one for each direction)
 * These are like the edges in our directed graph
 * Each has a start-location and an end-location
 */
public class Road implements Component {

    private Coordinate start;
    private Coordinate end;
    private ArrayList<Lane> lanes;
    private Intersection intersection;

    /**
     * Creates an instance of a new Road. It has no lanes yet. (Note: a road without lanes
     * should not exist, so the user must specify lanes right away). A road can have anywhere
     * between 1 and n lanes.
     * A road is defined by where it starts in the grid and where it ends in the grid.
     * The coordinates can be in any order, as long as they form a straight line.
     *
     * @param start the location of one end of the road
     * @param end the location of the other end of the road
     */
    public Road(Coordinate start, Coordinate end ) {
        lanes = new ArrayList<Lane>();
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the number of lanes a road has
     * @return number of lanes in the road
     */
    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    /**
     * Adds a lane to the road
     *
     * @param l the Lane to add to the road
     */
    public void addLane(Lane l) { lanes.add(l); }

    /**
     * Gets the lane at the index specified
     * @param i index of lane
     * @return the instance of Lane at that index i
     */
    public Lane getLaneAtIndex(int i) { return lanes.get(i); }

    /**
     * Gets the beginning coordinate of the road
     * @return location of beginning of road of type Coordinate
     */
    public Coordinate getStartLocation() {
        return start;
    }

    /**
     * Gets the end coordinate of the road
     * @return location of end of road of type Coordinate
     */
    public Coordinate getEndLocation() {
        return end;
    }

    /**
     * Gets the number of lanes currently part of the road
     * @return number of lanes in the road of type int
     */
    public int getNumberLanes() {
        return lanes.size();
    }

    public Intersection getIntersection() {
        //TODO
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Uses the coordinates to determine how long the road is
     * A road has a minimum length of 1
     * @return length of the road
     */
    public int getLength() {
        int aX = start.getX();
        int aY = start.getY();
        int bX = end.getX();
        int bY = end.getY();
        int length;

        if(aX == bX) {
            length = Math.abs(aY-bY) + 1;
            return length;
        }
        else if(aY == bY) {
            length = Math.abs(aX-bX) + 1;
            return length;
        }
        else
            return -1;
    }

    /**
     * Determines if a road runs NORTH to SOUTH
     *
     * @return true if the road runs NORTH to SOUTH or SOUTH to NORTH, false if the road runs EAST to WEST or WEST to EAST
     */
    public boolean runsVertically() {
        int aX = start.getX();
        int bX = end.getX();

        // if aX==bX, then road runs vertically
        if(aX == bX)
            return true;

        // if aY==bY, then road runs horizontally
        return false;
    }
}
