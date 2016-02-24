package londonsw.view.simulation;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;

import java.lang.reflect.Method;

/**
 * Created by felix on 21/02/2016.
 */
public class CarGUI extends Vehicle {

    public double getResizeFactor() {
        return resizeFactorX;
    }

    public void setResizeFactor(int resizeFactorX, int resizeFactorY) {
        this.resizeFactorX = 9.0/resizeFactorX;
        this.resizeFactorY = 9.0/resizeFactorY;
    }

    private double resizeFactorX;
    private double resizeFactorY;
    private double xSize;

    public double getySize() {
        return ySize;
    }

    public void setySize(double ySize) {
        this.ySize = ySize;
    }

    public double getxSize() {
        return xSize;
    }

    public void setxSize(double xSize) {
        this.xSize = xSize;
    }

    private double ySize;

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

    public Pane drawCar(Coordinate start) {

        Pane pane = new Pane();

        GridPane gp = this.getGridPane();

        Node n = getNodeFromGridPane(gp, start.getY(), start.getX());

        StackPane spn = (StackPane) n;

        double x =spn.getChildren().get(0).getLayoutBounds().getMaxX();
        double y =spn.getChildren().get(0).getLayoutBounds().getMaxX();

        this.setxSize(x);
        this.setySize(y);

        Rectangle r = new Rectangle(x*start.getX(),  y*start.getY(), 50*resizeFactorX, 25*resizeFactorY);

        r.setFill(Color.RED);

        pane.getChildren().add(r);

        this.setRectangle(r);

        return pane;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


    public void moveCar(int step)
    {
        this.moveVehicle(step);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //set delay to show starting point
        timeline.setDelay(Duration.millis(2000));

        final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), this.getxSize()*step);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}