package londonsw.view.simulation;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.VehicleBehavior;
import londonsw.model.simulation.components.vehicles.IVehicle;
import londonsw.model.simulation.components.vehicles.Vehicle;

import java.util.ArrayList;

/**
 * Created by felix on 26/02/2016.
 */
public abstract class VehicleDecorator implements IVehicle {

    protected Vehicle decoratedVehicle;

    public VehicleDecorator(Vehicle decoratedVehicle) {
        this.decoratedVehicle = decoratedVehicle;
    }

    @Override
    public Lane getPreviousLane() {
        return decoratedVehicle.getPreviousLane();
    }

    @Override
    public void setPreviousLane(Lane previousLane) {
        decoratedVehicle.setPreviousLane(previousLane);
    }

    public void setPreviousCoordinate(Coordinate coord) {
        decoratedVehicle.setPreviousCoordinate(coord);
    }

    @Override
    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        decoratedVehicle.setCurrentCoordinate(currentCoordinate);
    }

    @Override
    public int getVehicleLength() {
        return decoratedVehicle.getVehicleLength();
    }

    @Override
    public double getVehicleSpeed() {
        return decoratedVehicle.getVehicleSpeed();
    }

    @Override
    public int getVehiclePriority() {
        return decoratedVehicle.getVehiclePriority();
    }

    @Override
    public Lane getCurrentLane() {
        return decoratedVehicle.getCurrentLane();
    }

    @Override
    public int getCurrentCell() {
        return decoratedVehicle.getCurrentCell();
    }

    @Override
    public int getVehicleState() {
        return decoratedVehicle.getVehicleState();
    }

    @Override
    public VehicleBehavior getVehicleBehavior() {
        return decoratedVehicle.getVehicleBehavior();
    }

    @Override
    public Coordinate getCurrentCoordinate() {
        return decoratedVehicle.getCurrentCoordinate();
    }

    public Coordinate getPreviousCoordinate() {
        return decoratedVehicle.getPreviousCoordinate();
    }


    public Coordinate getStoredCurrentCoordinate() {
        return decoratedVehicle.getStoredCurrentCoordinate();
    }

    @Override
    public void setVehicleLength(int vehicleLength) {
        decoratedVehicle.setVehicleLength(vehicleLength);
    }

    @Override
    public void setVehicleSpeed(double vehicleSpeed) {
        decoratedVehicle.setVehicleSpeed(vehicleSpeed);
    }

    @Override
    public void setVehiclePriority(int vehiclePriority) {
        decoratedVehicle.setVehiclePriority(vehiclePriority);
    }

    @Override
    public void setCurrentLane(Lane currentLane) {
        decoratedVehicle.setCurrentLane(currentLane);
    }

    @Override
    public void setCurrentCell(int curCell, Lane currentLane) {
        decoratedVehicle.setCurrentCell(curCell,currentLane);
    }

    @Override
    public void setVehicleState(int vehicleState) {
        decoratedVehicle.setVehicleState(vehicleState);
    }

    @Override
    public void setVehicleBehavior(VehicleBehavior vehicleBehavior) {
        decoratedVehicle.setVehicleBehavior(vehicleBehavior);
    }

    @Override
    public boolean moveVehicle(int step) {
        return decoratedVehicle.moveVehicle(step);
    }

    @Override
    public void readTrafficLight() throws Exception {
        decoratedVehicle.readTrafficLight();
    }

    @Override
    public ArrayList<Lane> getLaneOptions() throws Exception {
        return decoratedVehicle.getLaneOptions();
    }

    @Override
    public void vehicleTurn() throws Exception {
        decoratedVehicle.vehicleTurn();
    }
}