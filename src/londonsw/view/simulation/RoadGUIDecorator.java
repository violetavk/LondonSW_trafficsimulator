package londonsw.view.simulation;

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

import java.util.ArrayList;

/**
 * Created by felix on 25/02/2016.
 */
public class RoadGUIDecorator extends RoadDecorator {
    public RoadGUIDecorator(Road decoratedRoad) {
        super(decoratedRoad);
    }

    private ResizeFactor resizeFactor;

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public StackPane drawRoad(MapDirection mapDirection) {
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

        if (mapDirection == MapDirection.EAST || mapDirection == MapDirection.WEST) {
            for (int i = 0; i < numberLanes * 2; i++) {
                if (i % 2 == 0) {

                    Lane l = lanes.get(j);

                    double lineStartX = 5;
                    double lineStartY = division * (i + 1);

                    double lineEndX = im.getWidth() - 15;
                    double lineEndY = division * (i + 1);

                    roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY);
                    roadLine.setStrokeWidth(2 * this.getResizeFactor().getResizeY()); //TODO avoid hardcode
                    roadLine.setStroke(Color.WHITE);
                    lines.getChildren().add(roadLine);

                    Polygon arrow = new Polygon();

                    arrow.getPoints().addAll(new Double[]{
                            0.0, 5.0,
                            -5.0, -5.0,
                            5.0, -5.0});

                    arrow.setFill(Color.WHITE);

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

                    lines.getChildren().add(arrow);

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
                    double lineEndY = im.getHeight() - 15;

                    roadLine = new Line(lineStartX, lineStartY, lineEndX, lineEndY); //TODO avoid hardcode
                    roadLine.setStrokeWidth(2 * this.getResizeFactor().getResizeY()); //TODO avoid hardcode
                    roadLine.setStroke(Color.WHITE);

                    lines.getChildren().add(roadLine);

                    Polygon arrow = new Polygon();

                    arrow.getPoints().addAll(new Double[]{
                            0.0, 5.0,
                            -5.0, -5.0,
                            5.0, -5.0});

                    arrow.setFill(Color.WHITE);

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

        stackPane.getChildren().add(iv);
        stackPane.getChildren().add(lines);

        return stackPane;
    }
}