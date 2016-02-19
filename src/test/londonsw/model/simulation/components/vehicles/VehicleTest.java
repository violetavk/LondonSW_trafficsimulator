package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.MapDirection;
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
    public void testMoveVehicle() throws Exception {
        Lane l= new Lane(new Coordinate(1,5), new Coordinate(4,5), MapDirection.EAST);
        Vehicle v= new Vehicle(1,l);
        int curCell =v.getCurrentCell();
        v.moveVehicle(1);
        assertEquals(curCell+1,v.getCurrentCell());


    }

    @Test
    public void testVehicleDirection() throws Exception {

    }
}