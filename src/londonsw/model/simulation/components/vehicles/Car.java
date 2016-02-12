package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Coordinate;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */

// Add Car class layout
public class Car extends Vehicle {



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
