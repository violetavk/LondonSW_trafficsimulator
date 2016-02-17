package londonsw.model.simulation.components;

/**
 * This class is our "node" in our directed graph
 * It will hold anywhere between 1 and 4 traffic lights
 * It will connect anywhere between 2 and 4 roads
 * Each will have a location in the map
 */

/* the traffic light belongs to the road
 * in each intersection, a car can choose(maybe randomly) Which road he can enter based on the array IntersectionRoad
 */

//TODO I think we don't need a location

/**
 * intersection test
 */
public class Intersection implements Component{

    private Road northRoad;
    private Road southRoad;
    private Road eastRoad;
    private Road westRoad;

    private TrafficLight northTrafficLight;
    private TrafficLight southTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;

    private Coordinate location;

    /* constructor */
    public Intersection(Coordinate location){
        this.northRoad = null;
        this.southRoad = null;
        this.eastRoad = null;
        this.westRoad = null;
        this.location = location;
        this.northTrafficLight = null;
        this.southTrafficLight = null;
        this.eastTrafficLight = null;
        this.westTrafficLight = null;
    }
    
// Pul request test

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

    public Road getNorthRoad() {
        return northRoad;
    }

    public void setNorthRoad(Road northRoad) throws Exception {

        if(northRoad.getEndLocation()==location || northRoad.getStartLocation()==location)
        {
            this.northRoad = northRoad;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Road getSouthRoad() {
        return southRoad;
    }

    public void setSouthRoad(Road southRoad) throws Exception {

        if(southRoad.getEndLocation()==location || southRoad.getStartLocation()==location)
        {
            this.southRoad = southRoad;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Road getEastRoad() {
        return eastRoad;
    }

    public void setEastRoad(Road eastRoad) throws Exception {

        if(eastRoad.getEndLocation()==location || eastRoad.getStartLocation()==location)
        {
            this.eastRoad = eastRoad;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Road getWestRoad() {
        return westRoad;
    }

    public void setWestRoad(Road westRoad) throws IntersectionSetupException {

        if(westRoad.getEndLocation()==location || westRoad.getStartLocation()==location)
        {
            this.westRoad = westRoad;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) throws IntersectionSetupException {

        this.location = location;
    }

    public void getIntersecttionLocation(Road[] road){
        //TODO
    }
}

class IntersectionSetupException extends Exception {
    public IntersectionSetupException() { super();}
    public IntersectionSetupException(String msg) { super(); }
    public IntersectionSetupException(String msg, Throwable t) { super(msg,t); }
    public IntersectionSetupException(Throwable t) { super(t); }
}