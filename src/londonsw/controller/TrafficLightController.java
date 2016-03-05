package londonsw.controller;

import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls all the traffic lights. This gets notified when the traffic light changes in the model, and it
 * does the necessary work to change the GUI to display the new model.
 */
public class TrafficLightController {

    private static Map<TrafficLight, TrafficLightDecorator> trafficLights = new HashMap<TrafficLight, TrafficLightDecorator>();;
    private static ArrayList<TrafficLight> allLights = new ArrayList<TrafficLight>();

    /**
     * Called by the TrafficLight (in the model) when the color changes. It tells the corresponding
     * TrafficLightDecorator to change its color in the GUI.
     * @param colour the new color to be
     * @param tl a TrafficLight that had its color changed
     */
    public static void colourChanged(LightColour colour, TrafficLight tl) {
        if(trafficLights.get(tl) == null) {
            System.out.println("No TrafficLightDecorator associated with this traffic light");
        }
        trafficLights.get(tl).setGUIColour(colour);
    }

    /**
     * Register a TrafficLight (in the model) to a TrafficLightDecorator (in the GUI), and add to an
     * ArrayList to keep track of all lights
     * @param tl the TrafficLight instance from the model
     * @param gui the corresponding instance of the TrafficLightDecorator for that TrafficLight
     */
    public static void addTrafficLightAndDecoratorPair(TrafficLight tl, TrafficLightDecorator gui) {
        trafficLights.put(tl,gui);
        allLights.add(tl);
    }

    /**
     * If you need to get the decorator for that TrafficLight outside of this class, use this method
     * @param tl a TrafficLight that you are querying for to get its decorator
     * @return the corresponding TrafficLightDecorator for that TrafficLight
     */
    public static TrafficLightDecorator getTrafficLightGUI(TrafficLight tl) {
        return trafficLights.get(tl);
    }

    /**
     * Get a list of all the TrafficLights in the system
     * @return ArrayList of all lights
     */
    public static ArrayList<TrafficLight> getAllTrafficLights() {
        return allLights;
    }

}