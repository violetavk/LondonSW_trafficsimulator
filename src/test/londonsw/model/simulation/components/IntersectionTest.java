package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInverval;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by felix on 12/02/2016.
 */
public class IntersectionTest {

    Intersection i;

    @Before
    public void setupBeforeTest() throws Exception
    {
        i = new Intersection(new Coordinate(0,0));
    }

    @Test
    public void testGetNorthTrafficLight() throws Exception {

        TrafficLight t = new TrafficLight(Color.GREEN,new TickerInverval());
        i.setNorthTrafficLight(t);

        TrafficLight output = i.getNorthTrafficLight();

        assertNotNull(output);
        assertEquals(output,t);

    }

    @Test
    public void testSetNorthTrafficLight() throws Exception {
        //TODO
    }

    @Test
    public void testGetSouthTrafficLight() throws Exception {
        //TODO
    }

    @Test
    public void testSetSouthTrafficLight() throws Exception {

    }

    @Test
    public void testGetEastTrafficLight() throws Exception {

    }

    @Test
    public void testSetEastTrafficLight() throws Exception {

    }

    @Test
    public void testGetWestTrafficLight() throws Exception {

    }

    @Test
    public void testSetWestTrafficLight() throws Exception {

    }

    @Test
    public void testGetNorth() throws Exception {

    }

    @Test
    public void testSetNorth() throws Exception {

    }

    @Test
    public void testGetSouth() throws Exception {

    }

    @Test
    public void testSetSouth() throws Exception {
        //TODO
    }

    @Test
    public void testGetEast() throws Exception {
        //TODO
    }

    @Test
    public void testSetEast() throws Exception {
        //TODO
    }

    @Test
    public void testGetWest() throws Exception {
        //TODO
    }

    @Test
    public void testSetWest() throws Exception {
        //TODO
    }

    @Test
    public void testGetLocation() throws Exception {
        //TODO
    }

    @Test
    public void testSetLocation() throws Exception {
        //TODO
    }
}