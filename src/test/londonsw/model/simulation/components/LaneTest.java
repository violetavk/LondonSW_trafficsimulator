package londonsw.model.simulation.components;

import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for Lane
 */
public class LaneTest {

    Lane x;
    Road r;
    Coordinate a;
    Coordinate b;

    @Before
    public void setUpForTests() throws Exception {
        a = new Coordinate(0, 1);
        b = new Coordinate(2, 1);
        r= new Road(a,b) ;
        x = new Lane(a, b, MapDirection.EAST,r);
    }

    @Test
    public void testGetLaneLengthValid() {
        int length = x.getLength();
        assertEquals(length,3);
    }

    @Test(expected = NotALaneException.class)
    public void testGetLaneLengthInvalid() throws Exception {
        Lane l = new Lane(new Coordinate(2,3),new Coordinate(3,4),MapDirection.ERROR,r);
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
        Lane l= new Lane (new Coordinate(0,0),new Coordinate(0,5),MapDirection.EAST,r);
        Vehicle v = new Car(5,l);

        x.setCell(v,2);
        boolean empty = x.isCellEmpty(2);
        assertFalse(empty);
    }
    @Test
    public void testSetIntersection() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Lane northLane= new Lane(new Coordinate(6,5), new Coordinate(10,5),MapDirection.WEST,r);
        northLane.setIntersection(intersection);
        Intersection testIntersection =northLane.getIntersection();
        assertEquals(intersection,testIntersection);
    }


    @Test
    public void testGetRoad() throws Exception {
        Road northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        Lane northLane1= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.NORTH,r);

        northRoad.addLane(northLane1);

        Road testRoad;
        testRoad= northLane1.getRoad();
        assertEquals(northRoad,testRoad);

    }
}