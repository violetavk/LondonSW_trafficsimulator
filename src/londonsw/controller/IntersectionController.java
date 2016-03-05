package londonsw.controller;


import londonsw.model.simulation.components.Intersection;
import londonsw.view.simulation.IntersectionDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntersectionController {
    private static Map<Intersection,IntersectionDecorator> intersections = new HashMap<Intersection,IntersectionDecorator>();
    private static ArrayList<Intersection> allIntersections = new ArrayList<>();

    /**
     * Prevents instantiation of this class
     */
    protected IntersectionController() {  }

    /**
     * Register an instance of an Intersection to and IntersectionDecorator, and adds the Intersection to a list to keep
     * track of
     * @param i the instance of Intersection to register
     * @param gui the instance of IntersectionDecorator for that intersection
     */
    public static void addIntersectionAndDecoratorPair(Intersection i, IntersectionDecorator gui) {
        intersections.put(i,gui);
        allIntersections.add(i);
    }

    /**
     * Retrieve the decorator for that specific Intersection
     * @param i the Intersection to get the decorator for
     * @return the decorator associated with that Intersection
     */
    public static IntersectionDecorator getIntersectionDecoratorForIntersection(Intersection i) {
        return intersections.get(i);
    }

    /**
     * Gets all Intersections in the system
     * @return ArrayList of all Intersections in the system
     */
    public ArrayList<Intersection> getAllIntersections() {
        return allIntersections;
    }

}
