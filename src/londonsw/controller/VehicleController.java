package londonsw.controller;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.VehicleGUIDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {

    private static Map<Vehicle,VehicleGUIDecorator> vehiclesAndDecorators = new HashMap<>();
    private static ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /**
     * Register a car to a specific CarGuiDecorator so we can retrieve it and draw the decorator for
     * for that car. This replaces the need to pass the CarGuiDecorator as a parameter. This also adds
     * the vehicle to an ArrayList, only to keep track of all cars in the system and nothing more.
     * @param vehicle an instance of a Vehicle type
     * @param decorator the CarGuiDecorator for that specific vehicle
     */
    public static void addVehicleAndDecoratorPair(Vehicle vehicle, VehicleGUIDecorator decorator) {
        vehiclesAndDecorators.put(vehicle,decorator);
        allVehicles.add(vehicle);
    }

    /**
     * Retrieve the CarGuiDecorator for the vehicle, for operations that happen outside this class
     * @param vehicle the Vehicle to retrieve the decorator for
     * @return CarGuiDecorator instance associated with that specific Vehicle
     */
    public static VehicleGUIDecorator getDecoratorForVehicle(Vehicle vehicle) {
        return vehiclesAndDecorators.get(vehicle);
    }

    /**
     * This is the method that gets called by the Vehicle (in the model) when the ticker ticks. This controller
     * handles the rest of the moving.
     * @param v the Vehicle that notified that it should move
     * @param step how far the vehicle should move
     * @throws Exception
     */
    public static void moveOnTick(Vehicle v, int step) throws Exception {
        VehicleGUIDecorator decorator = vehiclesAndDecorators.get(v);
        moveVehicle(decorator,step);
    }

    public static void moveVehicle(VehicleGUIDecorator vehicleGUIDecorator, int step) throws Exception {

        Boolean move = true;

        vehicleGUIDecorator.setPreviousLane(vehicleGUIDecorator.getCurrentLane());
        vehicleGUIDecorator.setPreviousCoordinate(vehicleGUIDecorator.getCurrentCoordinate());

        if (vehicleGUIDecorator.getCurrentCoordinate().equals(vehicleGUIDecorator.getCurrentLane().getExit())) {
            //only read when intersection is available
            vehicleGUIDecorator.readTrafficLight();

            if (vehicleGUIDecorator.getVehicleState() == 1 ) {
               // ArrayList<Lane> lanes = vehicleGUIDecorator.getLaneOptions();
                //check next lane available

                vehicleGUIDecorator.setVehicleState(2); //move vehicle to intersection
               move = vehicleGUIDecorator.vehicleTurn();
            }
        }
        else {
            if (vehicleGUIDecorator.getVehicleState() != 0) {
                move = vehicleGUIDecorator.moveVehicle(step);
            }
        }

        if (move) {
            vehicleGUIDecorator.moveVehicleGUI(step, vehicleGUIDecorator.getVehicleState());
        }
    }
}