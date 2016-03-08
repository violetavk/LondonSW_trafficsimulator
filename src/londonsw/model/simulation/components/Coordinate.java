package londonsw.model.simulation.components;

import java.io.Serializable;

/**
 * The Coordinate class determines the movement direction along the map
 */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 252245795148278739L;
    private int x, y;
//Setting x and y
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Constructor, sets the default values of the x and y axis to 0
     */
    public Coordinate() {

        this.x = 0;
        this.y = 0;
    }

    /**
     *
     * @param x takes the value of the x region/axis
     * @param y takes the value of the y region
     * @throws NotACoordinateException validation check for negative values entered for x and y
     */
    public Coordinate(int x, int y) throws NotACoordinateException {
        if(x<0 || y<0)
        {
            throw new NotACoordinateException("X and Y must be positive");
        }

        this.x=x;
        this.y=y;
    }
//gets values of x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     *checks if two coordinates are equal
     * @param obj coordinate to be compared with
     * @return x and y values
     */
    public boolean equals(Object obj) {
        Coordinate other = (Coordinate)obj;
        return (x == other.getX()) && (y == other.getY());
    }

    /**
     * Adds a step to the coordinate based on map direction. i.e if the map direction is eastwards, and a specified coordinate is (2,1), calling addStep method returns a new coordinate with dimensions (2,1)
     * @param mapDirection i.e directions north,south, east or west
     * @return sum, returns a new valid coordinate with a step added to it
     */
    public Coordinate addStep(MapDirection mapDirection)
    {
        Coordinate sum = new Coordinate();

        switch (mapDirection) {
            case NORTH:

                sum.setY(this.getY() - 1);
                sum.setX(this.getX());

                break;
            case SOUTH:

                sum.setY(this.getY() + 1);
                sum.setX(this.getX());

                break;
            case EAST:

                sum.setX(this.getX() + 1);
                sum.setY(this.getY());

                break;
            case WEST:

                sum.setX(this.getX() - 1);
                sum.setY(this.getY());

                break;
        }

        return sum;

    }

    public static Coordinate rest(Coordinate a, Coordinate b)
    {
        Coordinate rest = new Coordinate();

        rest.setX(a.getX()- b.getX());
        rest.setY(a.getY()- b.getY());

        return rest;
    }

}

/**
 * Exception handling, when invalid or negative values for x and y are entered
 */
class NotACoordinateException extends Exception {
    public NotACoordinateException() { super(); }
    public NotACoordinateException(String msg) { super(msg); }
    public NotACoordinateException(String msg, Throwable t) { super(msg,t); }
    public NotACoordinateException(Throwable t) { super(t); }
}
