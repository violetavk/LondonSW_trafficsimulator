package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInterval;

import java.awt.*;

/**
 * This class is the basis of a traffic light.
 * It will have 2 colors: red, green
 * It will toggle colors when its timer (based on the class Ticker runs out) and the timer will reset
 */
//add states to traffic light class

public class TrafficLight {

    private Color color;
    private TickerInterval duration;

    public TrafficLight(Color color, TickerInterval duration) {
        this.color = color;
        this.duration = duration;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TickerInterval getDuration() {
        return duration;
    }

    public void setDuration(TickerInterval duration) {
        this.duration = duration;
    }

    public Color toggleLight()
    {
        return !this.color.equals(Color.RED) ? Color.RED : Color.GREEN;
    }
}