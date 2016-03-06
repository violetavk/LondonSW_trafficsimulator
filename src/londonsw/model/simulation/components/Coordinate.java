package londonsw.model.simulation.components;

import java.io.Serializable;

/**
 * Created by yakubu on 10/02/2016.
        */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 252245795148278739L;
    private int x, y;

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Coordinate() {

        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int x, int y) throws NotACoordinateException {
        if(x<0 || y<0)
        {
            throw new NotACoordinateException("X and Y must be positive");
        }

        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object obj) {
        Coordinate other = (Coordinate)obj;
        return (x == other.getX()) && (y == other.getY());
    }

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

class NotACoordinateException extends Exception {
    public NotACoordinateException() { super(); }
    public NotACoordinateException(String msg) { super(msg); }
    public NotACoordinateException(String msg, Throwable t) { super(msg,t); }
    public NotACoordinateException(Throwable t) { super(t); }
}
