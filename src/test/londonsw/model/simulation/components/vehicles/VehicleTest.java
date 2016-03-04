package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.Map;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by rawanmoh on 16/02/2016.
 */
public class VehicleTest {



    Intersection intersection;
    Road northRoad,southRoad, eastRoad, westRoad;
    Lane northLane1,northLane2,southLane1,southLane2,eastLane1,eastLane2,westLane1,westLane2;


    @Before
    public void setUpForTests() throws Exception {
        // Create an intersection with four roads, each road has two lanes with different direction

        intersection= new Intersection(new Coordinate(5,5));
        northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        southRoad = new Road(new Coordinate(5,6),new Coordinate(5,10));
        eastRoad = new Road(new Coordinate(6,5),new Coordinate(9,5));
        westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        northLane1= new Lane(new Coordinate(5,4), new Coordinate(5,1),MapDirection.NORTH);
        northLane2= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.SOUTH);
        southLane1= new Lane(new Coordinate(5,10), new Coordinate(5,6),MapDirection.NORTH);
        southLane2= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.SOUTH);
        eastLane1= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.EAST);
        eastLane2= new Lane(new Coordinate(9,5), new Coordinate(6,5),MapDirection.WEST);
        westLane1= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.EAST);
        westLane2= new Lane(new Coordinate(4,5), new Coordinate(1,5),MapDirection.WEST);

        northRoad.addLane(northLane1);
        northRoad.addLane(northLane2);
        //northLane1.setIntersection(intersection);
        //northLane2.setIntersection(intersection);

        southRoad.addLane(southLane1);
        southRoad.addLane(southLane2);
      //  southLane1.setIntersection(intersection);
       // southLane2.setIntersection(intersection);

        eastRoad.addLane(eastLane1);
        eastRoad.addLane(eastLane2);
        //eastLane1.setIntersection(intersection);
        //eastLane2.setIntersection(intersection);

        westRoad.addLane(westLane1);
        westRoad.addLane(westLane2);
        //westLane1.setIntersection(intersection);
        //westLane2.setIntersection(intersection);

        intersection.setEastRoad(eastRoad);
        intersection.setNorthRoad(northRoad);
        intersection.setSouthRoad(southRoad);
        intersection.setWestRoad(westRoad);


        //northLane2.setEndIntersection(intersection);
       // northLane1.setStartIntersection(intersection);
        //southLane1.setEndIntersection(intersection);
        //southLane2.setStartIntersection(intersection);
       // eastLane1.setEndIntersection(intersection);
        //eastLane2.setStartIntersection(intersection);
       // westLane2.setEndIntersection(intersection);
        //westLane1.setStartIntersection(intersection);

    }

    @Test
    public void testMoveVehicle() throws Exception {
        Ticker ticker = Ticker.getInstance();
        Road r= new Road(new Coordinate(1,5),new Coordinate(4,5));
        Lane l= new Lane(new Coordinate(1,5), new Coordinate(4,5), MapDirection.EAST);
        Vehicle v= new Car(1,l);
        printLane(l);
        int curCell =v.getCurrentCell();
        v.moveVehicle(1);
        printLane(l);
        assertEquals(curCell+1,v.getCurrentCell());


    }

    @Test
    public void testReadTrafficLight() throws Exception {
        Ticker ticker = Ticker.getInstance();
        Vehicle car =new Car(southLane1.getLength()-1,southLane1);
        TrafficLight light = new TrafficLight();
        light.setState(LightColour.GREEN);
        intersection.setSouthTrafficLight(light);
        car.readTrafficLight();
        assertEquals(car.getVehicleState(),1);

        /*TrafficLight light = new TrafficLight();
        light.setState(LightColour.GREEN);
        Intersection intersection = new Intersection(new Coordinate(5,5));
        intersection.setSouthTrafficLight(light);
        Road r =new Road(new Coordinate(5,6), new Coordinate(5,9));
        Lane l= new Lane(new Coordinate(5,6), new Coordinate(5,9), MapDirection.NORTH,r);
        r.addLane(l);
        Vehicle v= new Car(3,l);
        //intersection.setSouthRoad(r);
        //l.setIntersection(intersection); //I think it is better
        r.setIntersection(intersection);
        v.setCurrentLane(l);
        v.readTrafficLight();
        assertEquals(v.getVehicleState(),1);*/
    }


    /**
     * This is a test for Vehicles moving with the ticker
     */
    public static void main(String[] args) throws Exception {
        Ticker ticker = Ticker.getInstance();
        ticker.start();
        Road road1= new Road(new Coordinate(0,0), new Coordinate(5, 0));
        Road road2 =new Road (new Coordinate(0,0), new Coordinate(10, 0));
        Lane lane1 = new Lane(new Coordinate(0,0), new Coordinate(5, 0), MapDirection.EAST);
        Lane lane2 = new Lane(new Coordinate(0,0), new Coordinate(10, 0), MapDirection.EAST);
        Vehicle car1 = new Car(0,lane1);
        Vehicle car2 = new Car(1,lane2);
        Vehicle car4 = new Car(5, lane2);
        Vehicle car3 = new Car(6,lane2);

        printLane(lane1);
        printLane(lane2);
        Thread.sleep(1000);
        printLane(lane1);
        printLane(lane2);
        Thread.sleep(1000);
        printLane(lane1);
        printLane(lane2);
        Vehicle car5 = new Car(0,lane1);
        Thread.sleep(1000);
        printLane(lane1);
        printLane(lane2);
        ticker.end();
    }


    private static void printLane(Lane lane) {
        for(int i = 0; i < lane.getLength(); i++) {
            if(lane.get(i) instanceof Car) {
                System.out.print("C" + ((Car) lane.get(i)).getCarId() + " ");
            }
            else {
                System.out.print("␣  ");
            }
        }
        System.out.println();
    }


    @Test
    public void testGetLaneOptions() throws Exception {
        Ticker t = Ticker.getInstance();
         Vehicle car =new Car(southLane1.getLength()-1,southLane1);
         car.getLaneOptions();
         assertEquals(car.getLaneOptions().size(),3);
    }

    @Test
    public void testGetLaneOptionsWithState() throws Exception {
        Ticker t = Ticker.getInstance();
        Vehicle car =new Car(southLane1.getLength()-1,southLane1);
        eastLane1.setState(0);

        car.getLaneOptions();
        assertEquals(car.getLaneOptions().size(),2);
    }


    @Test
    public void testVehicleTurn() throws Exception {
      

        Ticker t = Ticker.getInstance();
        Vehicle car =new Car(southLane1.getLength()-1,southLane1);
        car.getLaneOptions();
        car.vehicleTurn();
        Lane testLane =car.getCurrentLane();
        assertNotEquals(testLane,southLane1);
        assertNotEquals(testLane,southLane2);


    }

    public Map drawTestMap() throws Exception {
        Map map = new Map(6,5);

        intersection= new Intersection(new Coordinate(5,5));
        northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        southRoad = new Road(new Coordinate(5,6),new Coordinate(5,10));
        eastRoad = new Road(new Coordinate(6,5),new Coordinate(9,5));
        westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        northLane1= new Lane(new Coordinate(5,4), new Coordinate(5,1),MapDirection.NORTH);
        northLane2= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.SOUTH);
        southLane1= new Lane(new Coordinate(5,10), new Coordinate(5,6),MapDirection.NORTH);
        southLane2= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.SOUTH);
        eastLane1= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.EAST);
        eastLane2= new Lane(new Coordinate(9,5), new Coordinate(6,5),MapDirection.WEST);
        westLane1= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.EAST);
        westLane2= new Lane(new Coordinate(4,5), new Coordinate(1,5),MapDirection.WEST);

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


        map.addRoad(northRoad);
        map.addRoad(eastRoad);
        map.addRoad(westRoad);
        map.addRoad(southRoad);

        map.addIntersection(intersection);

        return map;
    }

    public void printMapGrid(Map map) {
        Component[][] grid = map.getGrid().getGrid();
        int width = map.getGrid().getWidth();
        int height = map.getGrid().getHeight();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Component current = grid[i][j];
                if(current instanceof Road)
                    System.out.print("R ");
                else if(current instanceof Intersection)
                    System.out.print("I ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println();
    }


}