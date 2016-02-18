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

    public Ticker() {
        CURRENT_TIME = 0;
        subscribers = new ArrayList<TickerListener>();
        timer = new Timer();
        tr = TickerRunnable.getInstance();
    } // TODO would be nice to have Ticker be a Singleton class

    public static void subscribe(TickerListener tl) {
        subscribers.add(tl);
    }

    public void start() {
        timer.schedule(tr, TICK_INTERVAL, TICK_INTERVAL);
    }


    private static class TickerRunnable extends TimerTask {

        //maybe a new thread for each, each will wait(TICK_INTERVAL) and then Ticker will notifyAll() or something...
        private static TickerRunnable instance;

        public static TickerRunnable getInstance() {
            if(instance == null) {
                instance = new TickerRunnable();
            }
            return instance;
        }

        @Override
        public void run() {
            System.out.println("Timer is working... Current time: " + CURRENT_TIME); // DEBUG ONLY

            //basic version
            for(TickerListener tl : subscribers)
                tl.onTick(CURRENT_TIME);

            //TODO try to send the update to all subscribers at the same time

            CURRENT_TIME = CURRENT_TIME + TICK_INTERVAL;
        }
    }

}
