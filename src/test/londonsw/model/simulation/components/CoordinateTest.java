package londonsw.model.simulation.components;

import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;

import static org.junit.Assert.*;

/**
 * Created by felix on 12/02/2016.
 */
public class CoordinateTest {

    @Test
    public void testGetX() throws Exception {
        Coordinate c = new Coordinate(1,2);
        assertEquals(1, c.getX());
    }

    @Test
    public void testGetY() throws Exception {
        Coordinate c = new Coordinate(1,2);
        assertEquals(2, c.getY());
    }

    @Test(expected = NotACoordinateException.class)
    public void testInvalidCoordinate() throws Exception {
        Coordinate c = new Coordinate(-1,3);
    }

    @Test
    public void testEquals() throws Exception {
        Coordinate c = new Coordinate(1,3);
        Coordinate d = new Coordinate(1,3);
        assertEquals(c,d);
    }
}