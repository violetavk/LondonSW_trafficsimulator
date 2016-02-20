package londonsw.model.simulation.components;

import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.TickerListener;

/**
 * Created by yakubu on 19/02/2016.
 */
public class TrafficLight implements TickerListener {

    LightColour state = LightColour.RED;
    private long duration = 2000;
    private long currentTime;

    public TrafficLight() {
        this.currentTime = 0;
        Ticker.subscribe(this);
    }

    /**
     *
     */
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

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    /**
     * @param no
     */
    public void change(int no) {
        for (int i = 0; i < no; i++) {
            nextState();
        }
    }

    @Override
    public void onTick(long time) {
        if(currentTime < (duration - Ticker.getTickInterval())) {
            currentTime += time;
        }
        else {
            currentTime = 0;
            nextState();
        }
        System.out.println("time: " + time + "  color: " + state); // debug only
    }
}

