package londonsw.controller;

import javafx.scene.shape.Circle;
import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;
import londonsw.view.simulation.TrafficLightGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrafficLightController {

    private static Map<TrafficLight, TrafficLightGUI> trafficLights;
    private static TrafficLightController instance;
    private static ArrayList<TrafficLight> allLights;

    protected TrafficLightController() {
        trafficLights = new HashMap<TrafficLight, TrafficLightGUI>();
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
            System.out.println("No TrafficLightGUI associated with this traffic light");
        }
        trafficLights.get(tl).setGUIColour(colour);
    }

    public static void addKeyValuePair(TrafficLight tl, TrafficLightGUI gui) {
        trafficLights.put(tl,gui);
        allLights.add(tl);
    }

    public static TrafficLightGUI getTrafficLightGUI(TrafficLight tl) {
        return trafficLights.get(tl);
    }

    public static TrafficLightGUI createNewTrafficLightGUI(TrafficLight tl) {
        TrafficLightGUI gui = new TrafficLightGUI(tl);
//        addKeyValuePair(tl,gui);
        return gui;
    }

    public static ArrayList<TrafficLight> getAllTrafficLights() {
        return allLights;
    }

}