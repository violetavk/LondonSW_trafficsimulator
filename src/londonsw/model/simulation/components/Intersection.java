package londonsw.model.simulation.components;

/**
 * This class is our "node" in our directed graph
 * It will hold anywhere between 1 and 4 traffic lights
 * It will connect anywhere between 2 and 4 roads
 * Each will have a location in the map
 */

/* the traffic light belongs to the road
 * in each intersection, a can can choose(maybe randomly) Which road he can enter based on the array IntersectionRoad
 */

public class Intersection {

    private Road north;
    private Road south;
    private Road east;
    private Road west;

    private TrafficLight northTrafficLight;
    private TrafficLight southTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;

    private Coordinate location;

    /* constructor */
    public Intersection(Coordinate location){
        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
        this.location = location;
        this.northTrafficLight = null;
        this.southTrafficLight = null;
        this.eastTrafficLight = null;
        this.westTrafficLight = null;
    }

    public TrafficLight getNorthTrafficLight() {
        return northTrafficLight;
    }

    public void setNorthTrafficLight(TrafficLight northTrafficLight) {
        this.northTrafficLight = northTrafficLight;
    }

    public TrafficLight getSouthTrafficLight() {
        return southTrafficLight;
    }

    public void setSouthTrafficLight(TrafficLight southTrafficLight) {
        this.southTrafficLight = southTrafficLight;
    }

    public TrafficLight getEastTrafficLight() {
        return eastTrafficLight;
    }

    public void setEastTrafficLight(TrafficLight eastTrafficLight) {
        this.eastTrafficLight = eastTrafficLight;
    }

    public TrafficLight getWestTrafficLight() {
        return westTrafficLight;
    }

    public void setWestTrafficLight(TrafficLight westTrafficLight) {
        this.westTrafficLight = westTrafficLight;
    }

    public Road getNorth() {
        return north;
    }

    public void setNorth(Road north) {
        this.north = north;
    }

    public Road getSouth() {
        return south;
    }

    public void setSouth(Road south) {
        this.south = south;
    }

    public Road getEast() {
        return east;
    }

    public void setEast(Road east) {
        this.east = east;
    }

    public Road getWest() {
        return west;
    }

    public void setWest(Road west) {
        this.west = west;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }
}

