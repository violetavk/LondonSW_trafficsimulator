package londonsw.model.simulation.components;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is our "node" in our directed graph
 * It will hold anywhere between 1 and 4 traffic lights
 * It will connect anywhere between 2 and 4 roads
 * Each will have a location in the map
 */

/* the traffic light belongs to the road
 * in each intersection, a car can choose(maybe randomly) Which road he can enter based on the array IntersectionRoad
 */



public class Intersection implements Component{

    private Road northRoad;
    private Road southRoad;
    private Road eastRoad;
    private Road westRoad;
    public ArrayList<Lane> laneOptions = new ArrayList<Lane>();

    private TrafficLight northTrafficLight;
    private TrafficLight southTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;

    private Coordinate location;
    private Random randomDirection;

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

        if((this.location.getX() == northRoad.getEndLocation().getX()
                && (this.location.getY() - 1 == northRoad.getEndLocation().getY()
                ||  this.location.getY() - 1 == northRoad.getStartLocation().getY())))
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

        if((this.location.getX()==southRoad.getEndLocation().getX()
                && (this.location.getY() + 1 == southRoad.getEndLocation().getY()
                || this.location.getY() + 1 == southRoad.getStartLocation().getY())))
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

        if ((this.location.getY() == eastRoad.getEndLocation().getY()
                && (this.location.getX() + 1  == eastRoad.getEndLocation().getY()
                || this.location.getX() + 1 == eastRoad.getStartLocation().getY()))) {
            this.eastRoad = eastRoad;
        } else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Road getWestRoad() {
        return westRoad;
    }

    public void setWestRoad(Road westRoad) throws IntersectionSetupException {

        if ((this.location.getY()  == westRoad.getEndLocation().getY()
                && (this.location.getX() - 1 == westRoad.getEndLocation().getX()
                || this.location.getX() -1 == westRoad.getStartLocation().getX()))) {
            this.westRoad = westRoad;
        } else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) throws IntersectionSetupException {

        this.location = location;
    }

    public void getIntersectionLocation(Road[] road){

       //TODO
    }

    public ArrayList<Lane> getLaneOptions(){

        laneOptions.clear();

        if(this.getEastRoad()!= null){
            for(int i=0; i<this.getEastRoad().getNumberLanes();i++){
                if(this.getEastRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.EAST){
                    laneOptions.add(this.getEastRoad().getLaneAtIndex(i));
                }
            }
        }
        if(this.getSouthRoad()!= null){
            for(int i=0; i<this.getSouthRoad().getNumberLanes();i++){
                if(this.getSouthRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.SOUTH){
                    laneOptions.add(this.getSouthRoad().getLaneAtIndex(i));
                }
            }
        }
        if(this.getWestRoad()!= null){
            for(int i=0; i<this.getWestRoad().getNumberLanes();i++){
                if(this.getWestRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.WEST){
                    laneOptions.add(this.getWestRoad().getLaneAtIndex(i));
                }
            }
        }
        if(this.getNorthRoad()!= null){
            for(int i=0; i<this.getNorthRoad().getNumberLanes();i++){
                if(this.getNorthRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.NORTH){
                    laneOptions.add(this.getNorthRoad().getLaneAtIndex(i));
                }
            }
        }
        return laneOptions;
    }

    public Lane chooseDirection (){
        randomDirection = new Random();
        int size = randomDirection.nextInt(this.getLaneOptions().size());
        return this.getLaneOptions().get(size);
    }

}

class IntersectionSetupException extends Exception {
    public IntersectionSetupException() {
        super();
    }

    public IntersectionSetupException(String msg) {
        super();
    }

    public IntersectionSetupException(String msg, Throwable t) {
        super(msg, t);
    }

    public IntersectionSetupException(Throwable t) {
        super(t);
    }
}