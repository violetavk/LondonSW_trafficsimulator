package londonsw.model.simulation;

import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.components.*;
import londonsw.view.simulation.IntersectionDecorator;
import londonsw.view.simulation.TrafficLightDecorator;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the graph structure that our map holds (Roads and Intersections)
 */
public class Map implements Serializable {
    private static final long serialVersionUID = -1932129809569954013L;
    private ArrayList<Road> roads;
    private ArrayList<Intersection> intersections;
    private MapGrid grid;
    private final static String MAP_DIR = "./maps/";
    private final static String TRAFFIC_LIGHTS_TEMP = "_data";


    /**
     * Creates an empty map with no roads or intersections
     *
     * @param width width of the map
     * @param height height of the map
     */
    public Map(int width, int height) {
        roads = new ArrayList<Road>();
        intersections = new ArrayList<Intersection>();
        grid = new MapGrid(width,height);
    }

    /**
     * Gets the width of the map
     *
     * @return integer representing width of the map
     */
    public int getWidth() {
        return grid.getWidth();
    }

    /**
     * Gets the height of the map
     *
     * @return integer representing the height of the map
     */
    public int getHeight() {
        return grid.getHeight();
    }

    /**
     * Gets all the roads currently in the map
     * @return ArrayList of all Roads in the map
     */
    public ArrayList<Road> getRoads() {
        return roads;
    }

    public Road getRandomRoad()
    {
        ArrayList<Road> roads = getRoads();

        Road road = null;

        Random randomRoad = new Random();

        if(roads.size()>0) {

            int roadSize = randomRoad.nextInt(this.getRoads().size());

            road = roads.get(roadSize);
        }

        return road;
    }

    public Lane getRandomLane() {
        Road road = getRandomRoad();
        Lane lane = null;

        if (road != null) {
            Random randomLane = new Random();
            int numberLanes = road.getNumberLanes();


            if (numberLanes > 0) {
                int laneSize = randomLane.nextInt(road.getNumberLanes());
                lane = road.getLanes().get(laneSize);
                while (lane.getState() != 1) { // if road is disabled, choose a new one
                    road = getRandomRoad();
                    lane = road.getLanes().get(randomLane.nextInt(road.getNumberLanes()));
                }
            }
        }

        return lane;

    }

    public int getRandomCell(){

        Random randomCell = new Random();

        Lane randomLane = getRandomLane();

        return randomCell.nextInt(randomLane.getLength());
    }

    /**
     * If you have a list of roads already, set the roads to the map
     * @param roads an ArrayList of valid Road instances
     */
    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
        for(Road r : roads)
            grid.addComponent(r);
    }

    /**
     * Gets all the intersections currently in the map
     * @return ArrayList of all intersections in the map
     */
    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    /**
     * If you have a list of intersections already, set the intersections to the map
     * @param intersections
     */
    public void setIntersections(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
        for(Intersection i : intersections)
            grid.addComponent(i);
    }

    /**
     * Gets the underlying MapGrid of the Map and returns it
     * @return the actual MapGrid of this map
     */
    public MapGrid getGrid() {
        return grid;
    }

    /**
     * If you have a valid MapGrid grid, set it as the grid to this Map
     * @return valid instance of MapGrid
     */
    public void setGrid(MapGrid grid) {
        this.grid = grid;
    }

    /**
     * Adds a brand new road to this map.
     * @param r valid Road instance to put into the Map
     */
    public void addRoad(Road r) {
        roads.add(r);
        grid.addComponent(r);
    }

    public Component getAtLocation(Coordinate c) {
        int x = c.getX();
        int y = c.getY();
        return grid.get(x,y);
    }

    /**
     * Adds a brand new intersection to this map
     * @param i valid Intersection instance to put into the Map
     */
    public void addIntersection(Intersection i) {
        intersections.add(i);
        grid.addComponent(i);
    }

    public void clearCell(Coordinate c) {
        int x = c.getX();
        int y = c.getY();
        grid.clearCell(x, y);
    }

    public void saveMap(String fileName)
    {
        try
        {
            File directory = new File(MAP_DIR);
            if(!directory.exists()) {
                directory.mkdir();
            }

            String path = MAP_DIR + fileName;

            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }

            // save the map
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in " + path + " and " + path + TRAFFIC_LIGHTS_TEMP);
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public static Map loadMap(String fileName) {
        String path = MAP_DIR + fileName;
        Map map = null;

        try {
            // open the Map
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (Map) in.readObject();
            in.close();
            fileIn.close();

            /*
             * Each intersection has 4 traffic lights, for each traffic light:
             *      - subscribe to the ticker
             *      - create a new Decorator
             * For each intersection:
             *      - create a new IntersectionDecorator
             *      - link each new TrafficLightDecorator to the corresponding field in the IntersectionDecorator
             * */
            for(Intersection i : map.getIntersections()) {
                IntersectionDecorator decorator = new IntersectionDecorator(i);
                TrafficLight north = i.getNorthTrafficLight();
                TrafficLight south = i.getSouthTrafficLight();
                TrafficLight east = i.getEastTrafficLight();
                TrafficLight west = i.getWestTrafficLight();
                if(north != null) {
                    north.subscribeToTicker();
                    TrafficLightDecorator dec = TrafficLightController.getInstance().createNewDecorator(north);
                    decorator.setNorthTrafficLightDecorator(dec);
                }
                if(south != null) {
                    south.subscribeToTicker();
                    TrafficLightDecorator dec = TrafficLightController.getInstance().createNewDecorator(south);
                    decorator.setSouthTrafficLightDecorator(dec);
                }
                if(east != null) {
                    east.subscribeToTicker();
                    TrafficLightDecorator dec = TrafficLightController.getInstance().createNewDecorator(east);
                    decorator.setEastTrafficLightDecorator(dec);
                }
                if(west != null) {
                    west.subscribeToTicker();
                    TrafficLightDecorator dec = TrafficLightController.getInstance().createNewDecorator(west);
                    decorator.setWestTrafficLightDecorator(dec);
                }
            }

            System.out.println("# TrafficLights's subscribed: " + Ticker.getSubscribers().size());
            System.out.println("# of lights added: " + TrafficLightController.getInstance().getAllTrafficLights().size());
            System.out.println("# of decorators: " + TrafficLightController.getInstance().getTrafficLightsMap().size());

        } catch(IOException i) {
            System.out.println("IO Exception");
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Did not find object");
            e.printStackTrace();
        }

        return map;
    }
}