package londonsw.view.simulation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;

/**
 * Created by felix on 21/02/2016.
 */
public class CarGUI extends Vehicle {

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private Rectangle rectangle;

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    private GridPane gridPane;

    public CarGUI(int currentCell, Lane currentLane) {
        super(currentCell, currentLane);
    }

    public void drawCar(Coordinate start) {

        Pane pane = new Pane();

        Rectangle r = new Rectangle(0, 0, 80, 30);
        r.setFill(Color.RED);

        pane.getChildren().add(r);
        this.getGridPane().add(pane,start.getX(),start.getY());

        this.setRectangle(r);

    }

    public void moveCar(int step)
    {
        this.moveVehicle(step);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), 100 * step);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}