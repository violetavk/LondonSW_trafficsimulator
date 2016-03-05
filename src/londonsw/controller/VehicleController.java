package londonsw.controller;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;
import londonsw.view.simulation.VehicleGUIDecorator;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {


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


        if (vehicleGUIDecorator.getCurrentCoordinate().equals(vehicleGUIDecorator.getCurrentLane().getExit())) {


            //vehicleGUIDecorator.setVehicleState(0); //TODO change to enum

            //TODO Change controller maybe

            ArrayList<Lane> lanes = vehicleGUIDecorator.getLaneOptions();

            //carGUI.moveVehicleGUI(step,car.getVehicleState());

            //move to coordinate

            vehicleGUIDecorator.setVehicleState(3);

            vehicleGUIDecorator.setPreviousLane(vehicleGUIDecorator.getCurrentLane());

            //move to intersection



            //vehicleGUIDecorator.moveVehicleGUI(1, vehicleGUIDecorator.getVehicleState());

            //vehicleGUIDecorator.setVehicleState(3); //stop


            vehicleGUIDecorator.vehicleTurn();

            //TODO: make vehicle turn with curve in intersection!


        }
        else {

            if (vehicleGUIDecorator.getVehicleState() != 0) {
                vehicleGUIDecorator.moveVehicle(step);
            }
        }

        vehicleGUIDecorator.moveVehicleGUI(step, vehicleGUIDecorator.getVehicleState());

    }
}