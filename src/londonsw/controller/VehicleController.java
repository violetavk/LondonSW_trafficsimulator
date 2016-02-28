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

    public static void moveVehicle(Vehicle car, int step) {

        // tell the view to move the vehicle one step
        System.out.println("Vehicle wants to move " + step + " step");

        CarGUIDecorator carGUI = new CarGUIDecorator( (Car) car);
        Lane lane = car.getCurrentLane();
        //Call methods to draw a vehicle

        //Call methods to move vehicle

        //I need the GridPane

        //I need the Pane



        //Scene scene = new Scene(sp);

        carGUI.moveVehicle(1);

    }
}