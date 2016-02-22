package londonsw.model.simulation.components.vehicles;
import londonsw.model.simulation.TickerListener;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;

/**
 * An implementation of a vehicle
 * This moves in a lane
 * Only moves forwards when the slot in front of it is empty
 */


public class Car extends Vehicle implements TickerListener {

    public Car(int vehicleId,Lane currentLane) {
        super( vehicleId,currentLane);
        this.vehicleLength=1;
        this.vehicleSpeed=1.0;
        this.vehiclePriority = 1;
    }

    public void onTick(long time) {
        super.onTick(time);
    }
  }



