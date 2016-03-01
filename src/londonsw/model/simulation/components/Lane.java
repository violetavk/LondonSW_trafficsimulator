package londonsw.model.simulation.components;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.vehicles.Vehicle;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * This class is where the vehicles actually move
 * This is based on the cell automaton model of simulation
 * Each lane is an "queue" and has a direction
 * Number slots in the lane will be based on the number of "cells" in the view the road/lane takes up
 */
public class Lane implements Serializable {

    private Vehicle[] lane;
    private int length;
    private Coordinate entry;
    private Coordinate exit;
    private MapDirection movingDirection;
    private Road road;
    private Intersection intersection;
    private Intersection laneIntersection;

    public MapDirection getMovingDirection() {
        return movingDirection;
    }

    public Lane(Coordinate entry, Coordinate exit, MapDirection movingDirection,Road road) throws NotALaneException {
        this.entry = entry;
        this.exit = exit;
        this.movingDirection = movingDirection;
        length = this.getLaneLength();
        lane = new Vehicle[length];
        this.road=road;
    }

    public Vehicle get(int i) {
        return lane[i];
    }

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

    public int getLength() {
        return length;
    }

    public boolean isCellEmpty(int cell) {
        if (cell < 0 || cell > this.length)
            return false;

        if (lane[cell] == null)
            return true;
        return false;
    }

    public Coordinate getEntry() {
        return entry;
    }
    public Coordinate getExit() {
        return exit;
    }

    public Road getRoad() {return road; }

    public void setRoad(Road road) {this.road = road;}

   /* public void setIntersection(Intersection intersection) {
        if((this.getMovingDirection()== MapDirection.WEST) &&
                (intersection.getLocation().getX()== this.getEntry().getX()-1) &&
                (intersection.getLocation().getY()== this.getEntry().getY()))
        {this.intersection = intersection;}

        if((this.getMovingDirection()== MapDirection.EAST) &&
                (intersection.getLocation().getX()== this.getExit().getX()+1) &&
                (intersection.getLocation().getY()== this.getExit().getY()))
        {this.intersection = intersection;}

        if((this.getMovingDirection()== MapDirection.NORTH) &&
                (intersection.getLocation().getX()== this.getEntry().getX()) &&
                (intersection.getLocation().getY()== this.getEntry().getY()-1))
        {this.intersection = intersection;}

        if((this.getMovingDirection()== MapDirection.SOUTH) &&
                (intersection.getLocation().getX()== this.getExit().getX()) &&
                (intersection.getLocation().getY()== this.getExit().getY()+1))
        {this.intersection = intersection;}

    }

    public Intersection getIntersection() {
        return intersection;
    }*/

    public boolean setCell(Vehicle v, int cell) {
        if (cell < 0 || cell >= length)
            return false;
        
        lane[cell] = v;
        return true;
    }


     public Intersection getLaneIntersection() throws NotACoordinateException {
        // Map map = new Map(30,30);

        Road r = this.getRoad();
        MapDirection m= this.movingDirection;
        Coordinate c ;
        Component someThing;

      if(m==MapDirection.EAST) {
            c = r.getEndLocation();
            someThing = Map.getAtLocation(new Coordinate(c.getX() + 1, c.getY()));
            if (someThing instanceof Intersection) {
                this.laneIntersection = (Intersection) someThing;
            } else if (someThing instanceof Road) {
                // use start
                }
                else {//error
            }
        }

           if (m== MapDirection.WEST)
           {
               c = r.getStartLocation();
               someThing = Map.getAtLocation(new Coordinate(c.getX() - 1, c.getY()));

               if (someThing instanceof Intersection) {
                   this.laneIntersection = (Intersection) someThing;
               } else if (someThing instanceof Road) {
                   // use start
               }
               else {//error
               }
           }

        if (m== MapDirection.NORTH)
        {
            c = r.getStartLocation();
            someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()-1));

            if (someThing instanceof Intersection) {
                this.laneIntersection = (Intersection) someThing;
            } else if (someThing instanceof Road) {
                // use start
            }
            else {//error
            }
        }
        if(m==MapDirection.SOUTH) {
            c = r.getEndLocation();
            someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()+1));
            if (someThing instanceof Intersection) {
                this.laneIntersection = (Intersection) someThing;
            } else if (someThing instanceof Road) {
                // use start
            }
            else {//error
            }
        }

        return laneIntersection;}


     /*public Coordinate getIntersectionCoordinate()throws NotACoordinateException {
        Coordinate laneCoordinate= null;
       Road r = this.getRoad();
        Coordinate c ;
        if (this.getMovingDirection()==MapDirection.NORTH)
        { c = r.getStartLocation();
            laneCoordinate =new Coordinate(c.getX(), c.getY()-1);}
        else if (this.getMovingDirection()==MapDirection.SOUTH) {
            c = r.getEndLocation();
            laneCoordinate =new Coordinate(c.getX(), c.getY()+1);}
        else if(this.getMovingDirection()==MapDirection.EAST) {
            c = r.getEndLocation();
            laneCoordinate= new Coordinate(c.getX() + 1, c.getY());}
        else if (this.getMovingDirection()== MapDirection.WEST) {
            c = r.getStartLocation();
            laneCoordinate=new Coordinate(c.getX() - 1, c.getY());}

            return laneCoordinate;
    }*/
}

class NotALaneException extends Exception {
    public NotALaneException() { super(); }
    public NotALaneException(String msg) { super(msg); }
    public NotALaneException(String msg, Throwable t) { super(msg,t); }
    public NotALaneException(Throwable t) { super(t); }
}
