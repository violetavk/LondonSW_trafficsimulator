package londonsw.model.simulation.components.vehicles;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */

// Add Car class layout
public class Car implements Vehicle {

   //Location (x,y)
    class location {
       int x=0;
       int y=0;

       // constructor
       public location (int x, int y)
       {
           this.x=x;
           this.y=y;
       }

       //getter
       public int getX (){return x;}
       public int getY(){return y;}

       //setter
       public void setX (int x){this.x=x;}
       public void setY (int y){this.y=y;}

   }

    int direction;

    public void moveforward () {};
    public void turn () {};


}
