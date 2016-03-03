package londonsw.model.simulation.components;

/**
 * Created by felix on 28/02/2016.
 */
public class ResizeFactor {

    private double resizeX;
    private double resizeY;

    public ResizeFactor(double resizeX, double resizeY) {

        this.resizeX = resizeX;
        this.resizeY = resizeY;
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
