package londonsw.model.simulation;

import londonsw.model.simulation.components.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class is the underlying structure of our Map. It is a 2D-array of map Components,
 * each Component being something that you would want to be displayed on the map, such as
 * a Road or Intersection.
 */
public class MapGrid implements Serializable, IMapGrid {

    private static final long serialVersionUID = -8256761045077358688L;
    private int width;
    private int height;
    private Component[][] grid;
    private ArrayList<Component> allComponents;

    /**
     * Creates a brand new MapGrid instance to be part of a Map
     * @param width width of the Map that this will be part of
     * @param height height of the Map that this will be part of
     */
    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Component[height][width];
        allComponents = new ArrayList<Component>();
    }

    /**
     * Returns the actual 2D-array of Components
     * @return 2D-array array of Components signifying the layout of the Map
     */
    public Component[][] getGrid() {
        return grid;
    }

    /**
     * Gets the width of the grid
     * @return width of the grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the grid
     * @return height of the grid
     */
    public int getHeight() {
        return height;
    }

    public ArrayList<Component> getAllComponents() { return allComponents; }

    public Component get(int x, int y) {
        return grid[y][x];
    }

    public void clearCell(int x, int y) {
        grid[y][x] = null;
    }

    /**
     * Adds a Component to the map grid structure
     * @param c the Component to be added
     * @return true if successfully added, false otherwise
     */
    @SuppressWarnings("Duplicates")
    public boolean addComponent(Component c) {
        if(c instanceof Intersection) {
            Intersection i = (Intersection) c;
            Coordinate coord = i.getLocation();
            grid[coord.getY()][coord.getX()] = i;
            allComponents.add(i);
            return true;
        }
        else if(c instanceof Road) { // TODO there must be a better way of representing a road cell
            Road road = (Road) c;
            Coordinate start = road.getStartLocation();
            Coordinate end = road.getEndLocation();
            int startX = start.getX();
            int startY = start.getY();
            int endX = end.getX();
            int endY = end.getY();

            if(road.runsVertically()) { // road runs vertically
                if(startY <= endY) { // start coordinate is north of end coordinate
                    for(int i = startY; i <= endY; i++) {
                        grid[i][startX] = road;
                    }
                    allComponents.add(road);
                    return true;
                }
                else { // start coordinate is south of end coordinate
                    for(int i = endY; i <= startY; i++) {
                        grid[i][startX] = road;
                    }
                    allComponents.add(road);
                    return true;
                }
            }
            else { // road runs horizontally
                if(startX <= endX) { // start coordinate is west of end coordinate
                    for(int i = startX; i <= endX; i++) {
                        grid[startY][i] = road;
                    }
                    allComponents.add(road);
                    return true;
                }
                else {
                    for(int i = endX; i <= startX; i++) { // start coordinate is east of end coordinate
                        grid[startY][i] = road;
                    }
                    allComponents.add(road);
                    return true;
                }
            }
        }
        else
            return false;
    }

}
