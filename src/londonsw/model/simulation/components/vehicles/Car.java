package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.VehicleBehavior;

import java.io.Serializable;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */


public class Car extends Vehicle implements Serializable, ICar {

    private static final long serialVersionUID = -3555254273903868035L;
    private static int idCounter = 0;
    private int id;

    public Car(int currentCell,Lane currentLane) {
        super(currentCell,currentLane);
        this.vehicleLength=1;
        this.vehicleSpeed=1.0;
        this.vehiclePriority = 1;
        this.vehicleBehavior = VehicleBehavior.AVERAGE; // default

        id = idCounter++;
    }

    public int getCarId() { return id; }

  }



