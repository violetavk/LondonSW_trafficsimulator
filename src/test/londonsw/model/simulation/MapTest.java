package londonsw.model.simulation;

import londonsw.model.simulation.components.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by felix on 19/02/2016.
 */
public class MapTest {

    @Test
    public void testSaveMap() throws Exception {
        Map m = new Map(4,4);
        Road R1 = new Road(new Coordinate(0,0), new Coordinate(2,0));
        Lane L1 = new Lane(new Coordinate(0,0), new Coordinate(2,0), MapDirection.EAST);
        Lane L2 = new Lane(new Coordinate(0,0), new Coordinate(2,0), MapDirection.WEST);

        R1.addLane(L1);
        R1.addLane(L2);

        Intersection I1 = new Intersection(new Coordinate(3,0));

//        I1.setEastLane(L1); //setEastRoad
//        I1.setWestLane(L2); //setWestRoad


    }
}