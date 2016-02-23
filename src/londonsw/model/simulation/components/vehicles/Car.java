package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.TickerListener;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.VehicleBehavior;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */


public class Car extends Vehicle implements TickerListener {

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



