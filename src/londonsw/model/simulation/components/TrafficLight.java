package londonsw.model.simulation.components;

import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.TickerListener;

import java.io.Serializable;

public class TrafficLight extends Ticker implements TickerListener, Serializable {


    private static final long serialVersionUID = 1299747948664926447L;
    LightColour state = LightColour.RED;
    private long duration = 3000;
    private long currentTime;

    public TrafficLight() {
        this.currentTime = 0;
        Ticker.subscribe(this);
    }

    public LightColour getState() {return state;}

    public void setState(LightColour state) {this.state = state;}


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

    public void change(int no) {
        for (int i = 0; i < no; i++) {
            nextState();
        }

    }

    public void setDuration(long duration) {
        this.duration = duration;

    }

    public long getDuration() {
        return duration;
    }

    @Override
    public void onTick(long time) {
        if(currentTime < (duration - Ticker.getTickInterval())) {
            currentTime += Ticker.getTickInterval();
        }
        else {

            currentTime = 0;
            nextState();
            TrafficLightController.colourChanged(state, this);
        }
        System.out.println("time: " + time + "  color: " + state); // debug only
    }

}