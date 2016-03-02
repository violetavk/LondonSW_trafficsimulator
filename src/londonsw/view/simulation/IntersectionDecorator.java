package londonsw.view.simulation;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import londonsw.model.simulation.components.Intersection;

public class IntersectionDecorator {

    private Intersection intersection;
    private int width;
    private int height;

    private TrafficLightDecorator northLight;
    private TrafficLightDecorator eastLight;
    private TrafficLightDecorator southLight;
    private TrafficLightDecorator westLight;

    private Group root;
    private Scene scene;
    private Circle northCircle;
    private Circle eastCircle;
    private Circle southCircle;
    private Circle westCircle;

    public IntersectionDecorator(Intersection intersection) {
        this.intersection = intersection;
        root = new Group();
        scene = new Scene(root, 100, 100, Color.BEIGE);
        StackPane sp = new StackPane();
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(100,100);
        gridPane.setAlignment(Pos.CENTER);
        if(intersection.getNorthTrafficLight() != null) {
            northLight = new TrafficLightDecorator(intersection.getNorthTrafficLight());
            northCircle = northLight.drawLight(50,15,10);
//            root.getChildren().add(northCircle);
            gridPane.add(northCircle,1,0);
        }
        if(intersection.getEastTrafficLight() != null) {
            eastLight = new TrafficLightDecorator(intersection.getEastTrafficLight());
            eastCircle = eastLight.drawLight(15,50,10);
//            root.getChildren().add(eastCircle);
            gridPane.add(eastCircle,2,1);
        }
        if(intersection.getSouthTrafficLight() != null) {
            southLight = new TrafficLightDecorator(intersection.getSouthTrafficLight());
            southCircle = southLight.drawLight(50,85,10);
//            root.getChildren().add(southCircle);
            gridPane.add(southCircle,1,2);
        }
        if(intersection.getWestTrafficLight() != null) {
            westLight = new TrafficLightDecorator(intersection.getWestTrafficLight());
            westCircle = westLight.drawLight(85,50,10);
//            root.getChildren().add(westCircle);
            gridPane.add(westCircle,0,1);
        }
        root.getChildren().add(gridPane);
    }

    public void showIntersection(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

}
