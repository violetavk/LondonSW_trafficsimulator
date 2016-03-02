package londonsw.controller;


import londonsw.model.simulation.components.Intersection;
import londonsw.view.simulation.IntersectionDecorator;

import java.util.HashMap;
import java.util.Map;

public class IntersectionController {
    private static Map<Intersection,IntersectionDecorator> intersections;
    private static IntersectionController instance;

    protected IntersectionController() {
        intersections = new HashMap<Intersection,IntersectionDecorator>();
    }

    public static IntersectionController getInstance() {
        if(instance == null) {
            instance = new IntersectionController();
        }
        return instance;
    }

    public static void addKeyValuePair(Intersection i, IntersectionDecorator gui) {
        intersections.put(i,gui);
    }

    public static IntersectionDecorator getIntersectionGui(Intersection i) {
        return intersections.get(i);
    }

}
