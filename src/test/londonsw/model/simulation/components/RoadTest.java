package londonsw.model.simulation.components;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class RoadTest {

    Road verticalRoad;
    Road horizontalRoad;

    @Before
    public void setUp() throws Exception {
        verticalRoad = new Road(new Coordinate(0,0), new Coordinate(0, 10));
        horizontalRoad = new Road(new Coordinate(0,0), new Coordinate(5,0));
    }

    @Test
    public void testGetLanes() throws Exception {
        assertEquals(verticalRoad.getLanes(),new ArrayList<Lane>());
    }

    @Test
    public void testGetLaneAtIndex() throws Exception {
        Lane l = new Lane(new Coordinate(0,0), new Coordinate(0,10), MapDirection.SOUTH);
        verticalRoad.addLane(l);
        assertNotNull(verticalRoad.getLaneAtIndex(0));
        assertEquals(verticalRoad.getLaneAtIndex(0), l);
    }

    @Test
    public void testGetStartLocation() throws Exception {
        Coordinate start = horizontalRoad.getStartLocation();
        assertNotNull(start);
        assertEquals(start,new Coordinate(0,0));
    }

    @Test
    public void testGetEndLocation() throws Exception {
        Coordinate end = verticalRoad.getEndLocation();
        assertNotNull(end);
        assertEquals(end, new Coordinate(0,10));
    }

    @Test
    public void testGetNumberLanes() throws Exception {
        assertEquals(verticalRoad.getNumberLanes(),0);
        verticalRoad.addLane(new Lane(new Coordinate(0,0), new Coordinate(0,10), MapDirection.SOUTH));
        assertEquals(verticalRoad.getNumberLanes(),1);
    }

    @Test
    public void testGetLength() throws Exception {
        int length = verticalRoad.getLength();
        assertEquals(length,11);
        assertEquals(horizontalRoad.getLength(),6);
    }

    @Test
    public void testRunsVertically() throws Exception {
        assertTrue(verticalRoad.runsVertically());
        assertFalse(horizontalRoad.runsVertically());
    }
}