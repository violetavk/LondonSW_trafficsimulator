package londonsw.model.simulation.components;

import java.io.Serializable;

/**
 * Created by yakubu on 10/02/2016.
        */
public class Coordinate implements Serializable {

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
}

class NotACoordinateException extends Exception {
    public NotACoordinateException() { super(); }
    public NotACoordinateException(String msg) { super(msg); }
    public NotACoordinateException(String msg, Throwable t) { super(msg,t); }
    public NotACoordinateException(Throwable t) { super(t); }
}
