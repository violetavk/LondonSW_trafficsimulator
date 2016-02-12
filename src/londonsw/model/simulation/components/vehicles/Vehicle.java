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
    double vehicleSpeed;
    private Vehicle[] lane;

    public Vehicle(Coordinate vehicleLocation, int vehicleId) {
        this.vehicleLocation = vehicleLocation;
        this.vehicleId = vehicleId;
    }

    public int getVehicleLength() {
        return vehicleLength;
    }

    public Coordinate getVehicleLocation() {
        return vehicleLocation;
    }

    public int getDirection() {
        return direction;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }


    public boolean moveCar(int currCell) {

        Vehicle v = lane[currCell];
        if(v == null) {
            return false;
        }
        else {
            lane[currCell+1] = v;
            lane[currCell] = null;
            return true;
        }

    }

    public void turn () {};
    public void priority(){};
}

