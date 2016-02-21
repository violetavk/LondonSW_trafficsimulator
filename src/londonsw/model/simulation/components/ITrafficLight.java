package londonsw.model.simulation.components;

/**
 * Interface ITrafficLight
 */
public interface ITrafficLight {

    /**
     * Methods....
     */
    public void nextState();
    public void change(int no);
    public void setDuration(long duration);
    public long getDuration();
    public void onTick(long time);
}
