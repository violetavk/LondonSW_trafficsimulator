package londonsw.view.simulation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Car;

/**
 * Created by felix on 26/02/2016.
 */
public class CarGUIDecorator extends CarDecorator {

    public CarGUIDecorator(Car decoratedCar) {
        super(decoratedCar);
    }

    private Rectangle rectangle;
    private GridPane gridPane;
    private ResizeFactor resizeFactor;

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
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

    public Pane drawCar() {

        Pane pane = new Pane();

        GridPane gp = this.getGridPane();

        Lane lane = decoratedCar.getCurrentLane();

        Node n = getNodeFromGridPane(gp, decoratedCar.getCurrentCoordinate().getY(),decoratedCar.getCurrentCoordinate().getX());

        StackPane sp = (StackPane) n;

        double x = sp.getChildren().get(0).getLayoutBounds().getMaxX();
        double y = sp.getChildren().get(0).getLayoutBounds().getMaxX();

        Rectangle r = new Rectangle(
                x * decoratedCar.getCurrentCoordinate().getX(),
                y * decoratedCar.getCurrentCoordinate().getY(),
                50 * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                25 * this.getResizeFactor().getResizeY());    //TODO: hardcode

        r.setFill(Color.RED);

        //check map direction for rotation

        if (decoratedCar.getCurrentLane().getMovingDirection() == MapDirection.NORTH || decoratedCar.getCurrentLane().getMovingDirection() == MapDirection.SOUTH) {
            //rotate
            int angle = 90;

            r.setRotate(angle);
        }

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

    public void moveVehicleGUI(int step) {
        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //set delay to show starting point
        timeline.setDelay(Duration.millis(Ticker.getTickInterval()));

        //move according to moving direction

        DoubleProperty doubleProperty = null;
        double distance = 0;
        double imageDimension = 100 * this.getResizeFactor().getResizeX();  //TODO: hardcode

        switch (decoratedCar.getCurrentLane().getMovingDirection()) {

            case NORTH:
                doubleProperty = this.getRectangle().yProperty();
                distance = doubleProperty.getValue() - (imageDimension) * step;

                break;
            case SOUTH:
                doubleProperty = this.getRectangle().yProperty();
                distance = doubleProperty.getValue() + (imageDimension) * step;

                break;
            case EAST:
                doubleProperty = this.getRectangle().xProperty();
                distance = doubleProperty.getValue() + (imageDimension) * step;

                break;
            case WEST:
                doubleProperty = this.getRectangle().xProperty();
                distance = doubleProperty.getValue() - (imageDimension) * step;

                break;

        }

        final KeyValue kv = new KeyValue(doubleProperty,
                distance);
        final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
