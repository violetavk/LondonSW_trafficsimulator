package londonsw.model.simulation.components;

import java.io.Serializable;

/**
 * The Coordinate class determines the movement direction along the map
 */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 252245795148278739L;
    private int x, y;

    /**
     *
     * @param x takes the value of the x region/axis
     * @param y takes the value of the y region
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

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

    public static Coordinate add(Coordinate a, Coordinate b) {
        return new Coordinate(a.getX()+b.getX(), a.getY()+b.getY());
    }

    public static Coordinate substract(Coordinate a, Coordinate b) {
        return new Coordinate(a.getX()-b.getX(), a.getY()-b.getY());
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Adds a step to the coordinate based on map direction. i.e if the map direction is eastwards, and a specified coordinate is (2,1), calling addStep method returns a new coordinate with dimensions (2,1)
     * @param mapDirection i.e directions north,south, east or west
     * @return sum, returns a new valid coordinate with a step added to it
     */
    public Coordinate addStep(MapDirection mapDirection)
    {
        Coordinate sum = new Coordinate(-1,-1);

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
        Coordinate rest = new Coordinate(-1,1);

        rest.setX(a.getX()- b.getX());
        rest.setY(a.getY()- b.getY());

        return rest;
    }

}
