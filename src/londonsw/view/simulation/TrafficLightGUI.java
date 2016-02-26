package londonsw.view.simulation;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import londonsw.model.simulation.components.LightColour;
/**
 * Traffic Light GUI Logic
 */
public class TrafficLightGUI {
    private static Circle circle;

    public static void setGUIColour(LightColour colour, Circle circle) {
        // probably not the best solution but it seems to work for now...
        Platform.runLater(() -> {
            switch (colour) {
                case RED:
                    System.out.println("Changing colour to red");
                    circle.setFill(Color.RED);
                    break;

                case GREEN:
                    System.out.println("Changing colour to green");
                    circle.setFill(Color.GREEN);
                    break;
            }
        });
    }

    public Circle DrawLight(double x, double y, double r){

        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);
        circle.setFill(Color.RED);
        return circle;
    }
}
