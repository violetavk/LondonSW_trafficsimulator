package londonsw.model.simulation.components;

import java.math.BigDecimal;

/**
 * This class represents how images will be resized to fit on the screen. Each image in our system is 100x100, but they
 * need to be scaled down in order to be displayed properly. This is used throughout the system, for drawing the grid
 * squares, vehicles, and determining the location to where vehicles will move.
 */
public class ResizeFactor {

    private double resizeX;
    private double resizeY;

    /**
     * Creates a new ResizeFactor instance. There are two parameters, although only one is used in most situations. This
     * is so that each square image maintains its aspect ratio. The typical range for resize factors is from 0 < ResizeFactor < 1.
     * @param resizeX how much to resize the x direction by
     * @param resizeY how much to resize the y direction by
     */
    public ResizeFactor(double resizeX, double resizeY) {
        this.resizeX = resizeX;
        this.resizeY = resizeY;
    }

    /**
     * Gets the resize factor of the x-direction
     * @return resize factor of x
     */
    public double getResizeX() {
        return resizeX;
    }

    /**
     * Gets the resize factor of the y-direction
     * @return resize factor of y
     */
    public double getResizeY() {
        return resizeY;
    }

    /**
     * Dynamically determines the resize factor for a map. Given a map width and height, determine the best
     * resize factor so that the map best displays on the user's display. It uses the larger of the two dimensions
     * to determine the best fitting resize factor.
     * @param mapWidth width of the map to get a resize factor for
     * @param mapHeight height of the map to get a resize factor for
     * @return a ResizeFactor that is good for the given map dimensions
     */
    public static ResizeFactor getSuggestedResizeFactor(int mapWidth, int mapHeight) {
        int larger = mapHeight > mapWidth ? mapHeight : mapWidth;

        double rf = 1.0;
        if(larger < 10 || larger < 10) {
            rf = 1.0 / (mapHeight * 0.4);
        }
        else {
            rf = 1.0 / (larger * 0.2);
        }

        BigDecimal resizeFactor = new BigDecimal(rf);
        BigDecimal rounded = resizeFactor.setScale(2,BigDecimal.ROUND_HALF_UP);
        double result = rounded.doubleValue();

        return new ResizeFactor(result, result);
    }

    public String toString() {
        return resizeX+","+resizeY;
    }
}
