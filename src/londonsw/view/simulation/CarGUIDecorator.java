package londonsw.view.simulation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Car;

import java.util.ArrayList;

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

    public void drawCar() {

        double x = 100 * this.getResizeFactor().getResizeX();
        double y = 100 * this.getResizeFactor().getResizeY();

        int numberLanes = this.decoratedCar.currentLane.getRoad().getNumberLanes();

        double division =   100 * this.getResizeFactor().getResizeX();
        //TODO I know it's 100 because the image background of the row is 100px plz try to put somewhere

        division = division / (numberLanes);

        double startPointX =
                x
                * decoratedCar.getCurrentCoordinate().getX()
                + (this.decoratedCar.getCurrentLane().getRoadIndex() * division);

        double startPointY = y
                * decoratedCar.getCurrentCoordinate().getY()
                + (this.decoratedCar.getCurrentLane().getRoadIndex() * division);

        Rectangle r = new Rectangle(
                startPointX,
                startPointY,
                50 * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                25 * this.getResizeFactor().getResizeY());    //TODO: hardcode

        r.setFill(Color.RED);

        //check map direction for rotation

        if (decoratedCar.getCurrentLane().getMovingDirection() == MapDirection.NORTH || decoratedCar.getCurrentLane().getMovingDirection() == MapDirection.SOUTH) {
            //rotate
            int angle = 90;

            r.setRotate(angle);
        }


        this.setRectangle(r);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void moveVehicleGUI(int step, int state) {
        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //set delay to show starting point
        //move according to moving direction

        if(state==0)
            timeline.stop();
        else {
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
}
