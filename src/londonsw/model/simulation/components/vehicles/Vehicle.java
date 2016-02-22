package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.TickerListener;
import londonsw.model.simulation.components.*;


/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public abstract class Vehicle implements TickerListener{
    // TODO this is supposed to be abstract!!

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
        this.currentLane = currentLane;
        this.currentLane.setCell(this,currentCell);
    }

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


    //Move a vehicle some steps forward
    public boolean moveVehicle(int step) {
       int curCell= this.getCurrentCell();
        if(curCell+step >= this.currentLane.getLength() || (!this.currentLane.isCellEmpty(curCell+step)) ) {
           return false;
        }
       else {
            currentLane.setCell(null,curCell);
            curCell= curCell+step;
            this.setCurrentCell(curCell,this.getCurrentLane());
            currentLane.setCell(this,curCell);

            return true;

       }
    }

    public void readTrafficLight(){
        if (this.getCurrentCell() == this.currentLane.getLength() -1) {
           this.getCurrentLane().getMovingDirection();
            TrafficLight light= new TrafficLight();
           switch (this.getCurrentLane().getMovingDirection()){
               case NORTH:
                   light= this.getCurrentLane().getRoad().getIntersection().getSouthTrafficLight();
                   break;
               case SOUTH:
                   light= this.getCurrentLane().getRoad().getIntersection().getNorthTrafficLight();
                   break;
               case EAST:
                   light= this.getCurrentLane().getRoad().getIntersection().getWestTrafficLight();
                   break;
               case WEST:
                   light= this.getCurrentLane().getRoad().getIntersection().getEastTrafficLight();
                   break;
               case ERROR:
                   System.out.println("Error Direction !");
                   break;
           }
            if (light.getState()==LightColour.RED)
                this.setVehicleState(0);
            else
                this.setVehicleState(1);
          
        }

    }

    public void vehicleDirection () {
        if (this.getCurrentCell() == this.currentLane.getLength() -1){
            //this.getCurrentLane().getRoad().getIntersection().chooseDirection();
            //this.setCurrentCell(0,);
        }

    }

    //1 is moving, 0 is static
    public void stopVehicle() {
        vehicleState = 0;
    }


    @Override
    public void onTick(long time) {
        System.out.println("Car heard tick, time is " + time);
        VehicleBehavior behavior = this.getVehicleBehavior();
        if(behavior == VehicleBehavior.AVERAGE) {
            this.moveVehicle(1);
        }
        else if(behavior == VehicleBehavior.AGGRESSIVE) {
            this.moveVehicle(2);
        }
        else if(behavior == VehicleBehavior.CAUTIOUS) {
            this.moveVehicle(1);
            // maybe some other characteristics that make it "cautious"
        }
        else
            this.moveVehicle(1); // default behaviour = Average
    }
}

