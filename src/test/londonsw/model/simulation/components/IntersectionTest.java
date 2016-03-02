package londonsw.model.simulation.components;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

 /*  @Test
    public void testGetNorthTrafficLight() throws Exception {

        TrafficLight t = new TrafficLight(Color.GREEN,new TickerInterval());
        i.setNorthTrafficLight(t);

        TrafficLight output = i.getNorthTrafficLight();

        assertNotNull(output);
        assertEquals(output,t);

    }*/

/*
    @Test   //Test with south and west roads
    public void testGetRoadOptions() throws Exception {

        Road sRoad=new Road(new Coordinate(3,1),new Coordinate(3,3));
        Road wRoad=new Road(new Coordinate(0,0),new Coordinate(2,0));

        Intersection i=new Intersection(new Coordinate(3,0));
        i.setSouthRoad(sRoad);
        i.setWestRoad(wRoad);

        ArrayList<Lane> test = i.getLaneOptions();

        assertEquals(2,test.size());
    }

    @Test   //Test with north and east roads
    public void testGetRoadOptions2() throws Exception {

        Road nRoad=new Road(new Coordinate(1,1),new Coordinate(1,0));
        Road eRoad=new Road(new Coordinate(2,2),new Coordinate(3,2));

        Intersection i=new Intersection(new Coordinate(1,2));
        i.setNorthRoad(nRoad);
        i.setEastRoad(eRoad);

        ArrayList<Lane> test = i.getLaneOptions();

        assertEquals(2,test.size());
    }
*/

    @Test
    public void testGetRoadOptionsFalse() throws Exception {

        Road sRoad=new Road(new Coordinate(3,1),new Coordinate(3,3));
        Road eRoad=new Road(new Coordinate(5,2),new Coordinate(9,2));
        Intersection i=new Intersection(new Coordinate(0,3));

        try {
            i.setSouthRoad(sRoad);
            i.setEastRoad(eRoad);
        }catch (Exception ex)
        {
            new AssertionError();
        }
    }

    @Test
    public void testSetSouthRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road southRoad = new Road(new Coordinate(5,6),new Coordinate(5,10));
        Lane southLane1= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.NORTH);
        Lane southLane2= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.SOUTH);
        southRoad.addLane(southLane1);
        southRoad.addLane(southLane2);

        intersection.setSouthRoad(southRoad);
        Road testRoad  =intersection.getSouthRoad();
        Intersection testIntersection= southLane1.getEndIntersection();
        Intersection testIntersection2= southLane2.getEndIntersection();
        assertNotEquals(intersection,testIntersection2);
        assertEquals(intersection,testIntersection);
        assertEquals(testRoad,southRoad);
    }

    @Test
    public void testSetNorthRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        Lane northLane1= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.NORTH);
        Lane northLane2= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.SOUTH);
        northRoad.addLane(northLane1);
        northRoad.addLane(northLane2);

        intersection.setNorthRoad(northRoad);
        Road testRoad  =intersection.getNorthRoad();
        Intersection testIntersection= northLane2.getEndIntersection();
        assertEquals(testRoad,northRoad);
        assertEquals(intersection,testIntersection);

    }

    @Test
    public void testSetWestRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        Lane westLane1= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.WEST);
        Lane westLane2= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.EAST);
        westRoad.addLane(westLane1);
        westRoad.addLane(westLane2);

        intersection.setWestRoad(westRoad);
        Road testRoad  =intersection.getWestRoad();
        Intersection testIntersection= westLane2.getEndIntersection();

        assertEquals(testRoad,westRoad);
        assertEquals(intersection,testIntersection);
    }

    @Test
    public void testSetEastRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road eastRoad = new Road(new Coordinate(6,5),new Coordinate(10,5));
        Lane eastLane1= new Lane(new Coordinate(6,5), new Coordinate(10,5),MapDirection.WEST);
        Lane eastLane2= new Lane(new Coordinate(6,5), new Coordinate(10,5),MapDirection.EAST);
        eastRoad.addLane(eastLane1);
        eastRoad.addLane(eastLane2);

        intersection.setEastRoad(eastRoad);
        Road testRoad  =intersection.getEastRoad();

        Intersection testIntersection= eastLane1.getEndIntersection();
        assertEquals(testRoad,eastRoad);
        assertEquals(intersection,testIntersection);
    }

    @Test
    public void testGetIntersection() throws Exception {
        Road r1 = new Road(new Coordinate(1,0), new Coordinate(8,0));
        Road r2 = new Road(new Coordinate(0,1), new Coordinate(0,8));
        Road r3 = new Road(new Coordinate(9,1), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(1,9), new Coordinate(8,9));

        Lane l11 =new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST);
        Lane l12 = new Lane(r1.getEndLocation(),r1.getStartLocation(), MapDirection.WEST);
        r1.addLane(l11);
        r1.addLane(l12);

        Lane l21 =new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH);
        Lane l22=new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.NORTH);
        r2.addLane(l21);
        r2.addLane(l22);

        Lane l31 =new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH);
        Lane l32 =new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);
        r3.addLane(l31);
        r3.addLane(l32);

        Lane l41 =new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST);
        Lane l42 =new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST);
        r4.addLane(l41);
        r4.addLane(l42);

        Intersection i1 = new Intersection(new Coordinate(0,0));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);
        Intersection i2 = new Intersection(new Coordinate(9,0));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(0,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        Intersection testIntersection1,testIntersection2,testIntersection3,testIntersection4;
        Road testRoad1,testRoad2,testRoad3,testRoad4;

        testIntersection1= l11.getEndIntersection();
        testIntersection2 = l21.getEndIntersection();
        testIntersection3 = l31.getEndIntersection();
        testIntersection4 = l41.getEndIntersection();

        testRoad1 = l12.getRoad();
        testRoad2=l22.getRoad();
        testRoad3=l32.getRoad();
        testRoad4=l42.getRoad();

        assertEquals(testRoad1,r1);
        assertEquals(testRoad2,r2);
        assertEquals(testRoad3,r3);
        assertEquals(testRoad4,r4);

        assertEquals(testIntersection1,i2);
        assertEquals(testIntersection2,i3);
        assertEquals(testIntersection3,i2);
        assertEquals(testIntersection4,i3);


    }





}