package londonsw.model.simulation.components;

import java.lang.reflect.Array;
import java.util.ArrayList;

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


public class Intersection implements Component{

    private Lane northLane;
    private Lane southLane;
    private Lane eastLane;
    private Lane westLane;
    public Lane[] laneOptions;

    private TrafficLight northTrafficLight;
    private TrafficLight southTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;

    private Coordinate location;

    /* constructor */
    public Intersection(Coordinate location){
        this.northLane = null;
        this.southLane = null;
        this.eastLane = null;
        this.westLane = null;
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

    public Lane getNorthLane() {
        return northLane;
    }

    public void setNorthLane(Lane northLane) throws Exception {

        if(northLane.getEntry().getX()==location.getX() || northLane.getExit().getX()==location.getX())
        {
            this.northLane = northLane;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Lane getSouthLane() {
        return southLane;
    }

    public void setSouthLane(Lane southLane) throws Exception {

        if(southLane.getEntry().getX()==location.getX() || southLane.getExit().getX()==location.getX())
        {
            this.southLane = southLane;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Lane getEastLane() {
        return eastLane;
    }

    public void setEastLane(Lane eastLane) throws Exception {

        if(eastLane.getEntry().getY()==location.getY() || eastLane.getExit().getY()==location.getY())
        {
            this.eastLane = eastLane;
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Lane getWestLane() {
        return westLane;
    }

    public void setWestLane(Lane westLane) throws IntersectionSetupException {

        if(westLane.getEntry().getY()==location.getY() || westLane.getExit().getY()==location.getY())
        {
            this.westLane = westLane;
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

    public Lane[] getLaneOptions(){
        int optionNumber=0;
        if(getEastLane()!= null){
            laneOptions[optionNumber]=this.getEastLane();
            optionNumber++;
        }
        if(getSouthLane()!= null){
            laneOptions[optionNumber]=this.getSouthLane();
            optionNumber++;
        }
        if(getWestLane()!= null){
            laneOptions[optionNumber]=this.getWestLane();
            optionNumber++;
        }
        if(getNorthLane()!= null){
            laneOptions[optionNumber]=this.getNorthLane();
        }
        return laneOptions;
    }
}

class IntersectionSetupException extends Exception {
    public IntersectionSetupException() { super();}
    public IntersectionSetupException(String msg) { super(); }
    public IntersectionSetupException(String msg, Throwable t) { super(msg,t); }
    public IntersectionSetupException(Throwable t) { super(t); }
}