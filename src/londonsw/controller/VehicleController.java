package londonsw.controller;

import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.VehicleGUIDecorator;

import java.util.ArrayList;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {

    public static void moveVehicle(Vehicle car, int step) {
        System.out.println("Vehicle wants to move " + step + " step");
        car.moveVehicle(step);
    }

    public static void moveVehicle(VehicleGUIDecorator vehicleGUIDecorator, int step) throws Exception {


        if (vehicleGUIDecorator.getCurrentCoordinate().equals(vehicleGUIDecorator.getCurrentLane().getExit())) {

            ArrayList<Lane> lanes = vehicleGUIDecorator.getLaneOptions();

            vehicleGUIDecorator.setVehicleState(2); //move vehicle to intersection

            vehicleGUIDecorator.setPreviousLane(vehicleGUIDecorator.getCurrentLane());

            vehicleGUIDecorator.vehicleTurn();
        }
        else {

            if (vehicleGUIDecorator.getVehicleState() != 0) {
                vehicleGUIDecorator.moveVehicle(step);
            }
        }

        vehicleGUIDecorator.moveVehicleGUI(step, vehicleGUIDecorator.getVehicleState());

    }
}