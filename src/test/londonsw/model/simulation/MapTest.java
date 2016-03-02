package londonsw.model.simulation;

import londonsw.model.simulation.components.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by felix on 19/02/2016.
 */
public class MapTest {

    @Test
    public void testSaveMap() throws Exception {
        Map map = drawTestMap();
        printMapGrid(map);
        System.out.println(System.getProperty("user.dir"));
        map.saveMap("FixedMap.map");
    }

    @Test
    public void testLoadMap() throws Exception {
        System.out.println("Opening map...");
        Map loaded = Map.loadMap("FixedMap.map");



        assertNotNull(loaded);
        printMapGrid(loaded);
    }

    @Test
    public void testGetAtLocation() throws Exception {
        Map map = drawTestMap();
        Coordinate x= new Coordinate(2,1);
        Component c =map.getAtLocation(new Coordinate(2,1));
        assertTrue(c instanceof Road);
    }



    public Map drawTestMap() throws Exception {
        Map map = new Map(6,5);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(4,1));
        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST,r1));
        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.WEST,r1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,3));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH,r2));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.NORTH,r2));
        Road r3 = new Road(new Coordinate(5,2), new Coordinate(5,3));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.NORTH,r3));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH,r3));
        Road r4 = new Road(new Coordinate(2,4), new Coordinate(4,4));
        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST,r4));
        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.WEST,r4));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);
        Intersection i2 = new Intersection(new Coordinate(5,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1,4));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(5,4));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

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