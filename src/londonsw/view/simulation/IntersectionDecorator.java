package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import londonsw.controller.IntersectionController;
import londonsw.model.simulation.components.Intersection;

/**
 * Associates an Intersection GUI with an instance of an Intersection. Each has up to 4
 * TrafficLightDecorators that live inside this GUI. The Intersection does not have any
 * explicit circles itself, it relies on the circles of each TrafficLightDecorator that
 * is associated with each TrafficLight in the Intersection instance.
 *
 * Exactly one IntersectionDecorator is created for exactly one Intersection.
 */
public class IntersectionDecorator {

    private Intersection intersection;
    private int width = 100;
    private int height = 100;

    private TrafficLightDecorator northLight;
    private TrafficLightDecorator eastLight;
    private TrafficLightDecorator southLight;
    private TrafficLightDecorator westLight;

    private Group root;
    private Scene scene;

    /**
     * Creates a GUI for that specific intersection. It creates TrafficLightDecorators for all
     * TrafficLights that are in this intersection. It draws and displays them in a Scene specified
     * by the global variables of width and height.
     * TODO add a scale factor to the graphics, because each x-coordinate, y-coordinate, and radius is based on 100x100
     * @param intersection an instance of an Intersection to associate this decorator
     */
    public IntersectionDecorator(Intersection intersection) {
        this.intersection = intersection;
        IntersectionController.addIntersectionAndDecoratorPair(intersection,this);
        root = new Group();
        scene = new Scene(root, width, height, Color.BEIGE);

        if(intersection.getNorthTrafficLight() != null) {
            northLight = new TrafficLightDecorator(intersection.getNorthTrafficLight());
            northLight.drawLight(50,15,12);
            root.getChildren().add(northLight.getCircle());
        }
        if(intersection.getEastTrafficLight() != null) {
            eastLight = new TrafficLightDecorator(intersection.getEastTrafficLight());
            eastLight.drawLight(85,50,12);
            root.getChildren().add(eastLight.getCircle());
        }
        if(intersection.getSouthTrafficLight() != null) {
            southLight = new TrafficLightDecorator(intersection.getSouthTrafficLight());
            southLight.drawLight(50,85,12);
            root.getChildren().add(southLight.getCircle());
        }
        if(intersection.getWestTrafficLight() != null) {
            westLight = new TrafficLightDecorator(intersection.getWestTrafficLight());
            westLight.drawLight(15,50,12);
            root.getChildren().add(westLight.getCircle());
        }
    }

    /**
     * This is mostly for debug, but it displays the intersection in the Stage provided. This might be able
     * to extend in the Map
     * @param stage the Stage to display this Intersection in
     */
    public void showIntersection(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

}
