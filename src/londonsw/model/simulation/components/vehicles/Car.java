package londonsw.model.simulation.components.vehicles;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */

// Add Car class layout
public class Car implements Vehicle {

    int location;
    int direction;

public void setLocation (int location){};
public int getlocation (){ return location; };

    public void moveforward () {};
    public void turn () {};


}
