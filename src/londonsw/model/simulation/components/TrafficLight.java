package londonsw.model.simulation.components;

import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.TickerListener;

import java.io.Serializable;

public class TrafficLight extends Ticker implements ITrafficLight, TickerListener, Serializable {


    LightColour state = LightColour.RED;
    private long duration = 2000;
    private long currentTime;

    public LightColour getState() {return state;}
    public void setState(LightColour state) {this.state = state;}

    @Override
    public void nextState() {
        switch (state) {
            case RED:
                state = LightColour.GREEN;
                break;
            case YELLOW:
                state = LightColour.RED;
                break;
            case GREEN:
                state = LightColour.RED; // changed to RED, no yellow behaviour for now
                break;
            default:
                state = LightColour.RED;
                break;
        }

    }

    @Override
    public void change(int no) {
        for (int i = 0; i < no; i++) {
            nextState();
        }

    }

    public TrafficLight() {
        this.currentTime = 0;
        Ticker.subscribe(this);

    }

    @Override
    public void setDuration(long duration) {
        this.duration = duration;

    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public void onTick(long time) {
        if(currentTime < (duration - Ticker.getTickInterval())) {
            currentTime += time;
        }
        else {

            currentTime = 0;
            nextState();
            TrafficLightController.colourChanged(state);
//            System.out.println("Color changing to " + state);

            // notify the controller that the color just changed!
        }
        System.out.println("time: " + time + "  color: " + state); // debug only
    }

}