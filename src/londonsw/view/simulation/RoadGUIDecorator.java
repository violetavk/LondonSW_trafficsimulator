package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import londonsw.model.simulation.components.*;
import java.util.ArrayList;

/**
 * Created by felix on 25/02/2016.
 */
public class RoadGUIDecorator extends RoadDecorator {
    public RoadGUIDecorator(Road decoratedRoad) {
        super(decoratedRoad);
    }

    private ResizeFactor resizeFactor;
    private Coordinate gridPaneCoordinates;

    public Pane getPane() {
        return paneRoad;
    }

    public void setPane(Pane paneRoad) {
        this.paneRoad = paneRoad;
    }

    private Pane paneRoad;

    public int getCell() {
        return Cell;
    }

    public void setCell(int cell) {
        Cell = cell;
    }

    public Coordinate getGridPaneCoordinates() {
        return gridPaneCoordinates;
    }

    public void setGridPaneCoordinates(Coordinate gridPaneCoordinates) {
        gridPaneCoordinates = gridPaneCoordinates;
    }

    private int Cell;

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public StackPane drawRoad() {

        String roadBackgroundPath = "RoadBackground.png";   //TODO avoid hardcode

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