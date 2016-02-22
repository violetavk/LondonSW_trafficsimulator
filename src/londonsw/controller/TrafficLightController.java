package londonsw.controller;

import londonsw.model.simulation.components.LightColour;
import londonsw.view.simulation.TrafficLightAnimation;

public class TrafficLightController {

    public static void colourChanged(LightColour colour) {

        // tell the view to be the color "colour"
        System.out.println("Color wants to be changed to " + colour);
        TrafficLightAnimation.setGUIColour(colour);
    }
}
