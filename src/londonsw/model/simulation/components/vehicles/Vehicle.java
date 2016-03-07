package londonsw.model.simulation.components.vehicles;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;
import rx.Subscriber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * This is the abstract class that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 *
 * This uses RxJava's Subscriber class to subscribe to the Ticker (that has as Observable). On each tick,
 * the onNext(..) method runs.
 */
public abstract class Vehicle extends Subscriber<Long> implements Serializable {

    private static final long serialVersionUID = -4552832373570448039L;
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

    public Lane getPreviousLane() {
        return previousLane;
    }

    public void setPreviousLane(Lane previousLane) {
        this.previousLane = previousLane;
    }

    private Lane previousLane;

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
                   light= this.getCurrentLane().getEndIntersection().getSouthTrafficLight();
                   break;
               case SOUTH:
                   light= this.getCurrentLane().getEndIntersection().getNorthTrafficLight();
                   break;
               case EAST:
                   light= this.getCurrentLane().getEndIntersection().getWestTrafficLight();
                   break;
               case WEST:
                   light= this.getCurrentLane().getEndIntersection().getEastTrafficLight();
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

        if ((this.currentLane.getEndIntersection().getEastRoad()!=null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.WEST))
        {for(int i=0; i<this.currentLane.getEndIntersection().getEastRoad().getNumberLanes();i++)
             {if((this.currentLane.getEndIntersection().getEastRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.EAST)
                 && (this.currentLane.getEndIntersection().getEastRoad().getLaneAtIndex(i).getState()==1))
                 {laneOptions.add(this.currentLane.getEndIntersection().getEastRoad().getLaneAtIndex(i));
                 }}}

        if ((this.currentLane.getEndIntersection().getSouthRoad()!= null) &&
                (this.currentLane.getMovingDirection()!=MapDirection.NORTH))
        {for(int i=0; i<this.currentLane.getEndIntersection().getSouthRoad().getNumberLanes();i++)
             {if((this.currentLane.getEndIntersection().getSouthRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.SOUTH)
                 && (this.currentLane.getEndIntersection().getSouthRoad().getLaneAtIndex(i).getState()==1))
                {laneOptions.add(this.currentLane.getEndIntersection().getSouthRoad().getLaneAtIndex(i));
                }}}

        if ((this.currentLane.getEndIntersection().getNorthRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.SOUTH))
        {for(int i=0; i<this.currentLane.getEndIntersection().getNorthRoad().getNumberLanes();i++)
            {if((this.currentLane.getEndIntersection().getNorthRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.NORTH)
                    && (this.currentLane.getEndIntersection().getNorthRoad().getLaneAtIndex(i).getState()==1))
               {laneOptions.add(this.currentLane.getEndIntersection().getNorthRoad().getLaneAtIndex(i));
               }}}

        if ((this.currentLane.getEndIntersection().getWestRoad()!= null)&&
                (this.currentLane.getMovingDirection()!=MapDirection.EAST))
        {for(int i=0; i<this.currentLane.getEndIntersection().getWestRoad().getNumberLanes();i++)
            {if((this.currentLane.getEndIntersection().getWestRoad().getLaneAtIndex(i).getMovingDirection()== MapDirection.WEST)
                    && (this.currentLane.getEndIntersection().getWestRoad().getLaneAtIndex(i).getState()==1))
               {laneOptions.add(this.currentLane.getEndIntersection().getWestRoad().getLaneAtIndex(i));
               }}}

        return laneOptions;
    }

    public void vehicleTurn() throws Exception {
        Lane oldLane = this.currentLane;
        Lane l;
        int num = this.getLaneOptions().size();

        if (num > 0) {
        randomDirection = new Random();
        int size = randomDirection.nextInt(this.getLaneOptions().size());
        l= this.getLaneOptions().get(size);

            //validate if its end of lane
            if ((this.getCurrentCell() == this.currentLane.getLength() - 1) && (l.isCellEmpty(0)))
                {
                    oldLane.setCell(null, oldLane.getLength() - 1);
                    this.setCurrentLane(l);
                    this.setCurrentCell(0, l);
                    this.setCurrentCoordinate(l.getEntry());
                }
            }else {this.setVehicleState(0);}
    }


/*
    //1 is moving, 0 is static
    public void stopVehicle() {
        vehicleState = 0;
    } */

    /**
     * This is the method that gets called when the ticker terminates (i.e. the stop() method was called
     * on the ticker). Left not implemented on purpose
     */
    @Override
    public void onCompleted() {    }

    /**
     * If there's some error with the ticker and this subscriber, this method would call.
     * Left not implemented on purpose
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {    }

    /**
     * This is like the onTick method. This is what cars would do when the ticker ticks.
     * @param aLong this gives the current time in the system to the car (although it is probably not required)
     */
    @Override
    public void onNext(Long aLong) {
        System.out.print("Tick! " + aLong + "     ");
        System.out.println("Location: " + this.getCurrentCoordinate().getX() + "," + this.getCurrentCoordinate().getY());
        try {
            if (vehicleBehavior == VehicleBehavior.AVERAGE) {
                VehicleController.moveOnTick(this,1);
            } else if (vehicleBehavior == VehicleBehavior.AGGRESSIVE) {
                VehicleController.moveOnTick(this,2);
            } else if (vehicleBehavior == VehicleBehavior.CAUTIOUS) {
                VehicleController.moveOnTick(this,1);
            } else {
                VehicleController.moveOnTick(this,1); // default behaviour
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

