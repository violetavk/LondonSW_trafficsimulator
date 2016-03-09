package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.components.Lane;


public class Ambulance extends Vehicle {


    /**
     * Create a vehicle and set its position by specify a cell in a lane
     *
     * @param currentCell the cell to set vehicle in, in the lane
     * @param currentLane the lane to set vehicle in
     */
    //TODO: Ambulance ignoring traffic light and blinking lights
    // TODO: figure out why ambulance gets stuck after moving forward
    public Ambulance(int currentCell, Lane currentLane) {
        super(currentCell, currentLane);
        this.vehicleLength=1;
        this.vehicleSpeed=1.0;
        this.vehiclePriority = 5;
        this.vehicleBehavior = vehicleBehavior.AGGRESSIVE;

    }
}
