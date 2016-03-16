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
    // private Intersection intersection;
    //private Intersection laneIntersection;
    private Intersection endIntersection;
    private int RoadIndex;
    private int state;

    public String getLaneID() {
        return laneID;
    }

    public void setLaneID(String laneID) {
        this.laneID = laneID;
    }

    private String laneID;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public int getRoadIndex() {
        return RoadIndex;
    }

    public void setRoadIndex(int roadIndex) {
        RoadIndex = roadIndex;
    }

    public Lane(Coordinate entry, Coordinate exit, MapDirection movingDirection) throws NotALaneException {
        this.entry = entry;
        this.exit = exit;
        this.movingDirection = movingDirection;
        length = this.getLaneLength();
        lane = new Vehicle[length];
        this.setState(1);
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

    public boolean isFull ()
    {
        for (int i=0; i<this.getLength();i++)
        {if (this.isCellEmpty(i))
            return false;
        }
        return true;
    }

    public Coordinate getEntry() {
        return entry;
    }
    public Coordinate getExit() {
        return exit;
    }
    public MapDirection getMovingDirection() {
        return movingDirection;
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


    public Intersection getEndIntersection() {
        return endIntersection;
    }

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

    public boolean setCell(Vehicle v, int cell) {
        if (cell < 0 || cell >= length)
            return false;
        
        lane[cell] = v;
        return true;
    }
    public static boolean Rotate(Lane lane1, Lane lane2){
        if (lane1.getMovingDirection()==lane2.getMovingDirection()){
            return false;
        }
        return true;
    }

/*
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
                c = r.getStartLocation();
                someThing = Map.getAtLocation(new Coordinate(c.getX() + 1, c.getY()));
                if (someThing instanceof Intersection) {
                    this.laneIntersection = (Intersection) someThing;}
                }
                else {System.out.print("Error request ");
            }
        }

           if (m== MapDirection.WEST)
           {
               c = r.getStartLocation();
               someThing = Map.getAtLocation(new Coordinate(c.getX() - 1, c.getY()));
               if (someThing instanceof Intersection) {
                   this.laneIntersection = (Intersection) someThing;
               } else if (someThing instanceof Road) {
                   c = r.getEndLocation();
                   someThing = Map.getAtLocation(new Coordinate(c.getX() - 1, c.getY()));
                   if (someThing instanceof Intersection) {
                       this.laneIntersection = (Intersection) someThing;}
               }
               else {System.out.print("Error request ");
               }
           }

        if (m== MapDirection.NORTH)
        {
            c = r.getStartLocation();
            someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()-1));

            if (someThing instanceof Intersection) {
                this.laneIntersection = (Intersection) someThing;
            } else if (someThing instanceof Road) {
                c = r.getEndLocation();
                someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()-1));

                if (someThing instanceof Intersection) {
                    this.laneIntersection = (Intersection) someThing;
                }
            }
            else {System.out.print("Error request ");
            }
        }
        if(m==MapDirection.SOUTH) {
            c = r.getEndLocation();
            someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()+1));
            if (someThing instanceof Intersection) {
                this.laneIntersection = (Intersection) someThing;
            } else if (someThing instanceof Road) {
                c = r.getStartLocation();
                someThing = Map.getAtLocation(new Coordinate(c.getX(), c.getY()+1));
                if (someThing instanceof Intersection) {
                    this.laneIntersection = (Intersection) someThing;
                }
            }
            else {System.out.print("Error request ");
            }
        }

        return laneIntersection;}

*/
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
