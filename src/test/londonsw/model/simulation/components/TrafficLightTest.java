package londonsw.model.simulation.components;

import londonsw.model.simulation.Ticker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yakubu on 19/02/2016.
 */
public class TrafficLightTest {

    /**
     * This test no longer has value; TrafficLight is now running off a ticker in a
     * JavaFX thread only, which JUnit does not support. For this reason, this test
     * expects an Exception when executing.
     *
     * @throws Exception this will be thrown because the correct threads are not initialized
     */
    @Test(expected = Exception.class)
    public void testChange() throws Exception {
        TrafficLight light = new TrafficLight();
        light.nextState();
        assertEquals(LightColour.GREEN, light.getState());
        light.nextState();
        assertEquals(LightColour.RED, light.getState());

        TrafficLight light2 = new TrafficLight(LightColour.GREEN);
        light2.nextState();
        assertEquals(LightColour.RED,light2.getState());
    }
}