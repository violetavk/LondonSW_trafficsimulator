package londonsw.model.simulation.components;

import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class for Lane
 */
public class LaneTest {

    Lane x;
    Coordinate a;
    Coordinate b;

    @Before
    public void setUpForTests() throws Exception {
        a = new Coordinate(0, 1);
        b = new Coordinate(2, 1);
        x = new Lane(a, b, MapDirection.EAST);
    }

    @Test
    public void testGetLaneLengthValid() {
        int length = x.getLength();
        assertEquals(length,3);
    }

    @Test(expected = NotALaneException.class)
    public void testGetLaneLengthInvalid() throws Exception {
        Lane l = new Lane(new Coordinate(2,3),new Coordinate(3,4),MapDirection.ERROR);
    }

    @Test
    public void testGetEntryCoordinate() throws Exception {
        Coordinate entry = x.getEntry();
        assertNotNull(entry);
        assertEquals(entry, new Coordinate(0,1));
    }

    @Test
    public void testGetExitCoordinate() throws Exception {
        Coordinate exit = x.getExit();
        assertNotNull(exit);
        assertEquals(exit, new Coordinate(2,1));
    }

    @Test
    public void testGetLength() {
        int length = x.getLength();
        assertEquals(length,3);

    }

    @Test
    public void testIsCellEmpty() {
        boolean empty = x.isCellEmpty(2);
        assertTrue(empty);
    }

    @Test
    public void testIsCellNotEmpty() throws Exception {
        Vehicle v = new Car(new Coordinate(0,2), 5);
        x.setCell(v,2);
        boolean empty = x.isCellEmpty(2);
        assertFalse(empty);
    }
}