package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.CarDirection;
import londonsw.model.simulation.components.Coordinate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public abstract class Vehicle {

    public Coordinate vehicleLocation;
    int vehicleId;
    int vehicleLength;
    int direction;
    double vehicleSpeed;
    double vehicleLifetime;
    private Vehicle[] lane;
    CarDirection cDirection;
    
    //Constructor
    public Vehicle(Coordinate vehicleLocation, int vehicleId) {
        this.vehicleLocation = vehicleLocation;
        this.vehicleId = vehicleId;}

    //Getter
    public int getVehicleLength() {return vehicleLength;}
    public Coordinate getVehicleLocation() {return vehicleLocation;}
    public int getDirection() {return direction;}
    public int getVehicleId() {return vehicleId;}
    public double getVehicleSpeed() {return vehicleSpeed;}
    
    //Move a vehicle one step forward
    public boolean moveVehicle(int currCell) {
        Vehicle v = lane[currCell];
        if(v == null) {
            return false;
        }
        else {
            lane[currCell+1] = v;
            lane[currCell] = null;
            return true;
        }
    }

    
    /* I think we need a setLane() method, so when a car is in an intersection and wants to turn
     left, right or straight,is has to choose a new lane, for example to turn left:
     Vehicle.setlane(Left or laneID);*/

    // vehicle chooese its direction randomly
    public void vehicleDirection(){
        switch (cDirection.randomLetter()){
            case STRAIGHT:
                moveVehicle(vehicleId);
                break;

            case LEFT:
                /*if (eastroad)
                Vechile.setlane(laneID);*/
                break;

            case RIGHT:

                break;
        }
    }

    // remove vehicle
    void removeVehicle(){
        //TODO

    }


    public void priority(){};
}

