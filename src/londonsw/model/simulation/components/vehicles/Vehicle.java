package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.VehicleBehavior;

import java.util.*;


/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public  class Vehicle {

    public Coordinate vehicleLocation;
    int vehicleId;
    int vehicleLength;
    double vehicleSpeed;
    private Vehicle[] lane;
    int currentCell;
    int vehiclePriority;// 1 is the lowest
    int vehicleState;
    VehicleBehavior vehicleBehavior;


    
    //Constructor
    public Vehicle(Coordinate vehicleLocation, int vehicleId) {
        this.vehicleLocation = vehicleLocation;
        this.vehicleId = vehicleId;}

    //Getter
    public int getVehicleLength() {return vehicleLength;}
    public Coordinate getVehicleLocation() {return vehicleLocation;}
    public int getVehicleId() {return vehicleId;}
    public double getVehicleSpeed() {return vehicleSpeed;}
    public int getVehiclePriorty(){return  vehiclePriority;}

    //Setter
    public void setVehicleLength(int vehicleLength){this.vehicleLength=vehicleLength;}
    public void setVehicleLocation(Coordinate vehicleLocation){this.vehicleLocation=vehicleLocation;}
    public void setVehicleId(int vehicleId){this.vehicleId=vehicleId;}
    public void setVehicleSpeed(double vehicleSpeed){this.vehicleSpeed=vehicleSpeed;}
    public void setVehiclePriority(int vehiclePriority){this.vehiclePriority=vehiclePriority;}


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

    public int getCurrentCell(){return currentCell;}

    public int getVehicleState(){return  vehicleState;}
    public void setVehicleState(int vehicleState){this.vehicleState=vehicleState;}
    //1 is moving, 0 is static
    public void stopVehicle(){vehicleState=0;}

    //give vehicle Behavior randomly
    public void setVehicleBehavior(VehicleBehavior vehicleBehavior){this.vehicleBehavior=vehicleBehavior;}
    public VehicleBehavior getVehicleBehavior(){return VehicleBehavior.randomLetter(); }

}

