package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInterval;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

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

        TrafficLight t = new TrafficLight(Color.GREEN,new TickerInterval());
        i.setNorthTrafficLight(t);

        TrafficLight output = i.getNorthTrafficLight();

        assertNotNull(output);
        assertEquals(output,t);

    }


    @Test
    public void testGetLaneOptions() throws Exception {
        Lane sLane=new Lane(new Coordinate(4,3),new Coordinate(4,5),MapDirection.SOUTH);
        Lane eLane=new Lane(new Coordinate(5,2),new Coordinate(9,2),MapDirection.EAST);
        Intersection i=new Intersection(new Coordinate(4,2));
        i.setSouthLane(sLane);
        i.setEastLane(eLane);
        ArrayList test = i.getLaneOptions();

        assertEquals(2,i.getLaneOptions().size());
    }
}