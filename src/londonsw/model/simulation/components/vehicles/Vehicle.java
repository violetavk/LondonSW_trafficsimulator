package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.TickerListener;
import londonsw.model.simulation.components.*;

import java.util.ArrayList;
import java.util.Random;


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

    public ArrayList<Lane> laneOptions = new ArrayList<Lane>();
    private Random randomDirection;


    // debug only
    int timesTicked;

    
    //Constructor
    public Vehicle( int currentCell, Lane currentLane) {
        this.currentCell = currentCell;
        this.currentLane = currentLane;
        this.currentLane.setCell(this,currentCell);
        Ticker.subscribe(this);

        timesTicked = 0;
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
            System.out.println("Car moved from " + (curCell-step) + " to " + curCell);
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

    public ArrayList<Lane> getLaneOptions(){
        laneOptions.clear();
        int i=0;

        if ((this.currentLane.getRoad().getIntersection().getEastRoad()!=null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.WEST))
        {laneOptions.addAll(this.currentLane.getRoad().getIntersection().getEastRoad().getLanes());}

        if ((this.currentLane.getRoad().getIntersection().getSouthRoad()!= null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.NORTH))
        {laneOptions.addAll(this.currentLane.getRoad().getIntersection().getSouthRoad().getLanes());}

        if ((this.currentLane.getRoad().getIntersection().getNorthRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.SOUTH))
        {laneOptions.addAll(this.currentLane.getRoad().getIntersection().getNorthRoad().getLanes());}

        if ((this.currentLane.getRoad().getIntersection().getWestRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.EAST))
        {laneOptions.addAll(this.currentLane.getRoad().getIntersection().getWestRoad().getLanes());}

        return laneOptions;
    }

    public void vehicleTurn () {
        Lane l;
        randomDirection = new Random();
        int size = randomDirection.nextInt(this.getLaneOptions().size());
        l= this.getLaneOptions().get(size);

        if ((this.getCurrentCell() == this.currentLane.getLength() -1)&& (l.isCellEmpty(l.getLength()-1))){
            this.setCurrentCell(0,l);
        }
    }

    //1 is moving, 0 is static
    public void stopVehicle() {
        vehicleState = 0;
    }


    @Override
    public void onTick(long time) {
        System.out.println("Car heard tick, time is " + time);
        long startTime = System.currentTimeMillis();
//        VehicleBehavior behavior = this.getVehicleBehavior();
        if(vehicleBehavior == VehicleBehavior.AVERAGE) {
            System.out.println("About to move one slot - AVERAGE");
            this.moveVehicle(1);
        }
        else if(vehicleBehavior == VehicleBehavior.AGGRESSIVE) {
            System.out.println("About to move two slots - AGGRESSIVE");
            this.moveVehicle(2);
        }
        else if(vehicleBehavior == VehicleBehavior.CAUTIOUS) {
            System.out.println("About to move one slot - CAUTIOUS");
            this.moveVehicle(1);
            // maybe some other characteristics that make it "cautious"
        }
        else
            this.moveVehicle(1); // default behaviour = Average
        long endtime = System.currentTimeMillis();
        System.out.println("Moving took " + (endtime-startTime) + " millis");
        timesTicked++;
        System.out.println("Times ticked: " + timesTicked + "\n");
    }
}

