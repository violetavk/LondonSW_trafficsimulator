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
    private Coordinate previousCoordinate;
    private Lane previousLane;

    // debug only
    int timesTicked;
    private static  int counter=0;
    private int id;


    /**
     * Create a vehicle and set its position by specify a cell in a lane
     * @param currentCell the cell to set vehicle in, in the lane
     * @param currentLane  the lane to set vehicle in
     */
    public Vehicle( int currentCell, Lane currentLane) {
        this.currentCell = currentCell;
        this.currentLane = currentLane;
        this.currentLane.setCell(this,currentCell);
        Ticker.subscribe(this);
        timesTicked = 0;
        id=++counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the length of a vehicle
     * @return the length of a vehicle in type of integer
     * each type of vehicle has its own length
     */
    public int getVehicleLength() {return vehicleLength;}

    /**
     * Gets the speed of a vehicle
     * @return the speed of a vehicle in type of double
     * each vehicle's speed depends on its behavior
     */
    public double getVehicleSpeed() {return vehicleSpeed;}


    public int getVehiclePriority(){return  vehiclePriority;}

    /**
     * Gets the lane which is vehicle in
     * @return the lane which is vehicle in on the current time in type of lane
     */
    public Lane getCurrentLane(){return currentLane;}

    /**
     * Gets the current cell in lane which is vehicle in
     * @return cell from lane which is vehicle in on the current time , in type of integer
     */
    public int getCurrentCell(){return currentCell;}

    /**
     * Gets the state of vehicle which are 0 refers to still or 1 refers to moving
     * @return the state of vehicle in type if integer
     */
    public int getVehicleState(){return  vehicleState;}

    /**
     * Gets the behavior of a vehicle from three behaviors AVERAGE, AGGRESSIVE, and CAUTIOUS
     * @return the behavior of a vehicle in type of enum VehicleBehavior
     */
    public VehicleBehavior getVehicleBehavior(){return VehicleBehavior.randomLetter(); }

    /**
     * Gets the previous lane which vehicle was in
     * @return the previous lane in type of Lane
     */
    public Lane getPreviousLane() {
        return previousLane;
    }

    public void setPreviousCoordinate(Coordinate prev) {
        this.previousCoordinate = prev;
    }

    public void setPreviousLane(Lane previousLane) {
        this.previousLane = previousLane;
    }

    public Coordinate getPreviousCoordinate() {
        return previousCoordinate;
    }

    /* getting the current coordinate of the car */
    public Coordinate getCurrentCoordinate()
    {
        int currentCell = this.getCurrentCell();
        Lane currentLane = this.getCurrentLane();
        Coordinate coordinate = new Coordinate(0,0);

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

        this.currentCoordinate = coordinate;

        return coordinate;
    }

    public Coordinate getStoredCurrentCoordinate() {
        return currentCoordinate;
    }
    //Setter

    /**
     * @param vehicleLength the length of a vehicle in type of integer
     */
    public void setVehicleLength(int vehicleLength){this.vehicleLength=vehicleLength;}

    /**
     * @param vehicleSpeed the speed of vehicle in type of double
     */
    public void setVehicleSpeed(double vehicleSpeed){this.vehicleSpeed=vehicleSpeed;}
    public void setVehiclePriority(int vehiclePriority){this.vehiclePriority=vehiclePriority;}


    /**
     * @param currentLane a lane to set car in, in type of lane
     * @throws Exception if lane is not exist
     * set a vehicle into a new lane
     */
    public void setCurrentLane(Lane currentLane) throws Exception{
        if(currentLane != null){
            this.currentLane =currentLane;}
    else
            throw new Exception("Lane is not Exist !");
    }

    /**
     * @param curCell new cell to assign vehicle to
     * @param currentLane lane that cell takes place in
     * @throws Exception if the cell is out of bounds or is not empty
     *
     * set a vehicle into a new cell
     * check if a new cell is empty, and not out of bounds
     * not less than zero or equal or more than lane length
     */
    public void setCurrentCell(int curCell,Lane currentLane) throws Exception{
        if ((curCell >=0)&& (curCell< currentLane.getLength())&& (currentLane.isCellEmpty(curCell))){
            this.currentCell=curCell;
        }else
            throw new Exception("new cell is not available !");
    }

    /**
     * @param vehicleState in type of integer
     * set the state of vehicle
     * 0 for still and 1 for movement
     */
    public void setVehicleState(int vehicleState){this.vehicleState=vehicleState;}

    /**
     * @param vehicleBehavior in type of VehicleBehavior enum
     * sets the behavior of a vehicle
     * there are three behaviors AVERAGE, AGGRESSIVE, and CAUTIOUS
     */
    public void setVehicleBehavior(VehicleBehavior vehicleBehavior){ this.vehicleBehavior = vehicleBehavior; }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    /**
     * @param step number of steps to move, depends on the behavior
     * @return True if vehicle can move, False if not
     * @throws Exception
     *
     * vehicle can move one step or more, depends on its behavior
     * if it moves two steps, should check if this movement is available
     * if not it checks if one step is available
     * if not vehicle stops
     */
    public boolean moveVehicle(int step) throws Exception {

        if (step == 0) {
            return false;
        } else {

            int curCell = this.getCurrentCell();

            if (curCell + step >= this.currentLane.getLength() || !this.currentLane.isCellEmpty(curCell + step)) {
                moveVehicle(step - 1);
            } else {
                currentLane.setCell(null, curCell);
                curCell += step;
                this.setCurrentCell(curCell, this.getCurrentLane());
                currentLane.setCell(this, curCell);

                return true;
            }

            return true;

        }
    }

        /*
      public boolean moveVehicle(int step) throws Exception {
        int curCell= this.getCurrentCell();
        int validStep=0;
        for (int i=1;i<=step;i++){
            if (curCell+i >=this.currentLane.getLength()|| (!this.currentLane.isCellEmpty(curCell+i)))
                break;
            else
                validStep=i;
        }
        if(curCell+validStep >= this.currentLane.getLength() || (!this.currentLane.isCellEmpty(curCell+validStep)) ) {
            return false;
        }
        else {
            currentLane.setCell(null,curCell);
            curCell= curCell+validStep;
            this.setCurrentCell(curCell,this.getCurrentLane());
            currentLane.setCell(this,curCell);
            return true;
       }

       */


    /**
     * to make a vehicle reads a traffic light
     * use intersection to read the traffic light, each intersection has up to four traffic light
     * depends on the direction of a lane which vehicle is in, a vehicle can reads the corresponding traffic light
     * if the traffic light is green the vehicle state set to moving
     * if the traffic light is red the vehicle state set to still
     * @throws Exception
     */
    public void readTrafficLight()throws Exception {
        if (this.getCurrentCell() == this.currentLane.getLength() -1) {
            TrafficLight light;

            if(this.getCurrentLane().getEndIntersection()!=null)
            {
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
                default: // ERROR case
                    light = null;
                    throw new Exception("Error Direction!");
                   // break;
            }
            if(vehiclePriority>1){
                if(light.getState()== LightColour.RED){
                    this.vehicleState=1;

                }
            }

           else if(light!=null) {
                if (light.getState() == LightColour.RED)
                    this.vehicleState = 0;
                else
                    this.vehicleState = 1;
            }
        }else
            {
                System.out.println("No Intersection assigned");
                this.vehicleState = 3;  //possible state to remove vehicle
            }


        }
        else
            throw new Exception("Reading traffic light when not at end of lane");
    }

    /**
     * in each intersection this method gives vehicles options of available lanes that vehicles can move to
     * this method checks three conditions
     * 1. if the lane is exist
     * 2. if there is a space for a new vehicle
     * 3. if the direction of a lane is legal for the vehicle
     * if  a lane obtains these conditions, then it is added to laneOptions Array List
     * @return  the options of lanes that vehicle can move to in type of Array List of lanes
     * @throws Exception
     */
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

    /**
     * Chooses lane randomly from lanes options
     * vehicle turn to new chosen lane
     * if there is no lane to move to, vehicle stops
     * @throws Exception
     */
    public boolean vehicleTurn() throws Exception {
        Lane oldLane = this.currentLane;
        Lane l;
        int num = this.getLaneOptions().size();

        if (num > 0) {
        randomDirection = new Random();
        int size = randomDirection.nextInt(this.getLaneOptions().size());
        l= this.getLaneOptions().get(size);

            //validate if its end of lane
            if ((this.getCurrentCell() == this.currentLane.getLength() -1) && (l.isCellEmpty(0)) )
                {
                    oldLane.setCell(null, oldLane.getLength() - 1);
                    this.setCurrentLane(l);
                    this.setCurrentCell(0, l);
                    return true;
                }
            else {this.setVehicleState(0);
        return false;}}
        return true;
    }




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
        System.out.println("Car: "+ this.getId()+"  Location: " + this.getCurrentCoordinate().getX() + "," + this.getCurrentCoordinate().getY());
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

