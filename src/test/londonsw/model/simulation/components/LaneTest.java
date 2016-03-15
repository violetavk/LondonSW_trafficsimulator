package londonsw.model.simulation.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.Vehicle;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.view.simulation.VehicleGUIDecorator;
import org.junit.Before;
import org.junit.Test;

import static javafx.application.Application.launch;
import static org.junit.Assert.*;

/**
 * JUnit test class for Lane
 */
public class LaneTest extends Application {

    Lane x;
    Road r;
    Coordinate a;
    Coordinate b;


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
       /* Lane l= new Lane (new Coordinate(0,0),new Coordinate(0,5),MapDirection.EAST);
        Vehicle v = new Car(5,l);

        x.setCell(v,2);
        boolean empty = x.isCellEmpty(2);
        assertFalse(empty);*/
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
    @Test
    public void  testRotate() throws Exception{
        Lane lane1 = new Lane(new Coordinate(0,0),new Coordinate(0,10), MapDirection.EAST);
        Lane lane2 = new Lane(new Coordinate(0,0),new Coordinate(0,15), MapDirection.EAST);
        Lane lane3 = new Lane(new Coordinate(0,0),new Coordinate(10,0), MapDirection.WEST);

        assertEquals(false,Lane.Rotate(lane1,lane2));
        assertEquals(true,Lane.Rotate(lane1,lane3));

    }

    @Test
    public void testIsFull() throws Exception {
        //Vehicle v = new Car(5,northLane1);

    }


    public Map drawTestMap () throws Exception {

        Map map = new Map(22, 22);

        Road r01 = new Road(new Coordinate(2, 1), new Coordinate(4, 1));
        Road r02 = new Road(new Coordinate(1, 2), new Coordinate(1, 4));
        Road r03 = new Road(new Coordinate(1, 6), new Coordinate(1, 9));
        Road r04 = new Road(new Coordinate(1, 11), new Coordinate(1, 14));
        Road r05 = new Road(new Coordinate(6, 1), new Coordinate(12, 1));
        Road r06 = new Road(new Coordinate(14, 1), new Coordinate(19, 1));
        Road r07 = new Road(new Coordinate(20, 2), new Coordinate(20, 9));
        Road r08 = new Road(new Coordinate(20, 11), new Coordinate(20, 14));
        Road r09 = new Road(new Coordinate(9, 15), new Coordinate(19, 15));
        Road r10 = new Road(new Coordinate(2, 15), new Coordinate(7, 15));
        Road r11 = new Road(new Coordinate(5, 2), new Coordinate(5, 4));
        Road r12 = new Road(new Coordinate(2, 5), new Coordinate(4, 5));
        Road r13 = new Road(new Coordinate(2, 10), new Coordinate(7, 10));
        Road r14 = new Road(new Coordinate(9, 10), new Coordinate(12, 10));
        Road r15 = new Road(new Coordinate(14, 10), new Coordinate(19, 10));
        Road r16 = new Road(new Coordinate(8, 11), new Coordinate(8, 14));
        Road r17 = new Road(new Coordinate(13, 2), new Coordinate(13, 4));
        Road r18 = new Road(new Coordinate(13, 6), new Coordinate(13, 9));
        Road r19 = new Road(new Coordinate(6, 5), new Coordinate(12, 5));
        Road r20 = new Road(new Coordinate(1, 16), new Coordinate(1, 19));
        Road r21 = new Road(new Coordinate(8, 16), new Coordinate(8, 19));
        Road r22 = new Road(new Coordinate(2, 20), new Coordinate(7, 20));
        Road r23 = new Road(new Coordinate(9, 20), new Coordinate(19, 20));
        Road r24 = new Road(new Coordinate(20, 16), new Coordinate(20, 19));

        /*This is a one lane Map
        If you want two lanes map
        comment these Lines , and uncomment The two lane code
        at the bottom
         */

/*
        r01.addLane(new Lane(r01.getStartLocation(),r01.getEndLocation(),MapDirection.EAST));
        r02.addLane(new Lane (r02.getEndLocation(),r02.getStartLocation(),MapDirection.NORTH));
        r03.addLane(new Lane(r03.getEndLocation(),r03.getStartLocation(),MapDirection.NORTH));
        r04.addLane(new Lane(r04.getEndLocation(),r04.getStartLocation(),MapDirection.NORTH));
        Lane l5 = new Lane(r05.getStartLocation(),r05.getEndLocation(),MapDirection.EAST);
        //l5.setState(0);
        r05.addLane(l5);
        Lane l6 = new Lane(r06.getStartLocation(),r06.getEndLocation(),MapDirection.EAST);
        r06.addLane(l6);
        //l6.setState(0);
        r07.addLane(new Lane(r07.getStartLocation(),r07.getEndLocation(),MapDirection.SOUTH));
        r08.addLane(new Lane(r08.getStartLocation(),r08.getEndLocation(),MapDirection.SOUTH));
        r09.addLane(new Lane(r09.getEndLocation(),r09.getStartLocation(),MapDirection.WEST));
        r10.addLane(new Lane(r10.getEndLocation(),r10.getStartLocation(),MapDirection.WEST));
        r11.addLane(new Lane(r11.getEndLocation(),r11.getStartLocation(),MapDirection.NORTH));
        r12.addLane(new Lane(r12.getStartLocation(),r12.getEndLocation(),MapDirection.EAST));
        r13.addLane(new Lane(r13.getStartLocation(),r13.getEndLocation(),MapDirection.EAST));
        r14.addLane(new Lane(r14.getStartLocation(),r14.getEndLocation(),MapDirection.EAST));
        r15.addLane(new Lane(r15.getStartLocation(),r15.getEndLocation(),MapDirection.EAST));
        r16.addLane(new Lane(r16.getEndLocation(),r16.getStartLocation(),MapDirection.NORTH));
        r17.addLane(new Lane(r17.getEndLocation(),r17.getStartLocation(),MapDirection.NORTH));
        r18.addLane(new Lane(r18.getEndLocation(),r18.getStartLocation(),MapDirection.NORTH));
        r19.addLane(new Lane(r19.getStartLocation(),r19.getEndLocation(),MapDirection.EAST));
        r20.addLane(new Lane(r20.getEndLocation(),r20.getStartLocation(),MapDirection.NORTH));
        r21.addLane(new Lane(r21.getEndLocation(),r21.getStartLocation(),MapDirection.NORTH));
        r22.addLane(new Lane(r22.getEndLocation(),r22.getStartLocation(),MapDirection.WEST));
        r23.addLane(new Lane(r23.getEndLocation(),r23.getStartLocation(),MapDirection.WEST));
        r24.addLane(new Lane(r24.getStartLocation(),r24.getEndLocation(),MapDirection.SOUTH));
*/

          /*This is a two lane Map
        If you want one lanes map
        comment these Lines , and uncomment The one lane code above
         */


        r01.addLane(new Lane(r01.getStartLocation(), r01.getEndLocation(), MapDirection.EAST));
        r02.addLane(new Lane(r02.getEndLocation(), r02.getStartLocation(), MapDirection.NORTH));
        r03.addLane(new Lane(r03.getEndLocation(), r03.getStartLocation(), MapDirection.NORTH));
        r04.addLane(new Lane(r04.getEndLocation(), r04.getStartLocation(), MapDirection.NORTH));
        Lane l5 = new Lane(r05.getStartLocation(), r05.getEndLocation(), MapDirection.EAST);
        l5.setState(0);
        r05.addLane(l5);
        Lane l6 = new Lane(r06.getStartLocation(), r06.getEndLocation(), MapDirection.EAST);
        r06.addLane(l6);
        //l6.setState(0);
        r07.addLane(new Lane(r07.getEndLocation(), r07.getStartLocation(), MapDirection.NORTH));
        r08.addLane(new Lane(r08.getEndLocation(), r08.getStartLocation(), MapDirection.NORTH));
        Lane l9=new Lane(r09.getStartLocation(), r09.getEndLocation(), MapDirection.EAST);
        l9.setState(0);
        r09.addLane(l9);
        r10.addLane(new Lane(r10.getStartLocation(), r10.getEndLocation(), MapDirection.EAST));
        r11.addLane(new Lane(r11.getEndLocation(), r11.getStartLocation(), MapDirection.NORTH));
        r12.addLane(new Lane(r12.getStartLocation(), r12.getEndLocation(), MapDirection.EAST));
        r13.addLane(new Lane(r13.getStartLocation(), r13.getEndLocation(), MapDirection.EAST));
        r14.addLane(new Lane(r14.getStartLocation(), r14.getEndLocation(), MapDirection.EAST));
        r15.addLane(new Lane(r15.getStartLocation(), r15.getEndLocation(), MapDirection.EAST));
        Lane l16 =new Lane(r16.getEndLocation(), r16.getStartLocation(), MapDirection.NORTH);
        l16.setState(0);
        r16.addLane(l16);
        r17.addLane(new Lane(r17.getEndLocation(), r17.getStartLocation(), MapDirection.NORTH));
        r18.addLane(new Lane(r18.getEndLocation(), r18.getStartLocation(), MapDirection.NORTH));
        r19.addLane(new Lane(r19.getStartLocation(), r19.getEndLocation(), MapDirection.EAST));
        r20.addLane(new Lane(r20.getEndLocation(), r20.getStartLocation(), MapDirection.NORTH));
        r21.addLane(new Lane(r21.getEndLocation(), r21.getStartLocation(), MapDirection.NORTH));
        r22.addLane(new Lane(r22.getStartLocation(), r22.getEndLocation(), MapDirection.EAST));
        r23.addLane(new Lane(r23.getStartLocation(), r23.getEndLocation(), MapDirection.EAST));
        r24.addLane(new Lane(r24.getEndLocation(), r24.getStartLocation(), MapDirection.NORTH));


        r01.addLane(new Lane(r01.getEndLocation(), r01.getStartLocation(), MapDirection.WEST));
        r02.addLane(new Lane(r02.getStartLocation(), r02.getEndLocation(), MapDirection.SOUTH));
        r03.addLane(new Lane(r03.getStartLocation(), r03.getEndLocation(), MapDirection.SOUTH));
        r04.addLane(new Lane(r04.getStartLocation(), r04.getEndLocation(), MapDirection.SOUTH));
        r05.addLane(new Lane(r05.getEndLocation(), r05.getStartLocation(), MapDirection.WEST));
        r06.addLane(new Lane(r06.getEndLocation(), r06.getStartLocation(), MapDirection.WEST));
        r07.addLane(new Lane(r07.getStartLocation(), r07.getEndLocation(), MapDirection.SOUTH));
        r08.addLane(new Lane(r08.getStartLocation(), r08.getEndLocation(), MapDirection.SOUTH));
        r09.addLane(new Lane(r09.getEndLocation(), r09.getStartLocation(), MapDirection.WEST));
        r10.addLane(new Lane(r10.getEndLocation(), r10.getStartLocation(), MapDirection.WEST));
        r11.addLane(new Lane(r11.getStartLocation(), r11.getEndLocation(), MapDirection.SOUTH));
        r12.addLane(new Lane(r12.getEndLocation(), r12.getStartLocation(), MapDirection.WEST));
        r13.addLane(new Lane(r13.getEndLocation(), r13.getStartLocation(), MapDirection.WEST));
        r14.addLane(new Lane(r14.getEndLocation(), r14.getStartLocation(), MapDirection.WEST));
        r15.addLane(new Lane(r15.getEndLocation(), r15.getStartLocation(), MapDirection.WEST));
        r16.addLane(new Lane(r16.getStartLocation(), r16.getEndLocation(), MapDirection.SOUTH));
        r17.addLane(new Lane(r17.getStartLocation(), r17.getEndLocation(), MapDirection.SOUTH));
        r18.addLane(new Lane(r18.getStartLocation(), r18.getEndLocation(), MapDirection.SOUTH));
        r19.addLane(new Lane(r19.getEndLocation(), r19.getStartLocation(), MapDirection.WEST));
        r20.addLane(new Lane(r20.getStartLocation(), r20.getEndLocation(), MapDirection.SOUTH));
        r21.addLane(new Lane(r21.getStartLocation(), r21.getEndLocation(), MapDirection.SOUTH));
        r22.addLane(new Lane(r22.getEndLocation(), r22.getStartLocation(), MapDirection.WEST));
        r23.addLane(new Lane(r23.getEndLocation(), r23.getStartLocation(), MapDirection.WEST));
        r24.addLane(new Lane(r24.getStartLocation(), r24.getEndLocation(), MapDirection.SOUTH));


        Intersection i01 = new Intersection(new Coordinate(1, 1));
        i01.setEastRoad(r01);
        i01.setSouthRoad(r02);
        i01.setDefaultTrafficLightsForRoads();

        Intersection i02 = new Intersection(new Coordinate(1, 5));
        i02.setNorthRoad(r02);
        i02.setSouthRoad(r03);
        i02.setEastRoad(r12);
        i02.setDefaultTrafficLightsForRoads();

        Intersection i03 = new Intersection(new Coordinate(1, 10));
        i03.setNorthRoad(r03);
        i03.setSouthRoad(r04);
        i03.setEastRoad(r13);
        i03.setDefaultTrafficLightsForRoads();

        Intersection i04 = new Intersection(new Coordinate(1, 15));
        i04.setNorthRoad(r04);
        i04.setEastRoad(r10);
        i04.setSouthRoad(r20);
        i04.setDefaultTrafficLightsForRoads();

        Intersection i05 = new Intersection(new Coordinate(5, 1));
        i05.setWestRoad(r01);
        i05.setEastRoad(r05);
        i05.setSouthRoad(r11);
        i05.setDefaultTrafficLightsForRoads();

        Intersection i06 = new Intersection(new Coordinate(13, 1));
        i06.setWestRoad(r05);
        i06.setEastRoad(r06);
        i06.setSouthRoad(r17);
        i06.setDefaultTrafficLightsForRoads();

        Intersection i07 = new Intersection(new Coordinate(20, 1));
        i07.setWestRoad(r06);
        i07.setSouthRoad(r07);
        i07.setDefaultTrafficLightsForRoads();

        Intersection i08 = new Intersection(new Coordinate(20, 10));
        i08.setNorthRoad(r07);
        i08.setSouthRoad(r08);
        i08.setWestRoad(r15);
        i08.setDefaultTrafficLightsForRoads();

        Intersection i09 = new Intersection(new Coordinate(20, 15));
        i09.setNorthRoad(r08);
        i09.setWestRoad(r09);
        i09.setSouthRoad(r24);
        i09.setDefaultTrafficLightsForRoads();

        Intersection i10 = new Intersection(new Coordinate(8, 15));
        i10.setEastRoad(r09);
        i10.setWestRoad(r10);
        i10.setNorthRoad(r16);
        i10.setSouthRoad(r21);
        i10.setDefaultTrafficLightsForRoads();

        Intersection i11 = new Intersection(new Coordinate(5, 5));
        i11.setWestRoad(r12);
        i11.setNorthRoad(r11);
        i11.setEastRoad(r19);
        i11.setDefaultTrafficLightsForRoads();

        Intersection i12 = new Intersection(new Coordinate(8, 10));
        i12.setWestRoad(r13);
        i12.setEastRoad(r14);
        i12.setSouthRoad(r16);
        i12.setDefaultTrafficLightsForRoads();

        Intersection i13 = new Intersection(new Coordinate(13, 10));
        i13.setWestRoad(r14);
        i13.setEastRoad(r15);
        i13.setNorthRoad(r18);
        i13.setDefaultTrafficLightsForRoads();

        Intersection i14 = new Intersection((new Coordinate(13, 5)));
        i14.setNorthRoad(r17);
        i14.setSouthRoad(r18);
        i14.setWestRoad(r19);
        i14.setDefaultTrafficLightsForRoads();

        Intersection i15 = new Intersection(new Coordinate(1, 20));
        i15.setNorthRoad(r20);
        i15.setEastRoad(r22);
        i15.setDefaultTrafficLightsForRoads();

        Intersection i16 = new Intersection(new Coordinate(8, 20));
        i16.setWestRoad(r22);
        i16.setNorthRoad(r21);
        i16.setEastRoad(r23);
        i16.setDefaultTrafficLightsForRoads();

        Intersection i17 = new Intersection(new Coordinate(20, 20));
        i17.setWestRoad(r23);
        i17.setNorthRoad(r24);
        i17.setDefaultTrafficLightsForRoads();

        map.addRoad(r01);
        map.addRoad(r02);
        map.addRoad(r03);
        map.addRoad(r04);
        map.addRoad(r05);
        map.addRoad(r06);
        map.addRoad(r07);
        map.addRoad(r08);
        map.addRoad(r09);
        map.addRoad(r10);
        map.addRoad(r11);
        map.addRoad(r12);
        map.addRoad(r13);
        map.addRoad(r14);
        map.addRoad(r15);
        map.addRoad(r16);
        map.addRoad(r17);
        map.addRoad(r18);
        map.addRoad(r19);
        map.addRoad(r20);
        map.addRoad(r21);
        map.addRoad(r22);
        map.addRoad(r23);
        map.addRoad(r24);

        map.addIntersection(i01);
        map.addIntersection(i02);
        map.addIntersection(i03);
        map.addIntersection(i04);
        map.addIntersection(i05);
        map.addIntersection(i06);
        map.addIntersection(i07);
        map.addIntersection(i08);
        map.addIntersection(i09);
        map.addIntersection(i10);
        map.addIntersection(i11);
        map.addIntersection(i12);
        map.addIntersection(i13);
        map.addIntersection(i14);
        map.addIntersection(i15);
        map.addIntersection(i16);
        map.addIntersection(i17);

        return map;


    }



    public Car generateCar(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp){

        Lane L1 =map.getRandomLane();
        // Lane L1 =map.getRoads().get(0).getLanes().get(0);

        Lane L2;

        if(L1!=null && (!L1.isFull()))
            for (int a=0; a<map.getRoads().size();a++)
            {
                for(int b=0; b<map.getRoads().get(a).getNumberLanes();b++)
                {
                    // L2= map.getRoads().get(a).getLanes().get(b);
                    //L1=map.getRoads().get(8).getLanes().get(1);
                    L1=map.getRandomLane();
                    for (int i=0; i<L1.getLength();i++) {
                        if (L1.isCellEmpty(i)) {
                            Car C1 = new Car(i, L1);
                            //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
                            VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
                            vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            vehicleGUIDecorator.drawCar();
                            Pane carPane = new Pane();
                            carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
                            sp.getChildren().add(carPane);
                            vehicleGUIDecorator.setVehicleState(1);
                            System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                            return C1;

                        }
                    }
                }
            }

        return null;

    }

   
    public Car generateCarOnRoad(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp,int roadNumber,int laneNumber){


         Lane L1 =map.getRoads().get(roadNumber).getLanes().get(laneNumber);

        if(L1!=null && (!L1.isFull())) {

            for (int i = 0; i < L1.getLength(); i++) {
                if (L1.isCellEmpty(i)) {
                    Car C1 = new Car(i, L1);
                    //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
                    VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
                    vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                    vehicleGUIDecorator.drawCar();
                    Pane carPane = new Pane();
                    carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
                    sp.getChildren().add(carPane);
                    vehicleGUIDecorator.setVehicleState(1);
                    System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                    return C1;

                }
            }
        }

        return null;

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Map map =drawTestMap();

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        // double width = mapGridGUIDecorator.getWidth();
        // double height = mapGridGUIDecorator.getHeight();


        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(.25,.25));
        GridPane rootGP = mapGridGUIDecorator.drawComponents();
        // System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());

        StackPane sp = new StackPane();
        sp.getChildren().add(rootGP);

      /*  Car testCar;
        int c=0;
        for (int i=0; i<50; i++){
            testCar = generateCar(map,mapGridGUIDecorator,sp);
            if (testCar!=null)
                c++;}*/

        Car testCar;
        for (int i=0; i<6; i++) {
          //  testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 8, 1);
            //testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 8, 0);

            testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 9, 0);
            //testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 9, 1);

            //testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 15, 1);
            //testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 15, 0);

            //testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 20, 0);
           // testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 20, 1);
        }
        for (int i=0; i<1; i++) {
           testCar = generateCarOnRoad(map, mapGridGUIDecorator, sp, 20, 1);
        }



       // System.out.println("Number of cars is "+ c);

        Scene scene = new Scene(sp);
        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);


        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);

        //primaryStage.setFullScreen(true);

    }

    public static void main(String[] args) {

        launch(args);

    }
}