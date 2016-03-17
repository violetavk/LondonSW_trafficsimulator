package londonsw.view.simulation;

/**
 * Created by felix on 12/03/2016.
 */

import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.*;

/**
 * Created by felix on 12/03/2016.
 */
public class MapExamples {



    public static Map drawMap1() throws Exception {
        Map map = new Map(15,15);

        Road r1 = new Road(new Coordinate(2,1),new Coordinate(10,1));
        Road r2 = new Road(new Coordinate(2,10),new Coordinate(10,10));
        Road r3 = new Road(new Coordinate(1,2), new Coordinate(1,9));
        Road r4 = new Road(new Coordinate(11,2), new Coordinate(11,9));
        Road rExit = new Road(new Coordinate(12,1), new Coordinate(12,1));

        Road roadMatrix[] = {r1,r2,r3,r4 , rExit};

        Lane laneR1L1 = new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST);
        Lane laneR1L2 = new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST);
        Lane laneR1L3 = new Lane(r1.getEndLocation(),r1.getStartLocation(), MapDirection.WEST);
        Lane laneR1L4 = new Lane(r1.getEndLocation(),r1.getStartLocation(), MapDirection.WEST);

        Lane laneR2L1 = new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.EAST);
        Lane laneR2L2 = new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.EAST);
        Lane laneR2L3 = new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.WEST);
        Lane laneR2L4 = new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.WEST);

        Lane laneR3L1 = new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH);
        Lane laneR3L2 = new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH);
        Lane laneR3L3 = new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);
        Lane laneR3L4 = new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);

        Lane laneR4L1 = new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.NORTH);
        Lane laneR4L2 = new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.NORTH);
        Lane laneR4L3 = new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.SOUTH);
        Lane laneR4L4 = new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.SOUTH);

        Lane laneExitL1 = new Lane(rExit.getStartLocation(),rExit.getEndLocation(),MapDirection.EAST);

        r1.addLane(laneR1L1);
        //r1.addLane(laneR1L2);
        //r1.addLane(laneR1L3);
        r1.addLane(laneR1L4);

        r2.addLane(laneR2L1);
        //r2.addLane(laneR2L2);
        //r2.addLane(laneR2L3);
        r2.addLane(laneR2L4);

        r3.addLane(laneR3L1);
        //r3.addLane(laneR3L2);
        //r3.addLane(laneR3L3);
        r3.addLane(laneR3L4);

        //laneR4L3.setState(0);
        //laneR4L4.setState(0);

        r4.addLane(laneR4L1);
        //r4.addLane(laneR4L2);
        //r4.addLane(laneR4L3);
        r4.addLane(laneR4L4);

        rExit.addLane(laneExitL1);

        for(int i = 0 ; i < roadMatrix.length; i++)
            map.addRoad(roadMatrix[i]);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r3);
        i1.setDefaultTrafficLightsForRoads();

        Intersection i2 = new Intersection(new Coordinate(11,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r4);
        i2.setEastRoad(rExit);

        Intersection i3 = new Intersection(new Coordinate(1,10));
        i3.setEastRoad(r2);
        i3.setNorthRoad(r3);

        Intersection i4 = new Intersection(new Coordinate(11,10));

        i4.setWestRoad(r2);
        i4.setNorthRoad(r4);


        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return  map;
    }

    public static Map drawMap1_1() throws Exception{

        Map map = new Map(6,6);

        Road R1 = new Road(new Coordinate(1,1),new Coordinate(4,1));

        Lane laneR1L1 = new Lane(R1.getStartLocation(),R1.getEndLocation(),MapDirection.EAST);

        R1.addLane(laneR1L1);

        map.addRoad(R1);

        return map;

    }

    public static Map drawTestMapBasic() throws Exception {

        Map map = new Map(10, 10);

        Road r1 = new Road(new Coordinate(2, 1), new Coordinate(8, 1));
        Road r2 = new Road(new Coordinate(1, 2), new Coordinate(1, 8));
        Road r3 = new Road(new Coordinate(9, 2), new Coordinate(9, 8));
        Road r4 = new Road(new Coordinate(2, 9), new Coordinate(8, 9));

        r1.addLane(new Lane(r1.getStartLocation(), r1.getEndLocation(), MapDirection.EAST));

        r2.addLane(new Lane(r2.getEndLocation(), r2.getStartLocation(), MapDirection.NORTH));

        r3.addLane(new Lane(r3.getStartLocation(), r3.getEndLocation(), MapDirection.SOUTH));

        r4.addLane(new Lane(r4.getEndLocation(), r4.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1, 1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9, 1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1, 9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9, 9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public static Map drawTestMapSingleLine() throws Exception {

        Map map = new Map(25, 25);

        Road r1 = new Road(new Coordinate(2, 1), new Coordinate(8, 1));
        Road r2 = new Road(new Coordinate(1, 2), new Coordinate(1, 8));
        Road r3 = new Road(new Coordinate(9, 2), new Coordinate(9, 8));
        Road r4 = new Road(new Coordinate(2, 9), new Coordinate(8, 9));

        Road r5 = new Road(new Coordinate(10, 1), new Coordinate(16, 1));
        Road r6 = new Road(new Coordinate(17, 2), new Coordinate(17, 8));
        Road r7 = new Road(new Coordinate(10, 9), new Coordinate(16, 9));

        Lane disabledLane = new Lane(r3.getStartLocation(), r3.getEndLocation(), MapDirection.SOUTH);

        //disable lane
        disabledLane.setState(0);

        r1.addLane(new Lane(r1.getStartLocation(), r1.getEndLocation(), MapDirection.EAST));

        r2.addLane(new Lane(r2.getEndLocation(), r2.getStartLocation(), MapDirection.NORTH));

        r3.addLane(disabledLane);

        r4.addLane(new Lane(r4.getEndLocation(), r4.getStartLocation(), MapDirection.WEST));

        r5.addLane(new Lane(r5.getStartLocation(), r5.getEndLocation(), MapDirection.EAST));

        r6.addLane(new Lane(r6.getStartLocation(), r6.getEndLocation(), MapDirection.SOUTH));

        r7.addLane(new Lane(r7.getEndLocation(), r7.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);
        map.addRoad(r5);
        map.addRoad(r6);
        map.addRoad(r7);

        Intersection i1 = new Intersection(new Coordinate(1, 1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9, 1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        i2.setEastRoad(r5);

        Intersection i3 = new Intersection(new Coordinate(1, 9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);

        Intersection i4 = new Intersection(new Coordinate(9, 9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);
        i4.setEastRoad(r7);

        Intersection i5 = new Intersection(new Coordinate(17, 1));
        i5.setWestRoad(r5);
        i5.setSouthRoad(r6);

        Intersection i6 = new Intersection(new Coordinate(17, 9));
        i6.setNorthRoad(r6);
        i6.setWestRoad(r7);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);
        map.addIntersection(i5);
        map.addIntersection(i6);

        return map;
    }



    public static Map drawTestMapSimple() throws Exception {

        Map map = new Map(10,10);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));



        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH));

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public static Map drawTestMapBig() throws Exception {

        Map map = new Map(20,20);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));

        Road r5 = new Road(new Coordinate(10,1), new Coordinate(16,1));
        Road r6 = new Road(new Coordinate(17,2), new Coordinate(17,8));
        Road r7 = new Road(new Coordinate(10,9), new Coordinate(16,9));

        Lane disabledLane = new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);

        //disable lane
        disabledLane.setState(0);

        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(disabledLane);

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        r5.addLane(new Lane(r5.getStartLocation(),r5.getEndLocation(), MapDirection.EAST));
        r5.addLane(new Lane(r5.getEndLocation(),r5.getStartLocation(), MapDirection.WEST));

        r6.addLane(new Lane(r6.getEndLocation(),r6.getStartLocation(), MapDirection.NORTH));
        r6.addLane(new Lane(r6.getStartLocation(),r6.getEndLocation(), MapDirection.SOUTH));

        r7.addLane(new Lane(r7.getStartLocation(),r7.getEndLocation(), MapDirection.EAST));
        r7.addLane(new Lane(r7.getEndLocation(),r7.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);
        map.addRoad(r5);
        map.addRoad(r6);
        map.addRoad(r7);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        i2.setEastRoad(r5);

        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);

        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);
        i4.setEastRoad(r7);

        Intersection i5 = new Intersection(new Coordinate(17,1));
        i5.setWestRoad(r5);
        i5.setSouthRoad(r6);

        Intersection i6 = new Intersection(new Coordinate(17,9));
        i6.setNorthRoad(r6);
        i6.setWestRoad(r7);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);
        map.addIntersection(i5);
        map.addIntersection(i6);

        return map;
    }

    //Added new Map with two lanes
    public static Map drawTestMapExample() throws Exception {

        Map map = new Map(22,22);

        Road r01 = new Road(new Coordinate(2,1), new Coordinate(4,1));
        Road r02 = new Road(new Coordinate(1,2), new Coordinate(1,4));
        Road r03 = new Road(new Coordinate(1,6), new Coordinate(1,9));
        Road r04 = new Road(new Coordinate(1,11), new Coordinate(1,14));
        Road r05 = new Road(new Coordinate(6,1), new Coordinate(12,1));
        Road r06 = new Road(new Coordinate(14,1), new Coordinate(19,1));
        Road r07 = new Road(new Coordinate(20,2), new Coordinate(20,9));
        Road r08 = new Road(new Coordinate(20,11), new Coordinate(20,14));
        Road r09 = new Road(new Coordinate(9,15), new Coordinate(19,15));
        Road r10 = new Road(new Coordinate(2,15), new Coordinate(7,15));
        Road r11 = new Road(new Coordinate(5,2), new Coordinate(5,4));
        Road r12 = new Road(new Coordinate(2,5), new Coordinate(4,5));
        Road r13 = new Road(new Coordinate(2,10), new Coordinate(7,10));
        Road r14 = new Road(new Coordinate(9,10), new Coordinate(12,10));
        Road r15 = new Road(new Coordinate(14,10), new Coordinate(19,10));
        Road r16 = new Road(new Coordinate(8,11), new Coordinate(8,14));
        Road r17 = new Road(new Coordinate(13,2), new Coordinate(13,4));
        Road r18 = new Road(new Coordinate(13,6), new Coordinate(13,9));
        Road r19 = new Road(new Coordinate(6,5),new Coordinate(12,5));
        Road r20 = new Road(new Coordinate(1,16),new Coordinate(1,19));
        Road r21 = new Road(new Coordinate(8,16),new Coordinate(8,19));
        Road r22 = new Road(new Coordinate(2,20),new Coordinate(7,20));
        Road r23 = new Road(new Coordinate(9,20),new Coordinate(19,20));
        Road r24 = new Road(new Coordinate(20,16), new Coordinate(20,19));

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
        r07.addLane(new Lane(r07.getEndLocation(),r07.getStartLocation(),MapDirection.NORTH));
        r08.addLane(new Lane(r08.getEndLocation(),r08.getStartLocation(),MapDirection.NORTH));

        Lane lane9Closed = new Lane(r09.getStartLocation(),r09.getEndLocation(),MapDirection.EAST);

        //lane9Closed.setState(0);

        r09.addLane(lane9Closed);


        r10.addLane(new Lane(r10.getStartLocation(),r10.getEndLocation(),MapDirection.EAST));
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

        Lane lane21NClosed = new Lane(r21.getEndLocation(),r21.getStartLocation(),MapDirection.NORTH);
        //lane21Closed.setState(0);

        r21.addLane(lane21NClosed);

        r22.addLane(new Lane(r22.getStartLocation(),r22.getEndLocation(),MapDirection.EAST));
        r23.addLane(new Lane(r23.getStartLocation(),r23.getEndLocation(),MapDirection.EAST));
        r24.addLane(new Lane(r24.getEndLocation(),r24.getStartLocation(),MapDirection.NORTH));


        r01.addLane(new Lane(r01.getEndLocation(),r01.getStartLocation(),MapDirection.WEST));
        r02.addLane(new Lane (r02.getStartLocation(),r02.getEndLocation(),MapDirection.SOUTH));
        r03.addLane(new Lane(r03.getStartLocation(),r03.getEndLocation(),MapDirection.SOUTH));
        r04.addLane(new Lane(r04.getStartLocation(),r04.getEndLocation(),MapDirection.SOUTH));
        r05.addLane(new Lane(r05.getEndLocation(),r05.getStartLocation(),MapDirection.WEST));
        r06.addLane(new Lane(r06.getEndLocation(),r06.getStartLocation(),MapDirection.WEST));
        r07.addLane(new Lane(r07.getStartLocation(),r07.getEndLocation(),MapDirection.SOUTH));
        r08.addLane(new Lane(r08.getStartLocation(),r08.getEndLocation(),MapDirection.SOUTH));
        r09.addLane(new Lane(r09.getEndLocation(),r09.getStartLocation(),MapDirection.WEST));
        r10.addLane(new Lane(r10.getEndLocation(),r10.getStartLocation(),MapDirection.WEST));
        r11.addLane(new Lane(r11.getStartLocation(),r11.getEndLocation(),MapDirection.SOUTH));
        r12.addLane(new Lane(r12.getEndLocation(),r12.getStartLocation(),MapDirection.WEST));

        Lane lane13WClosed = new Lane(r13.getEndLocation(),r13.getStartLocation(),MapDirection.WEST);
        //lane13WClosed.setState(0);

        r13.addLane(lane13WClosed);
        r14.addLane(new Lane(r14.getEndLocation(),r14.getStartLocation(),MapDirection.WEST));
        r15.addLane(new Lane(r15.getEndLocation(),r15.getStartLocation(),MapDirection.WEST));
        r16.addLane(new Lane(r16.getStartLocation(),r16.getEndLocation(),MapDirection.SOUTH));
        r17.addLane(new Lane(r17.getStartLocation(),r17.getEndLocation(),MapDirection.SOUTH));
        r18.addLane(new Lane(r18.getStartLocation(),r18.getEndLocation(),MapDirection.SOUTH));
        r19.addLane(new Lane(r19.getEndLocation(),r19.getStartLocation(),MapDirection.WEST));
        r20.addLane(new Lane(r20.getStartLocation(),r20.getEndLocation(),MapDirection.SOUTH));

        Lane lane21SClosed = new Lane(r21.getStartLocation(),r21.getEndLocation(),MapDirection.SOUTH);
        //lane21SClosed.setState(0);

        r21.addLane(lane21SClosed);

        r22.addLane(new Lane(r22.getEndLocation(),r22.getStartLocation(),MapDirection.WEST));
        r23.addLane(new Lane(r23.getEndLocation(),r23.getStartLocation(),MapDirection.WEST));
        r24.addLane(new Lane(r24.getStartLocation(),r24.getEndLocation(),MapDirection.SOUTH));


        Intersection i01 = new Intersection(new Coordinate(1,1));
        i01.setEastRoad(r01);
        i01.setSouthRoad(r02);
        i01.setDefaultTrafficLightsForRoads();

        Intersection i02 = new Intersection(new Coordinate(1,5));
        i02.setNorthRoad(r02);
        i02.setSouthRoad(r03);
        i02.setEastRoad(r12);
        i02.setDefaultTrafficLightsForRoads();

        Intersection i03 = new Intersection(new Coordinate(1,10));
        i03.setNorthRoad(r03);
        i03.setSouthRoad(r04);
        i03.setEastRoad(r13);
        i03.setDefaultTrafficLightsForRoads();

        Intersection i04 = new Intersection(new Coordinate(1,15));
        i04.setNorthRoad(r04);
        i04.setEastRoad(r10);
        i04.setSouthRoad(r20);
        i04.setDefaultTrafficLightsForRoads();

        Intersection i05 = new Intersection(new Coordinate(5,1));
        i05.setWestRoad(r01);
        i05.setEastRoad(r05);
        i05.setSouthRoad(r11);
        i05.setDefaultTrafficLightsForRoads();

        Intersection i06 = new Intersection(new Coordinate(13,1));
        i06.setWestRoad(r05);
        i06.setEastRoad(r06);
        i06.setSouthRoad(r17);
        i06.setDefaultTrafficLightsForRoads();

        Intersection i07 = new Intersection(new Coordinate(20,1));
        i07.setWestRoad(r06);
        i07.setSouthRoad(r07);
        i07.setDefaultTrafficLightsForRoads();

        Intersection i08 = new Intersection(new Coordinate(20,10));
        i08.setNorthRoad(r07);
        i08.setSouthRoad(r08);
        i08.setWestRoad(r15);
        i08.setDefaultTrafficLightsForRoads();

        Intersection i09 = new Intersection(new Coordinate(20,15));
        i09.setNorthRoad(r08);
        i09.setWestRoad(r09);
        i09.setSouthRoad(r24);
        i09.setDefaultTrafficLightsForRoads();

        Intersection i10 = new Intersection(new Coordinate(8,15));
        i10.setEastRoad(r09);
        i10.setWestRoad(r10);
        i10.setNorthRoad(r16);
        i10.setSouthRoad(r21);
        i10.setDefaultTrafficLightsForRoads();

        Intersection i11 = new Intersection(new Coordinate(5,5));
        i11.setWestRoad(r12);
        i11.setNorthRoad(r11);
        i11.setEastRoad(r19);
        i11.setDefaultTrafficLightsForRoads();

        Intersection i12 = new Intersection(new Coordinate(8,10));
        i12.setWestRoad(r13);
        i12.setEastRoad(r14);
        i12.setSouthRoad(r16);
        i12.setDefaultTrafficLightsForRoads();    //enables tl

        Intersection i13 = new Intersection(new Coordinate(13,10));
        i13.setWestRoad(r14);
        i13.setEastRoad(r15);
        i13.setNorthRoad(r18);
        i13.setDefaultTrafficLightsForRoads();

        Intersection i14 =new Intersection((new Coordinate(13,5)));
        i14.setNorthRoad(r17);
        i14.setSouthRoad(r18);
        i14.setWestRoad(r19);
        i14.setDefaultTrafficLightsForRoads();

        Intersection i15 =new Intersection(new Coordinate(1,20));
        i15.setNorthRoad(r20);
        i15.setEastRoad(r22);
        i15.setDefaultTrafficLightsForRoads();

        Intersection i16 =new Intersection(new Coordinate(8,20));
        i16.setWestRoad(r22);
        i16.setNorthRoad(r21);
        i16.setEastRoad(r23);
        i16.setDefaultTrafficLightsForRoads();

        Intersection i17= new Intersection (new Coordinate(20,20));
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

}

