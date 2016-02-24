package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.Road;

import java.awt.*;

/**
 * Created by felix on 23/02/2016.
 */
public class LayoutGUI {

    private int numberLanes;
    private int height;
    private int width;
    private double resizeFactorX;
    private double resizeFactorY;

    public double getResizeFactorX() {
        return resizeFactorX;
    }

    public double getResizeFactorY() {
        return resizeFactorY;
    }

    public void setResizeFactor(double resizeFactorX, double resizeFactorY) {
        this.resizeFactorX = resizeFactorX;
        this.resizeFactorY = resizeFactorY;
    }

    public LayoutGUI(Road road)
    {
        this.numberLanes = road.getNumberLanes();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LayoutGUI()
    {}

    public StackPane drawRoad(MapDirection mapDirection)
    {
        Image im  = new Image("RoadBackground.png",100*resizeFactorX,100*resizeFactorY,false,false);
        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        //draw amount of lines
        Pane lines = new Pane();

        if(mapDirection == MapDirection.EAST || mapDirection == MapDirection.EAST.WEST)
        {
            for(int i = 0; i < numberLanes; i++)
            {
                Line roadLine = new Line(0,im.getHeight()/(numberLanes-i+1),im.getWidth()-15,im.getHeight()/(numberLanes-i+1));
                roadLine.setStrokeWidth(5*resizeFactorY);
                roadLine.setStroke(Color.WHITE);

                lines.getChildren().add(roadLine);

                //stackPane.getChildren().add(yellowLine);
            }
        }
        else
            for(int i = 0; i < numberLanes; i++)
            {
                Line yellowLine = new Line(im.getWidth()/(numberLanes+i+1),0,im.getWidth()/(numberLanes+i+1),im.getHeight()-5);
                yellowLine.setStrokeWidth(5*resizeFactorY);
                yellowLine.setStroke(Color.YELLOW);
                //stackPane.getChildren().add(yellowLine);

                lines.getChildren().add(yellowLine);
            }

        stackPane.getChildren().add(iv);
        stackPane.getChildren().add(lines);

        return stackPane;
    }

    public StackPane drawIntersection()
    {
        return drawLayout("Line.png");
    }

    public StackPane drawGrass()
    {
        return drawLayout("Grass.png");
    }

    private StackPane drawLayout(String path)
    {
        Image im  = new Image(path,100*resizeFactorX,100*resizeFactorY,false,false);
        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(iv);

        return stackPane;
    }
}