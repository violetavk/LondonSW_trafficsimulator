package londonsw.controller;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;

import java.util.ArrayList;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {


    public static void moveVehicle(Vehicle car, CarGUIDecorator carGUI, int step) throws Exception
    {

        System.out.println(car.getCurrentCoordinate().getX() + "," + car.getCurrentCoordinate().getY() );

        car.setVehicleState(1); //TODO change to enum

        if(car.getCurrentCoordinate().equals(car.getCurrentLane().getExit()))
        {
            //car.setVehicleState(0); //TODO change to enum


            //TODO Change controller maybe

            ArrayList<Lane> lanes = car.getLaneOptions();

            //carGUI.moveVehicleGUI(step,car.getVehicleState());

            car.vehicleTurn();

            //TODO: make vehicle turn with curve in intersection!

            //carGUI.drawCar();

            //get intersection coordinates
            //get Intersection
            //Intersection.getLaneOptions

            //randomly choose where to go next

        }

        carGUI.moveVehicleGUI(step,car.getVehicleState());
        car.moveVehicle(step);

    }

    public static void moveVehicle(Vehicle car, int step)
    {
        System.out.println("Vehicle wants to move " + step + " step");
        car.moveVehicle(step);
    }
}