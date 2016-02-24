package londonsw.model.simulation.components;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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
        intersection.setSouthRoad(southRoad);
        Road testRoad  =intersection.getSouthRoad();
        assertEquals(testRoad,southRoad);
    }

    @Test
    public void testSetNorthRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        intersection.setNorthRoad(northRoad);
        Road testRoad  =intersection.getNorthRoad();
        assertEquals(testRoad,northRoad);
    }

    @Test
    public void testSetWestRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        intersection.setWestRoad(westRoad);
        Road testRoad  =intersection.getWestRoad();
        assertEquals(testRoad,westRoad);
    }

    @Test
    public void testSetEastRoad() throws Exception {
        Intersection intersection= new Intersection(new Coordinate(5,5));
        Road eastRoad = new Road(new Coordinate(6,5),new Coordinate(10,5));
        intersection.setEastRoad(eastRoad);
        Road testRoad  =intersection.getEastRoad();
        assertEquals(testRoad,eastRoad);
    }
}