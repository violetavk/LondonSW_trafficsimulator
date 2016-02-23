package londonsw.view.simulation;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import londonsw.model.simulation.components.LightColour;

public class TrafficLightGUI {
    private static Circle circle;

    public static void setGUIColour(LightColour colour) {
        // probably not the best solution but it seems to work for now...
        Platform.runLater(new Runnable()  {
            @Override
            public void run() {
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
            }
        });
    }
    public Circle DrawLight(){
        Circle circle = new Circle();
        circle = new Circle();
        circle.setCenterX(175.0f);
        circle.setCenterY(120.0f);
        circle.setRadius(25.0f);
        circle.setFill(Color.RED);
        return circle;
    }

}
