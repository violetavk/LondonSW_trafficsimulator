package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.ResizeFactor;

/**
 * Created by felix on 16/03/2016.
 */
public class LaneArrow extends Group {

    protected Lane lane;

    public LaneArrow(Lane lane, Line roadLine, ResizeFactor resizeFactor) {
        this.lane = lane;

        double arrowResizeFactor = resizeFactor.getResizeX() * 1.75;

        Polygon arrow = new Polygon();

        arrow.getPoints().addAll(
                0.0 * arrowResizeFactor, 5.0 * arrowResizeFactor,
                -5.0 * arrowResizeFactor, -5.0 * arrowResizeFactor,
                5.0 * arrowResizeFactor, -5.0 * arrowResizeFactor
        );


        roadLine.setStrokeWidth(2 * resizeFactor.getResizeY()); //TODO avoid hardcode

        roadLine.setStrokeWidth(2 * resizeFactor.getResizeY()); //TODO avoid hardcode

        if (lane.getState() == 1) {
            roadLine.setStroke(Color.WHITE);
            arrow.setFill(Color.WHITE);
        } else {
            //lane not enabled
            roadLine.setStroke(Color.RED);
            arrow.setFill(Color.RED);
        }

        double angle = 0.0;

        switch (lane.getMovingDirection())
        {
            case NORTH:
                angle = -90;
                arrow.setTranslateY(roadLine.getStartY());
                arrow.setTranslateX(roadLine.getStartX());
                break;
            case SOUTH:
                angle = 90;
                arrow.setTranslateY(roadLine.getEndY());
                arrow.setTranslateX(roadLine.getEndX());
                break;
            case EAST:
                angle = 0.0;
                arrow.setTranslateY(roadLine.getEndY());
                arrow.setTranslateX(roadLine.getEndX());
                break;
            case WEST:
                angle = 180;
                arrow.setTranslateY(roadLine.getStartY());
                arrow.setTranslateX(roadLine.getStartX());
                break;
        }

        arrow.setRotate(angle - 90);

        this.getChildren().addAll(roadLine,arrow);

    }
}