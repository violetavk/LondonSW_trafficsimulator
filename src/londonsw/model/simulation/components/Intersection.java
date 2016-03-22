package londonsw.model.simulation.components;

import londonsw.controller.IntersectionController;
import londonsw.model.simulation.components.vehicles.Vehicle;
import rx.Subscriber;
import londonsw.model.simulation.Ticker;

import java.io.Serializable;
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


public class Intersection extends Subscriber<Long> implements Component, Serializable {

    private static final long serialVersionUID = -2621352799268337492L;
    private Road northRoad;
    private Road southRoad;
    private Road eastRoad;
    private Road westRoad;

    private TrafficLight northTrafficLight;
    private TrafficLight southTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;
    private ArrayList<TrafficLight> allLights;
    private int id;
    private Coordinate location;
    private static  int counter=0;

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
        Ticker.subscribe(this);

        id=++counter;
    }

    /* getters */
    public TrafficLight getNorthTrafficLight() {
        return northTrafficLight;
    }
    public TrafficLight getSouthTrafficLight() {
        return southTrafficLight;
    }
    public TrafficLight getEastTrafficLight() {
        return eastTrafficLight;
    }
    public TrafficLight getWestTrafficLight() {
        return westTrafficLight;
    }
    public Road getNorthRoad() {
        return northRoad;
    }
    public Road getEastRoad() {
        return eastRoad;
    }
    public Road getSouthRoad() {
        return southRoad;
    }
    public Road getWestRoad() {
        return westRoad;
    }
    public Coordinate getLocation() {
        return location;
    }
    public int getId() {
        return id;
    }

    /* setters */
    public void setNorthTrafficLight(TrafficLight northTrafficLight) {
        this.northTrafficLight = northTrafficLight;
    }
    public void setSouthTrafficLight(TrafficLight southTrafficLight) {
        this.southTrafficLight = southTrafficLight;
    }
    public void setEastTrafficLight(TrafficLight eastTrafficLight) {
        this.eastTrafficLight = eastTrafficLight;
    }
    public void setWestTrafficLight(TrafficLight westTrafficLight) {
        this.westTrafficLight = westTrafficLight;
    }
    public void setLocation(Coordinate location) throws IntersectionSetupException {
        this.location = location;
    }
    public void setIdIntersection(int id) {
        this.id = id;
    }


    public void setNorthRoad(Road northRoad) throws Exception {
        if((this.location.getX() == northRoad.getEndLocation().getX()
                && (this.location.getY() - 1 == northRoad.getEndLocation().getY()
                ||  this.location.getY() - 1 == northRoad.getStartLocation().getY())))
        {
            this.northRoad = northRoad;
            for(int i=0; i<this.northRoad.getNumberLanes();i++){
                if(this.northRoad.getLaneAtIndex(i).getMovingDirection()==MapDirection.SOUTH)
                {
                    this.northRoad.getLaneAtIndex(i).setEndIntersection(this);
                }
            }
        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public void setSouthRoad(Road southRoad) throws Exception {
        if((this.location.getX()==southRoad.getEndLocation().getX()
                && (this.location.getY() + 1 == southRoad.getEndLocation().getY()
                || this.location.getY() + 1 == southRoad.getStartLocation().getY())))
        {
            this.southRoad = southRoad;
            for(int i=0; i<this.southRoad.getNumberLanes();i++){
                if(this.southRoad.getLaneAtIndex(i).getMovingDirection()==MapDirection.NORTH)
                {
                    this.southRoad.getLaneAtIndex(i).setEndIntersection(this);
                }
            }

        }
        else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public void setEastRoad(Road eastRoad) throws Exception {
        if (this.location.getY() == eastRoad.getEndLocation().getY()
                && (this.location.getX() + 1  == eastRoad.getEndLocation().getX()
                || this.location.getX() + 1 == eastRoad.getStartLocation().getX())) {
            this.eastRoad = eastRoad;
            for(int i=0; i<this.eastRoad.getNumberLanes();i++){
                if(this.eastRoad.getLaneAtIndex(i).getMovingDirection()==MapDirection.WEST)
                {
                    this.eastRoad.getLaneAtIndex(i).setEndIntersection(this);
                }
            }

        } else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public void setWestRoad(Road westRoad) throws IntersectionSetupException {
        if ((this.location.getY()  == westRoad.getEndLocation().getY()
                && (this.location.getX() - 1 == westRoad.getEndLocation().getX()
                || this.location.getX() -1 == westRoad.getStartLocation().getX()))) {
            this.westRoad = westRoad;
            for(int i=0; i<this.westRoad.getNumberLanes();i++){
                if(this.westRoad.getLaneAtIndex(i).getMovingDirection()==MapDirection.EAST)
                {
                    this.westRoad.getLaneAtIndex(i).setEndIntersection(this);
                }
            }

        } else
            throw new IntersectionSetupException("Road end location coordinates must match with Intersection");
    }

    public void setDefaultTrafficLightsForRoads() {
        if(northRoad != null) {
            ArrayList<Lane> lanes = northRoad.getLanes();
            boolean hasSouthLane = false;
            for(Lane l : lanes) {
                if(l.getMovingDirection() == MapDirection.SOUTH) {
                    hasSouthLane = true;
                    break;
                }
            }
            if(hasSouthLane)
                northTrafficLight = new TrafficLight();
        }

        if(southRoad != null) {
            ArrayList<Lane> lanes = southRoad.getLanes();
            boolean hasNorthLane = false;
            for(Lane l : lanes) {
                if(l.getMovingDirection() == MapDirection.NORTH) {
                    hasNorthLane = true;
                    break;
                }
            }
            if(hasNorthLane)
                southTrafficLight = new TrafficLight();
        }
        if(eastRoad != null) {
            ArrayList<Lane> lanes = eastRoad.getLanes();
            boolean hasWestLane = false;
            for(Lane l : lanes) {
                if(l.getMovingDirection() == MapDirection.WEST) {
                    hasWestLane = true;
                    break;
                }
            }
            if(hasWestLane) {
                if (northRoad != null || southRoad != null) {
                    eastTrafficLight = new TrafficLight(LightColour.GREEN);
                } else
                    eastTrafficLight = new TrafficLight();
            }
        }
        if(westRoad != null) {
            ArrayList<Lane> lanes = westRoad.getLanes();
            boolean hasEastLane = false;
            for(Lane l : lanes) {
                if(l.getMovingDirection() == MapDirection.EAST) {
                    hasEastLane = true;
                    break;
                }
            }
            if(hasEastLane) {
                if (northRoad != null || southRoad != null)
                    westTrafficLight = new TrafficLight(LightColour.GREEN);
                else
                    westTrafficLight = new TrafficLight();
            }
        }
    }


    /**
     * generate a list of 4 items form 1 to 4
     * these items are placed in the array randomly
     * @return list of integer in type of ArrayList
     */
    public static ArrayList<Integer> generateRandom (){
        int size =4;
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        ArrayList<Integer> l = new ArrayList<Integer>(size);

        for(int i = 1; i <= size; i++) {
            list.add(i);
        }Random rand = new Random();
        while(list.size() > 0) {
            int index = rand.nextInt(list.size());
            l.add(list.get(index));
           // System.out.println("Selected: "+list.remove(index));
            list.remove(index);
        }

        return l;

    }


    /**
     * intersection gives each vehicle on it a priority to turn first
     * first it checks :
     * 1. if there is a road connected to it.
     * 2. if in the road there is a lane which its direction to this intersection
     * 3. if there is a vehicle at the last cell in this lane, which is this intersection
     * if these conditions are obtained , then the intersection gives this vehicle a priority to turn
     * and put these vehicle in arrayList .
     * @param randomPriority a list 4 items form 1 to 4, arranged randomly in the array list
     * @return vehicleInIntersection an arrayList in type of integer , which contains all vehicles which
     * has priority to turn
     * @throws
     */
    public ArrayList<Vehicle> giveVehiclePriorities (ArrayList<Integer>  randomPriority) throws Exception {

       // ArrayList<Integer>  randomPriority = (ArrayList<Integer>)this.generateRandom(4).clone();
        ArrayList<Vehicle> vehicleInIntersection= new ArrayList<>() ;

        if (this.getNorthRoad() != null) {
            for (int i = 0; i < this.getNorthRoad().getNumberLanes(); i++) {
                if ((this.getNorthRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.SOUTH)) {
                    if ((this.getNorthRoad().getLaneAtIndex(i).getVehicleInIntersection() != null)) {
                        if(this.getNorthTrafficLight()!= null)
                            this.getNorthRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehicleTrafficLight(this.getNorthTrafficLight());
                        this.getNorthRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehiclePriorityToTurn(randomPriority.get(0));
                        vehicleInIntersection.add( this.getNorthRoad().getLaneAtIndex(i).getVehicleInIntersection());
                    }
                }
            }
        }

        if (this.getSouthRoad() != null) {
            for (int i = 0; i < this.getSouthRoad().getNumberLanes(); i++) {
                if ((this.getSouthRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.NORTH)) {
                    if ((this.getSouthRoad().getLaneAtIndex(i).getVehicleInIntersection() != null)) {
                        if(this.getSouthTrafficLight()!= null)
                            this.getSouthRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehicleTrafficLight(this.getSouthTrafficLight());
                        this.getSouthRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehiclePriorityToTurn(randomPriority.get(1));
                        vehicleInIntersection.add( this.getSouthRoad().getLaneAtIndex(i).getVehicleInIntersection());
                    }
                }
            }
        }

        if (this.getEastRoad() != null) {
            for (int i = 0; i < this.getEastRoad().getNumberLanes(); i++) {
                if ((this.getEastRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.WEST)) {
                    if ((this.getEastRoad().getLaneAtIndex(i).getVehicleInIntersection() != null)) {
                        if(this.getEastTrafficLight()!= null)
                            this.getEastRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehicleTrafficLight(this.getEastTrafficLight());
                        this.getEastRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehiclePriorityToTurn(randomPriority.get(2));
                        vehicleInIntersection.add( this.getEastRoad().getLaneAtIndex(i).getVehicleInIntersection());
                    }
                }
            }
        }

        if (this.getWestRoad() != null) {
            for (int i = 0; i < this.getWestRoad().getNumberLanes(); i++) {
                if ((this.getWestRoad().getLaneAtIndex(i).getMovingDirection() == MapDirection.EAST)) {
                    if ((this.getWestRoad().getLaneAtIndex(i).getVehicleInIntersection() != null)) {
                        if(this.getWestTrafficLight()!= null)
                            this.getWestRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehicleTrafficLight(this.getWestTrafficLight());
                        this.getWestRoad().getLaneAtIndex(i).getVehicleInIntersection().setVehiclePriorityToTurn(randomPriority.get(3));
                        vehicleInIntersection.add( this.getWestRoad().getLaneAtIndex(i).getVehicleInIntersection());
                    }
                }
            }
        }
        return vehicleInIntersection;

    }

    /**
     * checks the vehicle with higher priority to turn
     * if a vehicle has a higher priority
     * it sets its priority to turn to 1 which means move
     * otherwise sets it to 0 which means stop
     * @param vehicles an array list in type of integer which contains all vehicles which has priority to turn
     * @return
     * @throws Exception
     */
    public boolean vehicleTurnFirst (ArrayList<Vehicle> vehicles)throws Exception{
        int max=0;

       // for (int i=0; i<vehicles.size();i++)
       // {System.out.println("ID is: " + vehicles.get(i).getId()+ "  priority is : "+ vehicles.get(i).getVehiclePriorityToTurn());}


        if (vehicles != null) {
    for (int i = 0; i < vehicles.size(); i++) {
            if (max <= vehicles.get(i).getVehiclePriorityToTurn() ){
                if(vehicles.get(i).getVehicleTrafficLight()!= null){
                if(vehicles.get(i).getVehicleTrafficLight().getState()==LightColour.GREEN)
                max = vehicles.get(i).getVehiclePriorityToTurn();}
        else max = vehicles.get(i).getVehiclePriorityToTurn();}

    }


    for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehiclePriorityToTurn() == max || vehicles.get(i).getVehiclePriority()==5)
            {vehicles.get(i).setVehiclePriorityToTurn(1);}
        else vehicles.get(i).setVehiclePriorityToTurn(0);
    }
}

        return true;
    }


    /**
     * This is the method that gets called when the ticker terminates
     *  Left not implemented on purpose
     */
    @Override
    public void onCompleted() {

    }

    /**
     * If there's some error with the ticker and this subscriber, this method would call.
     * Left not implemented on purpose
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     * This is like the onTick method. This is what intersection would do when the ticker ticks.
     * @param aLong this gives the current time in the system to the intersection (although it is probably not required)
     */
    @Override
    public void onNext(Long aLong){
        try {

            IntersectionController.vehicleTurnFirst(this, this.giveVehiclePriorities(this.generateRandom()));
        } catch (Exception e) {
            e.printStackTrace();
        }

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