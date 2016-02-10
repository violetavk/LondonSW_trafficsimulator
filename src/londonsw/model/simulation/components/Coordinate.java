package londonsw.model.simulation.components;

/**
 * Created by yakubu on 10/02/2016.
 */
public class Coordinate {
    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }
    int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
