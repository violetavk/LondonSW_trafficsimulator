package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Coordinate;

/**
 * This is the interface that all vehicles will implement
 * This allows for scalability because we can add more types of cars (eg. ambulance, bus)
 */
public abstract class Vehicle {


    public Coordinate vehicleLocation;
    int vehicleId;
    int vehicleLength;
    int direction;

    public Vehicle(Coordinate vehicleLocation, int vehicleId,  int direction) {
        this.vehicleLocation = vehicleLocation;
        this.vehicleId = vehicleId;
        this.direction = direction;
    }

    public int getVehicleLength() {
        return vehicleLength;
    }


    public void moveForward() {};
    public void turn () {};
    public void priority(){};


}

