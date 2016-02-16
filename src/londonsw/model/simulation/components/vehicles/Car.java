package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Coordinate;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */


public class Car extends Vehicle {

    public Car(Coordinate vehicleLocation, int vehicleId) {
        super(vehicleLocation, vehicleId );
        this.vehicleLength=1;
        this.vehicleSpeed=1.0;
        this.vehicleLifetime =10;
    }
  }



