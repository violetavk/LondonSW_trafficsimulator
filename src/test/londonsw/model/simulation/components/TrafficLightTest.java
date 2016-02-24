package londonsw.model.simulation.components;

import londonsw.model.simulation.Ticker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yakubu on 19/02/2016.
 */
public class TrafficLightTest {

//    static Ticker ticker;

//    @Before
//    public void setUp() {
//        ticker = Ticker.getInstance();
//    }

//    @Test
//    public void testNextState() throws Exception {
//        TrafficLight light = new TrafficLight();
//        assertEquals(LightColour.RED, light.state);
//        light.nextState();
//        assertEquals(LightColour.GREEN, light.state);
//        light.nextState();
////        assertEquals(LightColour.YELLOW, light.state);
////        light.nextState();
//        assertEquals(LightColour.RED, light.state);
//
//
//    }

    @Test
    public void testChange() throws Exception {
        TrafficLight light = new TrafficLight();
        light.change(1);
        // assertEquals(SignalColor.YELLOW, light.state);
        assertEquals(LightColour.GREEN, light.state);
    }


    /* Test with Ticker */
    public static void main(String[] args) throws Exception {
        Ticker ticker = Ticker.getInstance();
        TrafficLight tl = new TrafficLight();
        ticker.start();
        Thread.sleep(4000);
        ticker.end();
    }
}