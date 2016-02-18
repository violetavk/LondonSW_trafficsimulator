package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.CarDirection;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.TrafficLight;
import java.awt.*;
import londonsw.model.simulation.components.TrafficLight.*;


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
   // CarDirection cDirection;
    int currentCell;
    int vehiclePriority;// 1 is the lowest
    int vehicleState;




    
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

    //1 is moving , 0 is static
    public int getVehicleState(){
       /* if  (TrafficLight.getColor()==Color.RED){
            vehicleState= 0;

        }
        else if (TrafficLight.getColor()==Color.GREEN ){
            vehicleState= 1;
        }*/
        return  vehicleState;
    }

    public void setVehicleState(int vehicleState){this.vehicleState=vehicleState;}
    public void stopVehicle(){vehicleState=0;}

    public void setVehicleBehavior(){}
    public void getVehicleBehavior(){}
    


    
    /* I think we need a setLane() method, so when a car is in an intersection and wants to turn
     left, right or straight,is has to choose a new lane, for example to turn left:
     Vehicle.setlane(Left or laneID);*/

    // vehicle chooese its direction randomly

  /* public void vehicleDirection(){
        switch (cDirection.randomLetter()){
            case STRAIGHT:
                moveVehicle(vehicleId);
                break;

            case LEFT:
                //if (eastroad)
               // Vechile.setlane(laneID);
                break;

            case RIGHT:

                break;
        }
    }*/




}

