package londonsw.controller;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {


    public static void moveVehicle(Vehicle car, CarGUIDecorator carGUI, int step)
    {

        System.out.println(car.getCurrentCoordinate().getX() + "," + car.getCurrentCoordinate().getY() );
        carGUI.moveVehicleGUI(step,car.getVehicleState());
        car.setVehicleState(1); //TODO change to enum

        if(car.getCurrentCoordinate().equals(car.getCurrentLane().getExit()))
        {
            car.setVehicleState(0); //TODO change to enum
        }

        car.moveVehicle(step);

    }

    public static void moveVehicle(Vehicle car, int step)
    {
        System.out.println("Vehicle wants to move " + step + " step");
        car.moveVehicle(step);
    }
}