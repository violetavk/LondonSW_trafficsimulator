package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import londonsw.controller.IntersectionController;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.ResizeFactor;

import java.io.Serializable;

/**
 * Associates an Intersection GUI with an instance of an Intersection. Each has up to 4
 * TrafficLightDecorators that live inside this GUI. The Intersection does not have any
 * explicit circles itself, it relies on the circles of each TrafficLightDecorator that
 * is associated with each TrafficLight in the Intersection instance.
 *
 * Exactly one IntersectionDecorator is created for exactly one Intersection.
 */
public class IntersectionDecorator implements Serializable {

    private static final long serialVersionUID = -197785071328536445L;
    private Intersection intersection;
    private int width = 100;
    private int height = 100;

    private TrafficLightDecorator northLight;
    private TrafficLightDecorator eastLight;
    private TrafficLightDecorator southLight;
    private TrafficLightDecorator westLight;

    private ResizeFactor resizeFactor;

    private Group root;
    private Scene scene;

    /**
     * Creates a GUI for that specific intersection. It creates TrafficLightDecorators for all
     * TrafficLights that are in this intersection. It draws and displays them in a Scene specified
     * by the global variables of width and height.
     * @param intersection an instance of an Intersection to associate this decorator
     */
    public IntersectionDecorator(Intersection intersection) {
        this.intersection = intersection;
        IntersectionController.addIntersectionAndDecoratorPair(intersection,this);
    }

    public void setResizeFactor(ResizeFactor rf) {
        resizeFactor = rf;
    }

    public StackPane drawIntersection() {
        StackPane stackPane = new StackPane();

        String roadBackgroundPath = "IntersectionX.png";
        Image image = new Image(roadBackgroundPath);
        Image im = new Image(roadBackgroundPath, image.getHeight() * resizeFactor.getResizeX(), image.getWidth() * resizeFactor.getResizeY(), false, true);
        ImageView iv = new ImageView(im);

        Pane lights = new Pane();
        double middleCoordX = 50*resizeFactor.getResizeX();
        double middleCoordY = 50*resizeFactor.getResizeY();
        double edgeCloseX = 18*resizeFactor.getResizeX();
        double edgeCloseY = 18*resizeFactor.getResizeY();
        double edgeFarX = 82*resizeFactor.getResizeX();
        double edgeFarY = 82*resizeFactor.getResizeY();
        double radius = 15*resizeFactor.getResizeX();
        if(intersection.getNorthTrafficLight() != null) {
            northLight = new TrafficLightDecorator(intersection.getNorthTrafficLight());
            northLight.drawLight(middleCoordX,edgeCloseY,radius);
            lights.getChildren().add(northLight.getCircle());
        }
        if(intersection.getEastTrafficLight() != null) {
            eastLight = new TrafficLightDecorator(intersection.getEastTrafficLight());
            eastLight.drawLight(edgeFarX,middleCoordY,radius);
            lights.getChildren().add(eastLight.getCircle());
        }
        if(intersection.getSouthTrafficLight() != null) {
            southLight = new TrafficLightDecorator(intersection.getSouthTrafficLight());
            southLight.drawLight(middleCoordX,edgeFarY,radius);
            lights.getChildren().add(southLight.getCircle());
        }
        if(intersection.getWestTrafficLight() != null) {
            westLight = new TrafficLightDecorator(intersection.getWestTrafficLight());
            westLight.drawLight(edgeCloseX,middleCoordY,radius);
            lights.getChildren().add(westLight.getCircle());
        }

        stackPane.setOnMouseClicked(event -> System.out.println("Clicked " + this.intersection.getIdIntersection()));

        stackPane.getChildren().add(iv);
        stackPane.getChildren().add(lights);
        return stackPane;
    }

    /**
     * This method is only for DEBUG! Displays the intersection in the given stack pane
     * @param stage the Stage to display this Intersection in
     */
    public void showIntersection(Stage stage, StackPane s) {
        root = new Group();
        scene = new Scene(root, width, height, Color.BEIGE);
        root.getChildren().add(s);
        stage.setScene(scene);
        stage.show();
    }

}
