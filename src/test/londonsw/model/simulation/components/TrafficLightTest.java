package londonsw.model.simulation.components;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yakubu on 19/02/2016.
 */
public class TrafficLightTest {

    @Test
    public void testNextState() throws Exception {
        TrafficLight light = new TrafficLight();
        assertEquals(LightColour.RED, light.state);
        light.nextState();
        assertEquals(LightColour.GREEN, light.state);
        light.nextState();
        assertEquals(LightColour.YELLOW, light.state);
        light.nextState();
        assertEquals(LightColour.RED, light.state);


    }

    @Test
    public void testChange() throws Exception {
        TrafficLight light = new TrafficLight();
        light.change(1);
        // assertEquals(SignalColor.YELLOW, light.state);
        assertEquals(LightColour.GREEN, light.state);


    }
}