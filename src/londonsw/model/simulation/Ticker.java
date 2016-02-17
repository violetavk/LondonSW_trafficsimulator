package londonsw.model.simulation;

/**
 * This is the class that keeps time for our simulation
 * Time speed can be adjusted to increase/decrease the speed of our simulation
 */

/**
 * we can just use java AnimationTimer as our animation timer
 * by import javafx.animation.AnimationTimer
 */

public class Ticker {

    private static double TICK_LENGTH = 1;
    private static double currentTime;

    private static Ticker instance;

    public static Ticker getInstance() {
        if(instance == null) {
            instance = new Ticker();
            currentTime = 0;
        }
        return instance;
    }

    public static void resetTicker(){
        currentTime = 0;
    }

    public static void changeTickLength(double length) {
        TICK_LENGTH = length;
    }

    public static void notifyTimeChange() {
        // TODO must notify all things in system that the time has just incremented
        // TODO return type may change
        // TODO still need to think of the semantics of this method
    }

}
