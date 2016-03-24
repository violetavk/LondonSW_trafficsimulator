package londonsw.controller;


import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.VehicleGUIDecorator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the controller for all vehicle movement. It keeps track of all vehicles and their decorators. It also
 * has the methods that facilitates vehicle movement in the model and in the map.
 */
public class VehicleController {

    private static Map<Vehicle, VehicleGUIDecorator> vehiclesAndDecorators = new HashMap<>();
    private static ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /**
     * Register a car to a specific CarGuiDecorator so we can retrieve it and draw the decorator for
     * for that car. This replaces the need to pass the CarGuiDecorator as a parameter. This also adds
     * the vehicle to an ArrayList, only to keep track of all cars in the system and nothing more.
     *
     * @param vehicle   an instance of a Vehicle type
     * @param decorator the CarGuiDecorator for that specific vehicle
     */
    public static void addVehicleAndDecoratorPair(Vehicle vehicle, VehicleGUIDecorator decorator) {
        vehiclesAndDecorators.put(vehicle, decorator);
        allVehicles.add(vehicle);
    }

    /**
     * Removes the vehicle instance from the list of all vehicles and the map of all vehicle,decorator pairs
     * @param v the vehicle to remove
     */
    public static void removeFromListAndMap(Vehicle v) {
        allVehicles.remove(v);
        vehiclesAndDecorators.remove(v);
    }

    /**
     * Gets all the vehicles in the system
     * @return ArrayList with all vehicles in the system
     */
    public static ArrayList<Vehicle> getVehicleList(){
        return allVehicles;
    }

    /**
     * Given an index in the array, this removes the vehicle from existence.
     * @param index the index in the list allVehicles that the vehicle occupies
     */
    public static void removeVehicle(int index) {
        try {
            Vehicle v = allVehicles.get(index);
            VehicleGUIDecorator decorator = vehiclesAndDecorators.get(v);
            decorator.getPane().getChildren().remove(decorator.getRectangle());
            v.setVehicleState(3);
            v.unsubscribe();
            Lane currLane = v.getCurrentLane();
            int currCell = v.getCurrentCell();
            currLane.setCell(null, currCell);
            allVehicles.remove(index);
            vehiclesAndDecorators.remove(v);
            v = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the total time spent standing for all vehicles in the system.
     * @return the total time spent standing by all vehicles in the system
     */
    public static int getTotalTimeSpentStanding() {
        int sum = 0;
        for(Vehicle v : allVehicles) {
            sum += v.getTimeSpentStanding();
        }
        return sum;
    }

    /**
     * Gets the total times ticked by all vehicles in the system. This is used in the calculation of
     * average time spent standing in the system.
     * @return
     */
    public static int getTotalTimesTicked() {
        int sum = 0;
        for(Vehicle v : allVehicles) {
            sum += v.getTimesTicked();
        }
        return sum;
    }

    /**
     * Retrieve the VehicleGGUIDecorator for the vehicle, for operations that happen outside this class
     *
     * @param vehicle the Vehicle to retrieve the decorator for
     * @return CarGuiDecorator instance associated with that specific Vehicle
     */
    public static VehicleGUIDecorator getDecoratorForVehicle(Vehicle vehicle) {
        return vehiclesAndDecorators.get(vehicle);
    }

    /**
     * This is the method that gets called by the Vehicle (in the model) when the ticker ticks. This controller
     * handles the rest of the moving.
     *
     * @param v    the Vehicle that notified that it should move
     * @param step how far the vehicle should move
     * @throws Exception
     */
    public static void moveOnTick(Vehicle v, int step) throws Exception {
        VehicleGUIDecorator decorator = vehiclesAndDecorators.get(v);
        moveVehicle(decorator, step);
    }

    /**
     * Moves the vehicle in the model and in the GUI
     * @param vehicleGUIDecorator the GUI decorator for this vehicle
     * @param step how many slots to move
     * @throws Exception
     */
    public static void moveVehicle(VehicleGUIDecorator vehicleGUIDecorator, int step) throws Exception {

        int move = 0;

        vehicleGUIDecorator.setPreviousLane(vehicleGUIDecorator.getCurrentLane());
        vehicleGUIDecorator.setPreviousCoordinate(vehicleGUIDecorator.getCurrentCoordinate());

        // Vehicle is at an intersection
        if (vehicleGUIDecorator.getCurrentCoordinate().equals(vehicleGUIDecorator.getCurrentLane().getExit())) {
            //only read when intersection is available
            vehicleGUIDecorator.readTrafficLight();

            if (vehicleGUIDecorator.getVehicleState() == 1) { // if vehicle was moving
                // get next lane available to move to
                Lane l = vehicleGUIDecorator.chooseLane();

                vehicleGUIDecorator.setVehicleState(2); // set vehicle's state to "in intersection"
                move = vehicleGUIDecorator.vehicleTurn(l); // move the vehicle in the model and get a result int
            }
        } else {
            if (vehicleGUIDecorator.getVehicleState() != 0) { // if not at intersection, and wasn't stopped, move forward
                move = vehicleGUIDecorator.moveVehicle(step);
            }
        }

        if(move == 0) {
            Vehicle thisVehicle = vehicleGUIDecorator.getVehicle();
            thisVehicle.incrementTimeSpentStanding();
        }

        if (vehicleGUIDecorator.getVehicleState() == 3) { //vehicle is deleted just move to next space
            vehicleGUIDecorator.moveVehicleGUI(move, vehicleGUIDecorator.getVehicleState());
        }
        else if(move>0 && vehicleGUIDecorator.getVehicleState()!=0) { // move the vehicle in the GUI
            vehicleGUIDecorator.moveVehicleGUI(move, vehicleGUIDecorator.getVehicleState());
        }
    }
}