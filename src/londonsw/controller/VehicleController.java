package londonsw.controller;

import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.CarGUIDecorator;

/**
 * Created by felix on 26/02/2016.
 */
public class VehicleController {

    public static void moveVehicle(Vehicle car, int step) {

        // tell the view to move the vehicle one step
        System.out.println("Vehicle wants to move " + step + " step");
        CarGUIDecorator carGUI = new CarGUIDecorator( (Car) car);



        carGUI.moveVehicle(step);

    }
}
