package londonsw.view.simulation;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import londonsw.controller.TrafficLightController;
import londonsw.model.simulation.components.LightColour;
import londonsw.model.simulation.components.TrafficLight;

/**
 * Traffic Light GUI Logic
 */
public class TrafficLightDecorator {

    private TrafficLight thisLight;
    private static Circle circle;

    public TrafficLightDecorator(TrafficLight thisLight) {
        this.thisLight = thisLight;
        circle = new Circle();
        TrafficLightController.addKeyValuePair(thisLight, this);
    }

    public void setGUIColour(LightColour colour) {
        // probably not the best solution but it seems to work for now...
        Platform.runLater(() -> {
            switch (colour) {
                case RED:
                    System.out.println("Changing colour to red for " + thisLight);
                    circle.setFill(Color.RED);
                    break;

                case GREEN:
                    System.out.println("Changing colour to green for " + thisLight);
                    circle.setFill(Color.GREEN);
                    break;
            }
        });



//       EventStreams.ticks(Duration.ofMillis(Ticker.getTickInterval()*1))   //needs to be greater than Ticker.getTickInterval
//                .subscribe(
//                        tick -> {
//                            try {
//
//
//                                VehicleController.moveVehicle(C1, CarGUI, 1);
//                                VehicleController.moveVehicle(C2, CarGUI2, 1);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        });
    }

    public Circle drawLight(double x, double y, double r){
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);
        circle.setFill(Color.RED);
        return circle;
    }

}
