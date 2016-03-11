package londonsw.model.simulation.components;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testEquals() throws Exception {
        Coordinate c = new Coordinate(1,3);
        Coordinate d = new Coordinate(1,3);
        assertEquals(c,d);
    }

    @Test
    public void testAddStep() throws Exception {

        Coordinate coordinate = new Coordinate(1,1);
        Coordinate ct = new Coordinate(1,0);
        Coordinate ct2 = new Coordinate(2,1);

        Coordinate result = coordinate.addStep(MapDirection.NORTH);
        Coordinate result1 = coordinate.addStep(MapDirection.EAST);

        assertEquals(result,ct);
        assertEquals(result1,ct2);

    }
}