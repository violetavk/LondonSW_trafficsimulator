package londonsw.view.simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;

import java.io.Serializable;

/**
 * Traffic Light GUI Logic is implemented here. One instance of this class is associated with exactly one instance
 * of a TrafficLight.
 */
public class TrafficLightDecorator implements Serializable {

    private static final long serialVersionUID = 8123065437897754089L;
    private TrafficLight thisLight;
    private Circle circle;

    /**
     * Creates a new instance of the TrafficLightDecorator for the GUI.
     * @param thisLight the instance of a TrafficLight that this decorator is for
     */
    public TrafficLightDecorator(TrafficLight thisLight) {
        this.thisLight = thisLight;
        circle = new Circle();
        TrafficLightController.getInstance().addTrafficLightAndDecoratorPair(thisLight, this);
    }

    /**
     * This gets called by the controller to set the color of the circle of the traffic light gui
     * @param colour
     */
    public void setGUIColour(LightColour colour) {
        switch (colour) {
            case RED:
                circle.setFill(Color.RED);
                break;

            case GREEN:
                circle.setFill(Color.GREEN);
                break;
        }
    }

    /**
     * Returns the circle associated with this decorator
     * @return circle for this traffic light
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Hides the circle representing the traffic light from the GUI. This is called when enabling and disabling
     * traffic lights.
     * @param hide true will hide the circle, false will display the circle
     */
    public void hideCircle(boolean hide) {
        if(hide)
            circle.setVisible(false);
        else
            circle.setVisible(true);
    }

    /**
     * Draws the circle for the GUI
     * @param x the x-position in its pane
     * @param y the y-position in its pane
     * @param r the radius of the circle
     * @return a new Circle with those properties
     */
    public Circle drawLight(double x, double y, double r){
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);
        circle.setFill(thisLight.getState() == LightColour.RED? Color.RED : Color.GREEN);
        return circle;
    }

}
