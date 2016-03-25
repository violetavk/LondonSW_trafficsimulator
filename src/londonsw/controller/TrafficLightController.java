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

    private Map<TrafficLight, TrafficLightDecorator> trafficLights;
    private ArrayList<TrafficLight> allLights;
    private long DURATION;
    private boolean lightsEnabled;

    private static TrafficLightController instance;

    /**
     * Creates a new instance of a TrafficLightController. It is set to protected so that the creation of this
     * controller is controlled. This class follows the singleton pattern, so there can be at most one instance
     * of this class in the system.
     */
    protected TrafficLightController() {
        trafficLights = new HashMap<>();
        allLights = new ArrayList<>();
        DURATION = 3000;
        lightsEnabled = true;
    }

    /**
     * Gives an instance of this class. This is what needs to be called if an instance is needed. If there is
     * not an instance created, it creates a new one, otherwise it returns an existing instance.
     * @return
     */
    public static TrafficLightController getInstance() {
        if(instance == null) {
            instance = new TrafficLightController();
        }
        return instance;
    }

    /**
     * Gets whether the traffic lights are enabled or not
     * @return true if all traffic lights are working and enabled, false otherwise
     */
    public boolean areLightsEnabled() {
        return lightsEnabled;
    }

    /**
     * Sets the lights boolean to true. Useful if re-loading a map and wanting to go back to default state.
     */
    public void setLightsToEnabled() {
        lightsEnabled = true;
    }

    /**
     * Disables or enables all traffic lights in the system. If traffic lights are disabled, cars will
     * move freely without stopping anywhere. If enabled, they listen to the traffic lights.
     * @param disable true disables the lights, false enables the lights
     */
    public void disableLights(boolean disable) {
        if(disable) {
            lightsEnabled = false;
            for(TrafficLight t : allLights) {
                TrafficLightDecorator decorator = trafficLights.get(t);
                if(decorator != null)
                    decorator.hideCircle(true);
            }
        }
        else {
            lightsEnabled = true;
            for(TrafficLight t : allLights) {
                TrafficLightDecorator decorator = trafficLights.get(t);
                if(decorator != null)
                    decorator.hideCircle(false);
            }
        }
    }

    /**
     * Creates a new TrafficLightDecorator for the given TrafficLight
     * @param tl the TrafficLight for which to create the decorator
     * @return the newly created decorated for that TrafficLight
     */
    public TrafficLightDecorator createNewDecorator(TrafficLight tl) {
        return new TrafficLightDecorator(tl);
    }

    /**
     * Called by the TrafficLight (in the model) when the color changes. It tells the corresponding
     * TrafficLightDecorator to change its color in the GUI.
     * @param colour the new color to be
     * @param tl a TrafficLight that had its color changed
     */
    public void colourChanged(LightColour colour, TrafficLight tl) {
        if(trafficLights.get(tl) == null) {
            return;
        }
        trafficLights.get(tl).setGUIColour(colour);
    }

    /**
     * Register a TrafficLight (in the model) to a TrafficLightDecorator (in the GUI), and add to an
     * ArrayList to keep track of all lights
     * @param tl the TrafficLight instance from the model
     * @param gui the corresponding instance of the TrafficLightDecorator for that TrafficLight
     */
    public void addTrafficLightAndDecoratorPair(TrafficLight tl, TrafficLightDecorator gui) {
        trafficLights.put(tl,gui);
        allLights.add(tl);
    }

    /**
     * If you need to get the decorator for that TrafficLight outside of this class, use this method
     * @param tl a TrafficLight that you are querying for to get its decorator
     * @return the corresponding TrafficLightDecorator for that TrafficLight
     */
    public TrafficLightDecorator getTrafficLightGUI(TrafficLight tl) {
        return trafficLights.get(tl);
    }

    /**
     * Get a list of all the TrafficLights in the system
     * @return ArrayList of all lights
     */
    public ArrayList<TrafficLight> getAllTrafficLights() {
        return allLights;
    }

    /**
     * Gets the duration length for traffic lights. Traffic lights call this method to know how long to be a certain color.
     * @return length of duration in millis
     */
    public long getDurationLength() {
        return DURATION;
    }

    /**
     * Sets the duration length for traffic lights
     * @param duration the duration for the traffic lights in millis
     */
    public void setDurationLength(long duration) {
        this.DURATION = duration;
    }

    /**
     * Set the duration of all the traffic lights in the map
     * @param duration the duration of the traffic light in milliseconds
     */
    public void setTrafficLightDuration(long duration) {
        for(TrafficLight t : allLights) {
            t.setDuration(duration);
        }
    }

    /**
     * Gets the HashMap of all TrafficLight,TrafficLightDecorator pairs
     * @return a Map of all TrafficLight, TrafficLightDecorator pairs
     */
    public Map getTrafficLightsMap() {
        return trafficLights;
    }

}