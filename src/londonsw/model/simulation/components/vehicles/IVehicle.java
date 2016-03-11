package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.VehicleBehavior;

import java.util.ArrayList;

/**
 * Created by felix on 04/03/2016.
 */
public interface IVehicle {

    Lane getPreviousLane();
    void setPreviousLane(Lane previousLane);
    void setCurrentCoordinate(Coordinate currentCoordinate);
    int getVehicleLength();
    double getVehicleSpeed();
    int getVehiclePriority();
    Lane getCurrentLane();
    int getCurrentCell();
    int getVehicleState();
    VehicleBehavior getVehicleBehavior();
    Coordinate getCurrentCoordinate();
    void setVehicleLength(int vehicleLength);
    void setVehicleSpeed(double vehicleSpeed);
    void setVehiclePriority(int vehiclePriority);
    void setCurrentLane(Lane currentLane);
    void setCurrentCell(int curCell,Lane currentLane);
    void setVehicleState(int vehicleState);
    void setVehicleBehavior(VehicleBehavior vehicleBehavior);
    boolean moveVehicle(int step);
    void readTrafficLight() throws Exception;
    ArrayList<Lane> getLaneOptions() throws Exception;
    void vehicleTurn () throws Exception;
    Coordinate getStoredCurrentCoordinate();
    void setPreviousCoordinate(Coordinate coord);
}
