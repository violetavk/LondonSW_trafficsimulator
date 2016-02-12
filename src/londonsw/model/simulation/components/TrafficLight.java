package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInverval;

import java.awt.*;

/**
 * This class is the basis of a traffic light.
 * It will have 2 colors: red, green
 * It will toggle colors when its timer (based on the class Ticker runs out) and the timer will reset
 */
public class TrafficLight {

    private Color color;
    private TickerInverval duration;


    public TrafficLight(Color color, TickerInverval duration) {
        this.color = color;
        this.duration = duration;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TickerInverval getDuration() {
        return duration;
    }

    public void setDuration(TickerInverval duration) {
        this.duration = duration;
    }

    public Color toggleLight()
    {
        if(this.color.equals(Color.RED))
        {
            this.color = Color.GREEN;
        }
        else
            this.color = Color.RED;

        return this.color;
    }
}