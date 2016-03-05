package londonsw.controller;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;
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

    public static void moveVehicle(Vehicle car, CarGUIDecorator carGUI, int step) throws Exception {

        car.setVehicleState(1); //TODO change to enum

        if (car.getCurrentCoordinate().equals(car.getCurrentLane().getExit())) {
            //car.setVehicleState(0); //TODO change to enum


            //TODO Change controller maybe

            ArrayList<Lane> lanes = car.getLaneOptions();

            //carGUI.moveVehicleGUI(step,car.getVehicleState());

            car.setPreviousLane(car.currentLane);

            car.setVehicleState(3);//in intersection turning
            carGUI.moveVehicleGUI(step, car.getVehicleState());
            car.vehicleTurn();


            //TODO: make vehicle turn with curve in intersection!


            //carGUI.drawCar();

            //get intersection coordinates
            //get Intersection
            //Intersection.getLaneOptions

            //randomly choose where to go next

        } else {
            car.moveVehicle(step);
        }

        carGUI.moveVehicleGUI(step, car.getVehicleState());

    }

    public static void moveVehicle(Vehicle car, int step) {
        System.out.println("Vehicle wants to move " + step + " step");
        car.moveVehicle(step);
    }

    public static void moveVehicle(VehicleGUIDecorator vehicleGUIDecorator, int step) throws Exception {


        vehicleGUIDecorator.setVehicleState(1); //TODO change to enum

        if (vehicleGUIDecorator.getCurrentCoordinate().equals(vehicleGUIDecorator.getCurrentLane().getExit())) {
            //vehicleGUIDecorator.setVehicleState(0); //TODO change to enum

            //TODO Change controller maybe

            ArrayList<Lane> lanes = vehicleGUIDecorator.getLaneOptions();

            //carGUI.moveVehicleGUI(step,car.getVehicleState());

            //move to coordinate

            //vehicleGUIDecorator.setVehicleState(3);

            vehicleGUIDecorator.setPreviousLane(vehicleGUIDecorator.getCurrentLane());
            vehicleGUIDecorator.moveVehicleGUI(1, vehicleGUIDecorator.getVehicleState());
            vehicleGUIDecorator.vehicleTurn();


            //TODO: make vehicle turn with curve in intersection!


        } else {
            vehicleGUIDecorator.moveVehicle(step);
        }

        vehicleGUIDecorator.moveVehicleGUI(step, vehicleGUIDecorator.getVehicleState());

    }
}