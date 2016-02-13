package londonsw.model.simulation.components;

import londonsw.model.simulation.TickerInterval;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


/**
 * Created by yakubu on 12/02/2016.
 */
public class TrafficLightTest {

    @Test
    public void testGetColor() throws Exception {

    }

    @Test
    public void testSetColor() throws Exception {

    }

    @Test
    public void testGetDuration() throws Exception {

    }

    @Test
    public void testSetDuration() throws Exception {

    }

    @Test
    public void testToggleLight() throws Exception {

        Color initialColor = Color.RED;

        TrafficLight t = new TrafficLight(initialColor, new TickerInterval());

        Color finalColor = Color.GREEN;

        assertEquals(finalColor,t.toggleLight());
    }
}