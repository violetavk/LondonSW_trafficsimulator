package londonsw.model.simulation.components.vehicles;

import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Lane lane = new Lane(new Coordinate(0,0), new Coordinate(5, 0), MapDirection.EAST);
        Vehicle car1 = new Car(0,lane);
        printLane(lane);
        ticker.start();
        Thread.sleep(2000);
        printLane(lane);
        Thread.sleep(1000);
        printLane(lane);
        ticker.end();
    }

    private static void printLane(Lane lane) {
        for(int i = 0; i < lane.getLength(); i++) {
            if(lane.get(i) != null) {
                System.out.print("C ");

            }
            else {
                System.out.print("␣ ");

            }
        }
        System.out.println();
    }
 }