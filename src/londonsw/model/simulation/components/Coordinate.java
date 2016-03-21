package londonsw.model.simulation.components;

import java.io.Serializable;

/**
 * The Coordinate class represents an (x, y) pair of integers that signify a location in the map
 * Coordinates are also used for doing calculations and translations for movement of vehicles
 * A coordinate at the top-left of a map is (0,0)
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

    /**
     * Sets the y coordinate of this instance
     * @param y int of the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the x coordinate of this instance
     * @param x int of the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the x-coordinate of this instance
     * @return int of the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this instance
     * @return int of the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if two coordinates are equal
     * @param obj coordinate to be compared with
     * @return true if the coordinates are the same, false otherwise
     */
    public boolean equals(Object obj) {
        Coordinate other = (Coordinate)obj;
        return (x == other.getX()) && (y == other.getY());
    }

    /**
     * Adds 2 coordinates and returns their sum
     * @param a first coordinate to add
     * @param b second coordinate to add
     * @return the sum of the two coordinates' x's and y's as a Coordinate instance
     */
    public static Coordinate add(Coordinate a, Coordinate b) {
        return new Coordinate(a.getX()+b.getX(), a.getY()+b.getY());
    }

    /**
     * Subtracts 2 coordinates and returns their difference
     * @param a coordinate to subtract from
     * @param b coordinate to subtract
     * @return the difference of the two coordinates' x's and y's as a Coordinate instance
     */
    public static Coordinate substract(Coordinate a, Coordinate b) {
        return new Coordinate(a.getX()-b.getX(), a.getY()-b.getY());
    }

    /**
     * Formats the coordinate for console output
     * @return the coordinate formatted as follows: (x, y)
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Adds a step to the coordinate based on map direction. i.e if the map direction is eastwards,
     * and a specified coordinate is (2,1), calling addStep method returns a new coordinate with dimensions (2,1)
     * @param mapDirection i.e directions north,south, east or west
     * @return sum, returns a new valid coordinate with a step added to it
     */
    public Coordinate addStep(MapDirection mapDirection) {
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

}
