package londonsw.model.simulation.components;

import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for Lane
 */
public class LaneTest {

    Lane x;
    Road r;
    Coordinate a;
    Coordinate b;

    Map map = new Map(30,30);
    Intersection intersection;
    Road northRoad,southRoad, eastRoad, westRoad;
    Lane northLane1,northLane2,southLane1,southLane2,eastLane1,eastLane2,westLane1,westLane2;


    @Before
    public void setUpForTests() throws Exception {
        a = new Coordinate(0, 1);
        b = new Coordinate(2, 1);
        r= new Road(a,b) ;
        x = new Lane(a, b, MapDirection.EAST);

        // Create an intersection with four roads, each road has two lanes with different direction
        intersection= new Intersection(new Coordinate(5,5));
        northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        southRoad = new Road(new Coordinate(5,6),new Coordinate(5,10));
        eastRoad = new Road(new Coordinate(6,5),new Coordinate(9,5));
        westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        northLane1= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.NORTH);
        northLane2= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.SOUTH);
        southLane1= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.NORTH);
        southLane2= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.SOUTH);
        eastLane1= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.EAST);
        eastLane2= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.WEST);
        westLane1= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.EAST);
        westLane2= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.WEST);

        northRoad.addLane(northLane1);
        northRoad.addLane(northLane2);

        southRoad.addLane(southLane1);
        southRoad.addLane(southLane2);

        eastRoad.addLane(eastLane1);
        eastRoad.addLane(eastLane2);

        westRoad.addLane(westLane1);
        westRoad.addLane(westLane2);

        intersection.setEastRoad(eastRoad);
        intersection.setNorthRoad(northRoad);
        intersection.setSouthRoad(southRoad);
        intersection.setWestRoad(westRoad);

        map.addIntersection(intersection);
        map.addRoad(northRoad);
        map.addRoad(southRoad);
        map.addRoad(eastRoad);
        map.addRoad(westRoad);

        northLane2.setEndIntersection(intersection);
        southLane1.setEndIntersection(intersection);
        eastLane2.setEndIntersection(intersection);
        westLane1.setEndIntersection(intersection);



    }

    @Test
    public void testGetLaneLengthValid() {
        int length = x.getLength();
        assertEquals(length,3);
    }

    @Test(expected = NotALaneException.class)
    public void testGetLaneLengthInvalid() throws Exception {
        Lane l = new Lane(new Coordinate(2,3),new Coordinate(3,4),MapDirection.ERROR);
    }

    @Test
    public void testGetEntryCoordinate() throws Exception {
        Coordinate entry = x.getEntry();
        assertNotNull(entry);
        assertEquals(entry, new Coordinate(0,1));
    }

    @Test
    public void testGetExitCoordinate() throws Exception {
        Coordinate exit = x.getExit();
        assertNotNull(exit);
        assertEquals(exit, new Coordinate(2,1));
    }

    @Test
    public void testGetLength() {
        int length = x.getLength();
        assertEquals(length,3);

    }

    @Test
    public void testIsCellEmpty() {
        boolean empty = x.isCellEmpty(2);
        assertTrue(empty);
    }

    @Test
    public void testIsCellNotEmpty() throws Exception {
        Lane l= new Lane (new Coordinate(0,0),new Coordinate(0,5),MapDirection.EAST);
        Vehicle v = new Car(5,l);

        x.setCell(v,2);
        boolean empty = x.isCellEmpty(2);
        assertFalse(empty);
    }
   /* @Test
    public void testSetIntersection() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Lane northLane= new Lane(new Coordinate(6,5), new Coordinate(10,5),MapDirection.WEST,r);
        northLane.setIntersection(intersection);
        Intersection testIntersection =northLane.getIntersection();
        assertEquals(intersection,testIntersection);
    }*/


    @Test
    public void testGetRoad() throws Exception {

        Road testRoad;
        testRoad= northLane1.getRoad();
        assertEquals(northRoad,testRoad);

    }
/*
    @Test
    public void testGetLaneIntersection() throws Exception {

        Intersection testIntersection;
        testIntersection =northLane2.getLaneIntersection();
        assertEquals(testIntersection,intersection);

    }*/

    @Test
    public void testGetEndIntersection()throws Exception {
        Intersection testIntersection1,testIntersection2,testIntersection3,testIntersection4,testIntersection5;
        testIntersection1 =northLane2.getEndIntersection();
        testIntersection2=southLane1.getEndIntersection();
        testIntersection3=eastLane2.getEndIntersection();
        testIntersection4=westLane1.getEndIntersection();

        assertEquals(testIntersection1,intersection);
        assertEquals(testIntersection2,intersection);
        assertEquals(testIntersection3,intersection);
        assertEquals(testIntersection4,intersection);

    }

    @Test
    public void testGetIntersectionCoordinate()throws Exception {
      /*  Coordinate testCoordinate1,testCoordinate2,testCoordinate3 ;
        testCoordinate1 = northLane2.getIntersectionCoordinate();
        testCoordinate2 = southLane1.getIntersectionCoordinate();
        testCoordinate3 = westLane1.getIntersectionCoordinate();

        assertEquals(testCoordinate1,new Coordinate(5,5));
        assertEquals(testCoordinate2,new Coordinate(5,5));
        assertEquals(testCoordinate3,new Coordinate(5,5));*/

    }
}