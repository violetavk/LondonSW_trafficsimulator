package londonsw.model.simulation;

/**
 * This is the class that keeps time for our simulation
 *
 * This uses RxJava's Observer and Subscriber to pass events to the classes that want messages from the
 * ticker. This also uses RxJavaFx to make sure that the GUI events are running on the JavaFX thread scheduler.
 */

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.JavaFxScheduler;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Ticker {

    private static ArrayList<Subscriber<Long>> subscribers = new ArrayList<Subscriber<Long>>();;

    private static long TICK_INTERVAL = 1000;

    private static Ticker instance;

    private static Observable<Long> tickerObservable = Observable.interval(TICK_INTERVAL, TimeUnit.MILLISECONDS);

    private static PublishSubject stop = PublishSubject.create();


    protected Ticker() { }

    /**
     * Singleton of the Ticker class, prevents having more than 1 ticker in the system. Creates a new
     * Ticker if one does not yet exist, otherwise gives the instance
     * @return
     */
    public static Ticker getInstance() {
        if(instance == null) {
            instance = new Ticker();
        }
        return instance;
    }

    /**
     * Adds a subscriber to the ticker. Any class that extends Subscriber can subscribe to the ticker. This
     * also adds the subscriber to an arraylist of subscribers just to keep track of them all.
     * @param sub the new subscriber to the ticker
     */
    public static void subscribe(Subscriber<Long> sub) {
        tickerObservable.takeUntil(stop).observeOn(JavaFxScheduler.getInstance()).subscribe(sub);
        subscribers.add(sub);
    }

    /**
     * Get the list of all subscribers of this ticker, which can include Vehicles and TrafficLights
     * @return ArrayList of all subscribers of the ticker
     */
    public static ArrayList<Subscriber<Long>> getSubscribers() {
        return subscribers;
    }

    /**
     * Get the length of the current tick interval
     * @return length of the current tick interval
     */
    public static long getTickInterval() { return TICK_INTERVAL; }

    /**
     * Change the length of a tick interval. //TODO currently does not work
     * @param interval length of new interval
     */
    public static void setTickInterval(long interval) { TICK_INTERVAL = interval; }

    /**
     * This method is a no-op. The ticker starts automatically as soon as something subscribes to it.
     */
    public void start() {    }

    /**
     * Ends the ticker. All subscribers must unsubscribe and a "stop" call is explicitly called, just in case.
     */
    public void end() {
        for(Subscriber s : subscribers)
            s.unsubscribe();
        stop.onNext(null);
    }

}
