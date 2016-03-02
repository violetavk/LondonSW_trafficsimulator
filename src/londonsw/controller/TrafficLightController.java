package londonsw.controller;

import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrafficLightController {

    private static Map<TrafficLight, TrafficLightDecorator> trafficLights;
    private static TrafficLightController instance;
    private static ArrayList<TrafficLight> allLights;

    protected TrafficLightController() {
        trafficLights = new HashMap<TrafficLight, TrafficLightDecorator>();
        allLights = new ArrayList<TrafficLight>();
    }

    public static TrafficLightController getInstance() {
        if(instance == null) {
            instance = new TrafficLightController();
        }
        return instance;
    }

    public static void colourChanged(LightColour colour, TrafficLight tl) {
        // tell the view to be the color "colour"
        System.out.println("TrafficLight " + tl +  " wants to be changed to " + colour);
        if(trafficLights.get(tl) == null) {
            System.out.println("No TrafficLightDecorator associated with this traffic light");
        }
        trafficLights.get(tl).setGUIColour(colour);
    }

    public static void addKeyValuePair(TrafficLight tl, TrafficLightDecorator gui) {
        trafficLights.put(tl,gui);
        allLights.add(tl);
    }

    public static TrafficLightDecorator getTrafficLightGUI(TrafficLight tl) {
        return trafficLights.get(tl);
    }

    public static TrafficLightDecorator createNewTrafficLightGUI(TrafficLight tl) {
        TrafficLightDecorator gui = new TrafficLightDecorator(tl);
//        addKeyValuePair(tl,gui);
        return gui;
    }

    public static ArrayList<TrafficLight> getAllTrafficLights() {
        return allLights;
    }

}