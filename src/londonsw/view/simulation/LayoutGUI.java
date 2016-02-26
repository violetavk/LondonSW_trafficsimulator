package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.Road;

/**
 * Created by felix on 23/02/2016.
 */
public class LayoutGUI {

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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public StackPane drawIntersection()
    {
        return drawLayout("Line.png");  //TODO hardcode
    }

    public StackPane drawGrass()
    {
        return drawLayout("Grass.png"); //TODO hardcode
    }

    private StackPane drawLayout(String path)
    {
        Image image = new Image(path);

        Image im  = new Image(path,image.getWidth()*this.getResizeFactorX(),image.getHeight()*this.getResizeFactorY(),false,false);
        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(iv);

        return stackPane;
    }
}