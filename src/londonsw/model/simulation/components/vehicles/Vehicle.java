package londonsw.model.simulation.components.vehicles;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.TickerListener;
import londonsw.model.simulation.components.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public abstract class Vehicle implements TickerListener, Serializable{

    int vehicleLength;
    double vehicleSpeed;
    int currentCell;
    int vehiclePriority;// 1 is the lowest
    int vehicleState;
    VehicleBehavior vehicleBehavior;
    public Lane currentLane;
    public ArrayList<Lane> laneOptions = new ArrayList<Lane>();
    private Random randomDirection;
    Lane l;
    Coordinate currentCoordinate;

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

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    //Getter
    public int getVehicleLength() {return vehicleLength;}
    public double getVehicleSpeed() {return vehicleSpeed;}
    public int getVehiclePriority(){return  vehiclePriority;}
    public Lane getCurrentLane(){return currentLane;}
    public int getCurrentCell(){return currentCell;}
    public int getVehicleState(){return  vehicleState;}
    public VehicleBehavior getVehicleBehavior(){return VehicleBehavior.randomLetter(); }
    /* getting the current coordinate of the car */
    public Coordinate getCurrentCoordinate()
    {
        int currentCell = this.getCurrentCell();
        Lane currentLane = this.getCurrentLane();
        Coordinate coordinate = new Coordinate();

        MapDirection mapDirection = currentLane.getMovingDirection();


        switch (mapDirection)
        {
            case NORTH:
                coordinate.setX(currentLane.getEntry().getX());
                coordinate.setY(currentLane.getEntry().getY() - currentCell);
                break;

            case SOUTH:
                coordinate.setX(currentLane.getEntry().getX());
                coordinate.setY(currentLane.getEntry().getY() + currentCell);
                break;

            case EAST:
                coordinate.setX(currentLane.getEntry().getX()+currentCell);
                coordinate.setY(currentLane.getEntry().getY());
                break;

            case WEST:
                coordinate.setX(currentLane.getEntry().getX()-currentCell);
                coordinate.setY(currentLane.getEntry().getY());
                break;
        }


        return coordinate;
    }

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

//            System.out.println("Car moved from " + (curCell-step) + " to " + curCell);

            this.setCurrentCoordinate(this.getCurrentCoordinate());

            return true;

       }
    }

    public void readTrafficLight()throws Exception {
        if (this.getCurrentCell() == this.currentLane.getLength() -1) {
           //this.getCurrentLane().getMovingDirection();
            TrafficLight light= new TrafficLight();
           switch (this.getCurrentLane().getMovingDirection()){
               case NORTH:
                   light= this.currentLane.getLaneIntersection().getSouthTrafficLight();
                   break;
               case SOUTH:
                   light= this.getCurrentLane().getLaneIntersection().getNorthTrafficLight();
                   break;
               case EAST:
                   light= this.getCurrentLane().getLaneIntersection().getWestTrafficLight();
                   break;
               case WEST:
                   light= this.getCurrentLane().getLaneIntersection().getEastTrafficLight();
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


    public ArrayList<Lane> getLaneOptions() throws Exception {
        laneOptions.clear();

        if ((this.currentLane.getLaneIntersection().getEastRoad()!=null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.WEST))
        {for(int i=0; i<this.currentLane.getLaneIntersection().getEastRoad().getNumberLanes();i++)
             {if(this.currentLane.getLaneIntersection().getEastRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.EAST)
                 {laneOptions.add(this.currentLane.getLaneIntersection().getEastRoad().getLaneAtIndex(i));
                 }}}

        if ((this.currentLane.getLaneIntersection().getSouthRoad()!= null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.NORTH))
        {for(int i=0; i<this.currentLane.getLaneIntersection().getSouthRoad().getNumberLanes();i++)
             {if(this.currentLane.getLaneIntersection().getSouthRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.SOUTH)
                {laneOptions.add(this.currentLane.getLaneIntersection().getSouthRoad().getLaneAtIndex(i));
                }}}

        if ((this.currentLane.getLaneIntersection().getNorthRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.SOUTH))
        {for(int i=0; i<this.currentLane.getLaneIntersection().getNorthRoad().getNumberLanes();i++)
            {if(this.currentLane.getLaneIntersection().getNorthRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.NORTH)
               {laneOptions.add(this.currentLane.getLaneIntersection().getNorthRoad().getLaneAtIndex(i));
               }}}

        if ((this.currentLane.getLaneIntersection().getWestRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.EAST))
        {for(int i=0; i<this.currentLane.getLaneIntersection().getWestRoad().getNumberLanes();i++)
            {if(this.currentLane.getLaneIntersection().getWestRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.WEST)
               {laneOptions.add(this.currentLane.getLaneIntersection().getWestRoad().getLaneAtIndex(i));
               }}}

        return laneOptions;
    }



    public void vehicleTurn () throws Exception {
        Lane l;
        randomDirection = new Random();
        int size = randomDirection.nextInt(this.getLaneOptions().size());
        l= this.getLaneOptions().get(size);

        if ((this.getCurrentCell() == this.currentLane.getLength() -1)&& (l.isCellEmpty(l.getLength()-1)))
        {
            this.setCurrentLane(l);
            this.setCurrentCell(0,l);}
    }

/*
    //1 is moving, 0 is static
    public void stopVehicle() {
        vehicleState = 0;
    } */


    @Override
    public void onTick(long time) {
//        System.out.println("Car heard tick, time is " + time);
//        long startTime = System.currentTimeMillis();
//        VehicleBehavior behavior = this.getVehicleBehavior();
        if(vehicleBehavior == VehicleBehavior.AVERAGE) {
//            System.out.println("About to move one slot - AVERAGE");

            VehicleController.moveVehicle(this,1);
            this.moveVehicle(1);

        }
        else if(vehicleBehavior == VehicleBehavior.AGGRESSIVE) {
//            System.out.println("About to move two slots - AGGRESSIVE");
            this.moveVehicle(2);
        }
        else if(vehicleBehavior == VehicleBehavior.CAUTIOUS) {
//            System.out.println("About to move one slot - CAUTIOUS");
            this.moveVehicle(1);
            // maybe some other characteristics that make it "cautious"
        }
        else
            this.moveVehicle(1); // default behaviour = Average
//        long endtime = System.currentTimeMillis();
//        System.out.println("Moving took " + (endtime-startTime) + " millis");
//        timesTicked++;
//        System.out.println("Times ticked: " + timesTicked + "\n");
        System.out.println();
    }
}

