package londonsw.controller;

import javafx.scene.shape.Circle;
import londonsw.model.simulation.components.LightColour;
import londonsw.view.simulation.TrafficLightGUI;

public class TrafficLightController {

    static Circle circle;

    public static void colourChanged(LightColour colour) {

        // tell the view to be the color "colour"
        System.out.println("Color wants to be changed to " + colour);
        TrafficLightGUI.setGUIColour(colour, circle);
    }
    public void setCircle(Circle circle){
        this.circle=circle;

    }

}