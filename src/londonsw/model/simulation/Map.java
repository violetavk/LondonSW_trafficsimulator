package londonsw.model.simulation;

import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.components.*;
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

    public void saveMap(String fileName)
    {
        try
        {
            File directory = new File(MAP_DIR);
            if(!directory.exists()) {
                directory.mkdir();
            }

            String path = MAP_DIR + fileName;
            String pathTemp = path + TRAFFIC_LIGHTS_TEMP;

            File file = new File(path);
            File fileTemp = new File(pathTemp); // used for the traffic lights
            if(!file.exists()) {
                file.createNewFile();
            }
            if(!fileTemp.exists()) {
                fileTemp.createNewFile();
            }

            // save the map
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            // save the traffic lights & intersection data
            FileOutputStream fileOutTemp = new FileOutputStream(pathTemp);
            ObjectOutputStream outTemp = new ObjectOutputStream(fileOutTemp);
            ArrayList<Object> trafficLights = new ArrayList<>();
            ArrayList<TrafficLight> lightsList = (ArrayList<TrafficLight>) convertFromStaticToSave(TrafficLightController.getAllTrafficLights());
            System.out.println("Saved lights list: " + lightsList.size());
          //  java.util.Map<TrafficLight,TrafficLightDecorator> lightsMap = (java.util.Map<TrafficLight, TrafficLightDecorator>) convertFromStaticToSave(TrafficLightController.getTrafficLightsMap());
          //  System.out.println("Saved lights map: " + lightsMap.size());
            // TODO get all the lights, but then convert them to a non static version of the list... have a helper method to copy the things
            trafficLights.add(lightsList); // the array list of all traffic lights
           // trafficLights.add(lightsMap); // the HashMap of (TrafficLight, TrafficLightDecorator) pairs
            outTemp.writeObject(trafficLights);
            outTemp.close();
            fileOutTemp.close();


            System.out.printf("Serialized data is saved in " + path + " and " + path + TRAFFIC_LIGHTS_TEMP);
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public static Map loadMap(String fileName) {
        String path = MAP_DIR + fileName;
        String pathLights = MAP_DIR + fileName + TRAFFIC_LIGHTS_TEMP;
        Map map = null;
        ArrayList<TrafficLight> allLights = null;
        java.util.Map<TrafficLight,TrafficLightDecorator> lightsMap = null;

        try {
            // open the Map
            File file = new File(path);
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (Map) in.readObject();
            in.close();
            fileIn.close();

            // open the Traffic Lights and TODO intersections
            FileInputStream fileInLights = new FileInputStream(pathLights);
            ObjectInputStream inLights = new ObjectInputStream(fileInLights);
            ArrayList<Object> list = (ArrayList<Object>) inLights.readObject();
            System.out.println("Loading lights.. size = " + list.size()); // should be 2
            allLights = (ArrayList<TrafficLight>) list.get(0);
            System.out.println("Found " + allLights.size() + " lights in list");
            lightsMap = (java.util.Map<TrafficLight,TrafficLightDecorator>) list.get(1);
            System.out.println("Found " + lightsMap.size() + " in map");
          //  TrafficLightController.setAllTrafficLights(allLights);
           // TrafficLightController.setTrafficLightMap(lightsMap);

        } catch(IOException i) {
            System.out.println("IO Exception");
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Did not find object");
            e.printStackTrace();
        }

        return map;
    }

    public Object convertFromStaticToSave(Object toConvert) {
        if(toConvert instanceof ArrayList) {
            ArrayList newList = new ArrayList();
            Collections.copy(newList, (ArrayList) toConvert);
            return newList;
        }

        if(toConvert instanceof java.util.Map) {
            java.util.Map m = new HashMap<TrafficLight,TrafficLightDecorator>((HashMap<TrafficLight,TrafficLightDecorator>) toConvert);
            return m;
        }

        return null;
    }
}