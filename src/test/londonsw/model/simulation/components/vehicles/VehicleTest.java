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
 }