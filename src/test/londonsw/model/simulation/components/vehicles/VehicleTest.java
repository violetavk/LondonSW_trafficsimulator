package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.components.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rawanmoh on 16/02/2016.
 */
public class VehicleTest {

   /* Vehicle v;
    @Before
    public void setUpForeTesting(){
        v= new Vehicle(new Coordinate(1,2),2);
    }*/

    @Test
    public void testGetVehicleId() throws Exception {
        Vehicle v= new Vehicle(new Coordinate(1,2),2);
        assertEquals(2, v.getVehicleId()) ;
    }

    @Test
    public void testMoveVehicle() throws Exception {
        Vehicle v= new Vehicle(new Coordinate(1,2),2);


    }

    @Test
    public void testVehicleDirection() throws Exception {

    }
}