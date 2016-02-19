package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.VehicleBehavior;
//new

/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public  class Vehicle {

    int vehicleLength;
    double vehicleSpeed;
    int currentCell;
    int vehiclePriority;// 1 is the lowest
    int vehicleState;
    VehicleBehavior vehicleBehavior;
    public Lane currentLane;


    
    //Constructor
    public Vehicle( int currentCell, Lane currentLane) {
        this.currentCell = currentCell;
        this.currentLane = currentLane;}

    //Getter
    public int getVehicleLength() {return vehicleLength;}
    public double getVehicleSpeed() {return vehicleSpeed;}
    public int getVehiclePriority(){return  vehiclePriority;}
    public Lane getCurrentLane(){return currentLane;}
    public int getCurrentCell(){return currentCell;}
    public int getVehicleState(){return  vehicleState;}
    public VehicleBehavior getVehicleBehavior(){return VehicleBehavior.randomLetter(); }

    //Setter
    public void setVehicleLength(int vehicleLength){this.vehicleLength=vehicleLength;}
    public void setVehicleSpeed(double vehicleSpeed){this.vehicleSpeed=vehicleSpeed;}
    public void setVehiclePriority(int vehiclePriority){this.vehiclePriority=vehiclePriority;}
    public void setCurrentLane(Lane currentLane){this.currentLane =currentLane;}
    public void setCurrentCell(int curCell,Lane currentLane){this.currentCell=curCell;}
    public void setVehicleState(int vehicleState){this.vehicleState=vehicleState;}
    public void setVehicleBehavior(VehicleBehavior vehicleBehavior){this.vehicleBehavior=vehicleBehavior;}


    //Move a vehicle one step forward
    public boolean moveVehicle(int step) {
       int curCell= this.getCurrentCell();
        if(curCell+step >= this.currentLane.getLength()) {
           return false;
        }
       else {
            curCell= curCell+step;
            this.setCurrentCell(curCell,this.getCurrentLane());
            return true;
       }
    }

    //1 is moving, 0 is static
    public void stopVehicle(){vehicleState=0;}


}

