package londonsw.model.simulation.components;

/**
 * Created by felix on 28/02/2016.
 */
public class ResizeFactor {

    private double resizeX;
    private double resizeY;
    private double intervalX;
    private double intervalY;

    public ResizeFactor(double resizeX, double resizeY) {
        this.resizeX = resizeX;
        this.resizeY = resizeY;
    }

    public double getIntervalX() {
        return intervalX;
    }

    public void setIntervalX(double intervalX) {
        this.intervalX = intervalX;
    }

    public double getIntervalY() {
        return intervalY;
    }

    public void setIntervalY(double intervalY) {
        this.intervalY = intervalY;
    }

    public double getResizeX() {
        return resizeX;
    }

    public void setResizeX(double resizeX) {
        this.resizeX = resizeX;
    }

    public double getResizeY() {
        return resizeY;
    }

    public void setResizeY(double resizeY) {
        this.resizeY = resizeY;
    }
}
