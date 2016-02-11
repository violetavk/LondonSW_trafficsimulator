package londonsw.model.simulation.components.vehicles;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */

// Add Car class layout
public class Car implements Vehicle {

   //Location (x,y)
    // x represents lane
    // y represents cell

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
    int carID;
    int status=0; // 0= still , 1 = moving

    //getter
    public int getStatus(){return status;}
    public int getID (){return carID;}


    //setter
    public void setStatus(int status){this.status=status;}
    public void setID(int id){this.carID=id;}

    public void moveforward () {};
    public void turn () {};
    public void priority(){};


}
