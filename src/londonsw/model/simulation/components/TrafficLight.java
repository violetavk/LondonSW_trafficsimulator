package londonsw.model.simulation.components;

import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.Ticker;
import rx.Subscriber;

import java.io.Serializable;

/**
 * The implementation of the traffic lights in our system. This extends the RxJava class Subscriber to receive
 * messages from the Ticker.
 */
public class TrafficLight extends Subscriber<Long> implements Serializable {

    private static final long serialVersionUID = 1299747948664926447L;
    private LightColour state;
    private long duration = 6000;
    private long currentTime;

    /**
     * Default constructor, initial light color is red
     */
    public TrafficLight() {
        this.currentTime = 0;
        Ticker.subscribe(this);
        state = LightColour.RED;
    }

    /**
     * Creating a new traffic light with a new default color
     * @param colour the initial color of the traffic light
     */
    public TrafficLight(LightColour colour) {
        this.currentTime = 0;
        Ticker.subscribe(this);
        this.state = colour;
    }

    /**
     * Get the current color of the traffic light
     * @return LightColour (enum) of the current colour
     */
    public LightColour getState() {
        return state;
    }

    /**
     * Set the color of the traffic light from an external source
     * @param state LightColour (enum) of the color to be
     */
    public void setState(LightColour state) {
        this.state = state;
    }

    /**
     * Set the color of the traffic light based on the current state.
     * Current behaviour:
     * If currently red, go to green
     * If currently green, go to red
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

    /**
     * Set how long the traffic light should be a specific color
     * @param duration time (in millis) of how long the traffic light should stay its color
     */
    public void setDuration(long duration) {
        this.duration = duration;

    }

    /**
     * Get the current duration of the traffic light (how long a color lasts)
     * @return the duration (in millis)
     */
    public long getDuration() {
        return duration;
    }


    /**
     * This is for what the traffic light would do if the ticker stops. Left unimplemented on purpose
     */
    @Override
    public void onCompleted() {    }

    /**
     * This is what the traffic light would do if there is an error thrown by the ticker's observable. Left
     * unimplemented on purpose
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {   }

    /**
     * This is like the onTick method. This is what happens when the ticker ticks. The state changes after
     * a specified amount of ticks (the duration).
     * @param aLong the current time in the system
     */
    @Override
    public void onNext(Long aLong) {
        if(currentTime < (duration)) {
            currentTime += Ticker.getTickInterval();
        }
        else {

            currentTime = Ticker.getTickInterval();
            nextState();
            TrafficLightController.colourChanged(state, this);
        }
//        System.out.println("time: " + aLong + "  color: " + state); // debug only
    }
}