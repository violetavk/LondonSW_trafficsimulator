package londonsw.view.simulation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Car;

/**
 * Created by felix on 26/02/2016.
 */
public class CarGUIDecorator extends CarDecorator {

    public CarGUIDecorator(Car decoratedCar) {
        super(decoratedCar);
    }

    private double resizeFactorX;
    private double resizeFactorY;
    private double xSize;
    private double ySize;
    private Rectangle rectangle;
    private GridPane gridPane;

    public void setResizeFactor(double resizeFactorX, double resizeFactorY) {
        this.resizeFactorX = resizeFactorX;
        this.resizeFactorY = resizeFactorY;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Pane drawCar(Coordinate start) {

        Pane pane = new Pane();

        GridPane gp = this.getGridPane();

        Node n = getNodeFromGridPane(gp, start.getY(), start.getX());

        StackPane sp = (StackPane) n;

        double x =sp.getChildren().get(0).getLayoutBounds().getMaxX();
        double y =sp.getChildren().get(0).getLayoutBounds().getMaxX();

        this.setxSize(x);
        this.setySize(y);

        Rectangle r = new Rectangle(x*start.getX(),  y*start.getY(), 50*resizeFactorX, 25*resizeFactorY);   //TODO hardcode

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

    public void moveVehicle(int step)
    {
        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //set delay to show starting point
        timeline.setDelay(Duration.millis(1000));

        final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), (this.getxSize())*(step+1));
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}