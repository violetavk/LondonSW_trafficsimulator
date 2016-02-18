package londonsw.model.simulation;

import londonsw.model.simulation.components.Component;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Road;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapGridTest {

    MapGrid mapGrid;

    @Before
    public void setUp() {
        mapGrid = new MapGrid(8,5);
    }

    @Test
    public void testGetWidth() {
        assertEquals(mapGrid.getWidth(),8);
    }

    @Test
    public void testGetHeight() {
        assertEquals(mapGrid.getHeight(), 5);
    }

    public void printMapGrid() {
        Component[][] grid = mapGrid.getGrid();
        int width = mapGrid.getWidth();
        int height = mapGrid.getHeight();
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

    @Test
    public void testAddComponent() throws Exception {
        printMapGrid();
        Intersection i1 = new Intersection(new Coordinate(3,3));
        mapGrid.addComponent(i1);
        printMapGrid();
        Intersection i2 = new Intersection(new Coordinate(2,7));
        mapGrid.addComponent(i2);
        printMapGrid();
        Road r1 = new Road(new Coordinate(0,0),new Coordinate(5,0));
        mapGrid.addComponent(r1);
        printMapGrid();
        Road r2 = new Road(new Coordinate(0,3), new Coordinate(0,4));
        mapGrid.addComponent(r2);
        printMapGrid();
    }
}