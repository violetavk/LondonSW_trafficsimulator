package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInterval;
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

        TrafficLight t = new TrafficLight(Color.GREEN,new TickerInterval());
        i.setNorthTrafficLight(t);

        TrafficLight output = i.getNorthTrafficLight();

        assertNotNull(output);
        assertEquals(output,t);

    }
}