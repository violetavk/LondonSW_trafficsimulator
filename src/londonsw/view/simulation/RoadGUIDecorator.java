package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import londonsw.model.simulation.components.*;
import java.util.ArrayList;

/**
 * This class defines how roads are drawn and displayed in the view. Each Road instance will
 * have exactly one of these decorators associated with it.
 */
public class RoadGUIDecorator extends RoadDecorator {

    private ResizeFactor resizeFactor;
    private Coordinate gridPaneCoordinates;
    private Pane paneRoad;
    private int Cell;

    /**
     * Creates a new instance of this decorator class for the given Road instance
     * @param decoratedRoad the Road instance to associate this decorator with
     */
    public RoadGUIDecorator(Road decoratedRoad) {
        super(decoratedRoad);
    }

    /**
     * Gets the pane associated with this decorator
     * @return the pane for this decorator
     */
    public Pane getPane() {
        return paneRoad;
    }

    /**
     * Sets the pane for this decorator
     * @param paneRoad the pane to set for this decorator
     */
    public void setPane(Pane paneRoad) {
        this.paneRoad = paneRoad;
    }

    /**
     * Gets the cell for this road
     * @return the cell
     */
    public int getCell() {
        return Cell;
    }

    /**
     * Sets the cell for this road
     * @param cell the cell for this road
     */
    public void setCell(int cell) {
        Cell = cell;
    }

    /**
     * Gets the grid pane coordinate of this road
     * @return the coordinate for this road
     */
    public Coordinate getGridPaneCoordinates() {
        return gridPaneCoordinates;
    }

    /**
     * Sets the grid pane coordinate for this road
     * @param gridPaneCoordinates the coordinate for this road
     */
    public void setGridPaneCoordinates(Coordinate gridPaneCoordinates) {
        gridPaneCoordinates = gridPaneCoordinates;
    }

    /**
     * Gets the resize factor for this road
     * @return the resize factor for this road
     */
    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    /**
     * Sets the resize factor for this road
     * @param resizeFactor the resize factor to set for this road
     */
    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    /**
     * This method draws the road and returns the StackPane representation of this road. Each cell has a road background
     * image. Each cell also contains an arrow that displays the moving direction of that cell.
     * @return the StackPane representation of this road cell
     */
    public StackPane drawRoad() {

        String roadBackgroundPath = "RoadBackground.png";

        Image image = new Image(roadBackgroundPath);

        Image im = new Image(roadBackgroundPath, image.getHeight() * getResizeFactor().getResizeX(), image.getWidth() * getResizeFactor().getResizeY(), false, false);

        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        //draw amount of lines
        Group arrowLines = new Group();

        int numberLanes = this.getNumberLanes();

        ArrayList<Lane> lanes = this.getLanes();

        double division = im.getHeight();

        division = division / (numberLanes * 2);

        LaneArrow arrow;

        int j = 0;

        if (!this.runsVertically(lanes.get(0).getMovingDirection())) {
            for (int i = 0; i < numberLanes * 2; i++) {
                if (i % 2 == 0) {

                    Lane lane = lanes.get(j);

                    double lineStartX = 5;
                    double lineStartY = division * (i + 1);

                    double lineEndX = im.getWidth() - 10;
                    double lineEndY = division * (i + 1);

                    Line roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY);

                    arrow = new LaneArrow(lane,roadLine,resizeFactor);

                    arrowLines.getChildren().addAll(arrow.getGroup());

                    j++;

                }
            }
        } else
            for (int i = 0; i < numberLanes * 2; i++) {

                if (i % 2 == 0) {

                    Lane lane = lanes.get(j);

                    double lineStartX = division * (i + 1);
                    double lineStartY = 5;
                    double lineEndX = division * (i + 1);
                    double lineEndY = im.getHeight() - 10;

                    Line roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY);

                    arrow = new LaneArrow(lane,roadLine,resizeFactor);
                    arrowLines.getChildren().addAll(arrow.getGroup());

                    j++;

                }
            }

        stackPane.getChildren().add(iv);
        stackPane.getChildren().add(arrowLines);

        return stackPane;
    }
}