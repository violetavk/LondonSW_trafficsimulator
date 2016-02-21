package londonsw.model.simulation.components;

import londonsw.model.simulation.Ticker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ITrafficLightTest {
    TrafficLight light = new TrafficLight();
    Ticker ticker;


    @Before
    public void setUp() throws Exception {ticker = new Ticker();}

    @Test
    public void testNextState() throws Exception {
        TrafficLight light = new TrafficLight();
        assertEquals(LightColour.RED, light.state);
        light.nextState();
        assertEquals(LightColour.GREEN, light.state);
        light.nextState();
        assertEquals(LightColour.RED, light.state);

    }

    @Test
    public void testChange() throws Exception {
        TrafficLight light = new TrafficLight();
        light.change(1);
        assertEquals(LightColour.GREEN, light.state);

    }
    public static void main(String[] args) throws InterruptedException {
        Ticker ticker = new Ticker();
        TrafficLight tl = new TrafficLight();
        ticker.start();
        Thread.sleep(8000);
        ticker.end();
    }

}