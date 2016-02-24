package londonsw.model.simulation;

/**
 * This is the class that keeps time for our simulation
 * Time speed can be adjusted to increase/decrease the speed of our simulation
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Ticker {

    private static TickerRunnable tr;

    private static ArrayList<TickerListener> subscribers;

    private static long TICK_INTERVAL = 1000;

    private static long CURRENT_TIME;

    private static Timer timer;

    private static Ticker instance;

    protected Ticker() {
        CURRENT_TIME = 0;
        subscribers = new ArrayList<TickerListener>();
        timer = new Timer(true);
        tr = TickerRunnable.getInstance();
    }

    public static Ticker getInstance() {
        if(instance == null) {
            instance = new Ticker();
        }
        return instance;
    }

    public static void subscribe(TickerListener tl) {
        subscribers.add(tl);
    }

    public static long getTickInterval() { return TICK_INTERVAL; }

    public void start() {
        timer.schedule(tr, TICK_INTERVAL, TICK_INTERVAL);
    }

    public void end() {
        tr.cancel();
        timer.cancel();
        timer.purge();
//        System.out.println("Timer cancelled");
    }


    private static class TickerRunnable extends TimerTask {

        private static TickerRunnable instance;

        public static TickerRunnable getInstance() {
            if(instance == null) {
                instance = new TickerRunnable();
            }
            return instance;
        }

        @Override
        public void run() {
//            System.out.println("\nTimer is working... Current time: " + CURRENT_TIME); // DEBUG ONLY

            //basic version
            for(TickerListener tl : subscribers)
                tl.onTick(CURRENT_TIME);

            CURRENT_TIME = CURRENT_TIME + TICK_INTERVAL;
        }
    }

}
