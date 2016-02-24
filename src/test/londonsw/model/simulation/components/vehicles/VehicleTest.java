package londonsw.model.simulation.components.vehicles;

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

 /*  Vehicle v ;
    Lane l;
    @Before
    public void setUpForeTesting(){
        l = new Lane(new Coordinate(1,5) , new Coordinate(4,5),MapDirection.EAST);
        v= new Vehicle(1,l);
    }*/



    @Test
    public void testMoveVehicle() throws Exception {
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
        TrafficLight light = new TrafficLight();
        light.setState(LightColour.GREEN);
        Intersection intersection = new Intersection(new Coordinate(5,5));
        intersection.setSouthTrafficLight(light);
        Road r =new Road(new Coordinate(1,5), new Coordinate(4,5));
        r.setIntersection(intersection);
        Lane l= new Lane(new Coordinate(1,5), new Coordinate(4,5), MapDirection.NORTH);
        Vehicle v= new Car(3,l);
        r.setIntersection(intersection);
        l.setRoad(r);
        v.setCurrentLane(l);
        v.readTrafficLight();
        assertEquals(v.getVehicleState(),1);
    }


    /**
     * This is a test for Vehicles moving with the ticker
     */
    public static void main(String[] args) throws Exception {
        Ticker ticker = Ticker.getInstance();
        ticker.start();
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
        Intersection intersection= new Intersection(new Coordinate(5,5));

        Road northRoad = new Road(new Coordinate(5,1),new Coordinate(5,4));
        Lane northLane1= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.NORTH);
        Lane northLane2= new Lane(new Coordinate(5,1), new Coordinate(5,4),MapDirection.SOUTH);
       // northRoad.addLane(northLane1);
       // northRoad.addLane(northLane2);
        northLane1.setRoad(northRoad);
        northLane2.setRoad(northRoad);

        Road southRoad = new Road(new Coordinate(5,6),new Coordinate(5,10));
        Lane southLane1= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.NORTH);
        Lane southLane2= new Lane(new Coordinate(5,6), new Coordinate(5,10),MapDirection.SOUTH);
        //southRoad.addLane(southLane1);
        //southRoad.addLane(southLane2);
        southLane1.setRoad(southRoad);
        southLane2.setRoad(southRoad);

        Road eastRoad = new Road(new Coordinate(6,5),new Coordinate(9,5));
        Lane eastLane1= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.EAST);
        Lane eastLane2= new Lane(new Coordinate(6,5), new Coordinate(9,5),MapDirection.WEST);
        //eastRoad.addLane(eastLane1);
        //eastRoad.addLane(eastLane2);
        eastLane1.setRoad(eastRoad);
        eastLane2.setRoad(eastRoad);

        Road westRoad = new Road(new Coordinate(1,5),new Coordinate(4,5));
        Lane westLane1= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.EAST);
        Lane westLane2= new Lane(new Coordinate(1,5), new Coordinate(4,5),MapDirection.WEST);
        //westRoad.addLane(westLane1);
        //westRoad.addLane(westLane2);
        westLane1.setRoad(westRoad);
        westLane2.setRoad(westRoad);

        /*intersection.setEastRoad(eastRoad);
        intersection.setNorthRoad(northRoad);
        intersection.setSouthRoad(southRoad);
        intersection.setWestRoad(westRoad);*/
        eastRoad.setIntersection(intersection);
        westRoad.setIntersection(intersection);
        northRoad.setIntersection(intersection);
        southRoad.setIntersection(intersection);

       // Vehicle car =new Car(southLane1.getLength()-1,southLane1);
        //car.getLaneOptions();
        //assertEquals(car.getLaneOptions().size(),0);// has to be equal 3 TODO
    }

    @Test
    public void testVehicleTurn() throws Exception {

    }
}