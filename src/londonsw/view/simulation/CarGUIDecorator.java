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

    private Rectangle rectangle;
    private GridPane gridPane;
    private ResizeFactor resizeFactor;

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public ResizeFactor getResizeFactor()
    {
        return  resizeFactor;
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

        //TODO: Change for different moving directions

        Lane lane = decoratedCar.getCurrentLane();

        //TODO: get Coordinates of current location

        Node n = getNodeFromGridPane(gp, lane.getEntry().getY(), decoratedCar.getCurrentCell());

        StackPane sp = (StackPane) n;

        double x =sp.getChildren().get(0).getLayoutBounds().getMaxX();
        double y =sp.getChildren().get(0).getLayoutBounds().getMaxX();

        Rectangle r = new Rectangle(
                x*decoratedCar.getCurrentCell(),
                y*decoratedCar.getCurrentLane().getEntry().getY(),
                50* this.getResizeFactor().getResizeX(),    //TODO: hardcode
                25*this.getResizeFactor().getResizeY());    //TODO: hardcode

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
        timeline.setDelay(Duration.millis(Ticker.getTickInterval()));

        /*
        Platform.runLater(() -> {
            final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), (this.getResizeFactor().getResizeX()) * (step + 1));
            final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

            timeline.getKeyFrames().add(kf);
            timeline.play();
        });*/
            //final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), (this.getResizeFactor().getResizeX()) * (step + 1));

        final KeyValue kv = new KeyValue(this.getRectangle().xProperty(),100 * (step +1));

            final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }
