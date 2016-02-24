package londonsw.view.simulation;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.Vehicle;

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

        //GridPane gp = this.getGridPane();

        //Node n = getNodeByRowColumnIndex(start.getY(), start.getX(), this.getGridPane());

        //Rectangle r = new Rectangle(n.getLayoutBounds().getMinX(), n.getLayoutBounds().getMinY(), 50*resizeFactorX, 25*resizeFactorY);

        Rectangle r = new Rectangle(45, 45, 50*resizeFactorX, 25*resizeFactorY);

        r.setFill(Color.RED);

        pane.getChildren().add(r);

        //this.getGridPane().add(pane,start.getX(),start.getY());

        this.setRectangle(r);

        return pane;
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }


    public void moveCar(int step)
    {
        this.moveVehicle(step);

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //set delay to show starting point
        timeline.setDelay(Duration.millis(2000));

        final KeyValue kv = new KeyValue(this.getRectangle().xProperty(), 100*step);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}