package londonsw.controller;


import londonsw.model.simulation.components.Intersection;
import londonsw.view.simulation.IntersectionGUI;

import java.util.HashMap;
import java.util.Map;

public class IntersectionController {
    private static Map<Intersection,IntersectionGUI> intersections;
    private static IntersectionController instance;

    protected IntersectionController() {
        intersections = new HashMap<Intersection,IntersectionGUI>();
    }

    public static IntersectionController getInstance() {
        if(instance == null) {
            instance = new IntersectionController();
        }
        return instance;
    }

    public static void addKeyValuePair(Intersection i, IntersectionGUI gui) {
        intersections.put(i,gui);
    }

    public static IntersectionGUI getIntersectionGui(Intersection i) {
        return intersections.get(i);
    }

}
