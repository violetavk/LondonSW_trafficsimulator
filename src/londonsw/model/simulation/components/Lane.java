package londonsw.model.simulation.components;
import londonsw.model.simulation.components.vehicles.Vehicle;

import java.io.Serializable;

/**
 * This class is where the vehicles actually move
 * This is based on the cell automaton model of simulation
 * Each lane is an "queue" and has a direction
 * Number slots in the lane will be based on the number of "cells" in the view the road/lane takes up
 */
public class Lane implements Serializable {

    private static final long serialVersionUID = 7899381124564682583L;
    private Vehicle[] lane;
    private int length;
    private Coordinate entry;
    private Coordinate exit;
    private MapDirection movingDirection;
    private Road road;
    private Intersection endIntersection;
    private int RoadIndex;
    private int state;
    private int id;
    private static  int counter=0;


    /**
     * Creates a lane and sets its first and last cell and it's moving direction
     * and calculate the length of a lane and gives it a unique Id
     * stes the state to 1 which means a lane is enabled.
     * @param entry first cell in a lane in type of Coordinate
     * @param exit last cell in a lane un type of Coordinate
     * @param movingDirection the moving direction  of a lane in type of Map direction
     * @throws NotALaneException
     */
    public Lane(Coordinate entry, Coordinate exit, MapDirection movingDirection) throws NotALaneException {
        this.entry = entry;
        this.exit = exit;
        this.movingDirection = movingDirection;
        length = this.getLaneLength();
        lane = new Vehicle[length];
        id=++counter;
        this.setState(1);
    }


    /**
     * Gets the vehicle in last cell in a lane
     * @return the vehicle in an intersection if there is any in type of vehicle
     * if there is no, it returns null
     * @throws Exception
     */
    public Vehicle getVehicleInIntersection() throws Exception {
        if (lane[length-1] != null)
            return lane[length-1];
        else
            return null;
    }

    /**
     * Gets a lane's state
     * 1 if a lane is enabled
     * 0 if it is disabled
     *
     * @return the state of a lane in type of integer
     */
    public int getState() {
        return state;
    }

    /**
     * Gets a unique ID for the lane
     * @return a lane's ID in type of integer
     */
    public int getId() {
        return id;
    }


    /**
     * Sets an ID to the lane
     * @param id is a unique Id for a lane in type of integer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets a state of a lane
     * @param state is the state of a lane in type of integer
     *              where 1 is enabled  and 0 is disabled
     */
    public void setState(int state) {
        this.state = state;
    }


    /**
     * Gets the length of a lane,
     * and checks if it is a legal lane : which means the entry and exit coordinate of a lane must have
     * either same x's or y's
     *
     * @return the lane length in type od integer
     * @throws NotALaneException if the lane coordinates are not legal.
     */
    private int getLaneLength() throws NotALaneException {
        int aX = entry.getX();
        int aY = entry.getY();
        int bX = exit.getX();
        int bY = exit.getY();
        int length;

        if (aX == bX) {
            length = Math.abs(aY - bY) + 1;
            return length;
        } else if (aY == bY) {
            length = Math.abs(aX - bX) + 1;
            return length;
        } else
            throw new NotALaneException("Not a lane. Coordinate x or y must match for both");
    }

    /**
     * Gets the length of a lane
     * @return the lane length in type of integer
     */
    public int getLength() {
        return length;
    }

    /**
     * Checks if a given cell is empty
     * @param cell is a cell in a lane in type of integer
     * @return the true if a cell is empty and false if not
     */
    public boolean isCellEmpty(int cell) {
        if (cell < 0 || cell > this.length)
            return false;

        if (lane[cell] == null)
            return true;
        return false;
    }

    /**
     * Checks if a lane is full
     * @return true if a lane is full, false otherwise
     */
    public boolean isFull ()
    {
        for (int i=0; i<this.getLength();i++)
        {if (this.isCellEmpty(i))
            return false;
        }
        return true;
    }

    /**
     * Gets the entry coordinate of a lane
     * @return the lane entry in type of coordinate
     */
    public Coordinate getEntry() {
        return entry;
    }

    /**
     * Gets the exit coordinate of a lane
     * @return the lane exit in type of coordinate
     */
    public Coordinate getExit() {
        return exit;
    }

    /**
     * Gets the moving direction of a lane
     * @return the lane moving direction in type of MapDirection
     */
    public MapDirection getMovingDirection() {
        return movingDirection;
    }

    /**
     * Gets the road that a lane is belongs to
     * @return the Road that lane in it in type of road
     */
    public Road getRoad() {return road; }

    /**
     * Sets the road that a alane belongs to
     * @param road to set it to a lane in type of road
     */
    public void setRoad(Road road) {this.road = road;}


    /**
     * Gets the end Intersection of a lane,
     * @return the lane End intersection in type of Intersection
     */
    public Intersection getEndIntersection() {
        return endIntersection;
    }

    /**
     * Sets the end intersection for a lane
     *
     * then sets the intersection to the
     * and checks if the intersection is in the right coordinates
     * and if it matches the correct lane direction
     * @param endIntersection is the intersection to set to the lane
     */
    public void setEndIntersection(Intersection endIntersection) {
        int x =endIntersection.getLocation().getX();
        int y =endIntersection.getLocation().getY();

        if ((this.getMovingDirection()==MapDirection.NORTH)&&(this.getExit().getX()==x)&&(this.getExit().getY()==y+1) )
        {this.endIntersection = endIntersection;}

        else if ((this.getMovingDirection()==MapDirection.SOUTH)&&(this.getExit().getX()==x)&&(this.getExit().getY()==y-1) )
        {this.endIntersection = endIntersection;}

        else if((this.getMovingDirection()==MapDirection.EAST)&&(this.getExit().getX()==x-1)&&(this.getExit().getY()==y) )
        {this.endIntersection = endIntersection;}

        else if((this.getMovingDirection()==MapDirection.WEST)&&(this.getExit().getX()==x+1)&&(this.getExit().getY()==y) )
        {this.endIntersection = endIntersection;}

    }

    /**
     * @param v is a vehicle to set it to a lane
     * @param cell is a cell in a lane to set vehicle on it
     * @return true if a vehicle is setted
     * false if the cell is out of bound
     */
    public boolean setCell(Vehicle v, int cell) {
        if (cell < 0 || cell >= length)
            return false;
        
        lane[cell] = v;
        return true;
    }




    public int getRoadIndex() {
        return RoadIndex;
    }
    public Vehicle get(int i) {
        return lane[i];
    }
    public void setRoadIndex(int roadIndex) {
        RoadIndex = roadIndex;
    }

    public static boolean Rotate(Lane lane1, Lane lane2){
        if (lane1.getMovingDirection()==lane2.getMovingDirection()){
            return false;
        }
        return true;
    }
}

class NotALaneException extends Exception {
    public NotALaneException() { super(); }
    public NotALaneException(String msg) { super(msg); }
    public NotALaneException(String msg, Throwable t) { super(msg,t); }
    public NotALaneException(Throwable t) { super(t); }
}
