package londonsw.view.simulation;

import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import londonsw.model.simulation.components.*;

import java.beans.EventHandler;
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
        Pane lines = new Pane();

        int numberLanes = this.getNumberLanes();

        ArrayList<Lane> lanes = this.getLanes();

        double division = im.getHeight();

        division = division / (numberLanes * 2);

        Line roadLine;

        int j = 0;

        if (!this.runsVertically()) {
            for (int i = 0; i < numberLanes * 2; i++) {
                if (i % 2 == 0) {

                    Lane l = lanes.get(j);

                    double lineStartX = 5;
                    double lineStartY = division * (i + 1);

                    double lineEndX = im.getWidth() - 10;
                    double lineEndY = division * (i + 1);

                    roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY);
                    roadLine.setStrokeWidth(2 * this.getResizeFactor().getResizeY()); //TODO avoid hardcode

                    lines.getChildren().add(roadLine);

                    Polygon arrow = drawArrow();

                    if (l.getState() == 1) {
                        roadLine.setStroke(Color.WHITE);
                        arrow.setFill(Color.WHITE);
                    } else {
                        //lane not enabled
                        roadLine.setStroke(Color.RED);
                        arrow.setFill(Color.RED);
                    }

                    double angle = 0.0;

                    if (l.getMovingDirection() == MapDirection.EAST) {
                        arrow.setTranslateY(lineEndY);
                        arrow.setTranslateX(lineEndX);
                    } else {
                        angle = 180;
                        arrow.setTranslateY(lineStartY);
                        arrow.setTranslateX(lineStartX);

                    }

                    arrow.setRotate(angle - 90);

                    //arrow.setOnMouseClicked(event-> repaintWithNewState(l,mapDirection));

                    lines.getChildren().add(arrow);

                    //lines.setOnMouseClicked(event -> System.out.println("Clicked " + l.getLaneID()));

                    j++;

                }
            }
        } else
            for (int i = 0; i < numberLanes * 2; i++) {

                if (i % 2 == 0) {

                    Lane l = lanes.get(j);

                    double lineStartX = division * (i + 1);
                    double lineStartY = 5;
                    double lineEndX = division * (i + 1);
                    double lineEndY = im.getHeight() - 10;

                    roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY);
                    roadLine.setStrokeWidth(2 * this.getResizeFactor().getResizeY()); //TODO avoid hardcode

                    lines.getChildren().add(roadLine);

                    Polygon arrow = drawArrow();

                    if (l.getState() == 1) {
                        arrow.setFill(Color.WHITE);
                        roadLine.setStroke(Color.WHITE);
                    } else {
                        //lane not enabled
                        arrow.setFill(Color.RED);
                        roadLine.setStroke(Color.RED);
                    }

                    double angle = 0.0;

                    if (l.getMovingDirection() == MapDirection.NORTH) {

                        angle = -90;
                        arrow.setTranslateY(lineStartY);
                        arrow.setTranslateX(lineStartX);
                    } else {

                        angle = 90;
                        arrow.setTranslateY(lineEndY);
                        arrow.setTranslateX(lineEndX);
                    }

                    arrow.setRotate(angle - 90);

                    lines.getChildren().add(arrow);

                    j++;

                }
            }

        stackPane.setOnMouseClicked(event -> System.out.println("Clicked " + this.getRoadId() + " Cell: " + this.getCell()));

        stackPane.getChildren().add(iv);
        stackPane.getChildren().add(lines);


        return stackPane;
    }

    private void repaintWithNewState(Lane l, MapDirection mapDirection) {

        System.out.println(l.getLaneID());
        l.setState(l.getState()==0?1:0);

    }

    private void paintArrows(Polygon arrow, Lane lane) {

        lane.setState(0);
        arrow.setFill(Color.RED);

    }

    public Polygon drawArrow() {
        Polygon arrow = new Polygon();

        double arrowResizeFactor = resizeFactor.getResizeX() * 1.75;
        arrow.getPoints().addAll(
                0.0 * arrowResizeFactor, 5.0 * arrowResizeFactor,
                -5.0 * arrowResizeFactor, -5.0 * arrowResizeFactor,
                5.0 * arrowResizeFactor, -5.0 * arrowResizeFactor
        );

        return arrow;
    }
}