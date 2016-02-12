package londonsw.model.simulation.components;

/**
 * Created by yakubu on 10/02/2016.
        */
public class Coordinate {

    public Coordinate(int x, int y) throws NotACoordinateException {
        if(x<0 || y<0)
        {
            throw new NotACoordinateException("X and Y must be positive");
        }

        this.x=x;
        this.y=y;
    }

    private int x, y;

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
