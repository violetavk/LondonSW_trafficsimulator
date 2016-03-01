package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import londonsw.model.simulation.components.ResizeFactor;

/**
 * Created by felix on 23/02/2016.
 */
public class LayoutGUI {

    private int height;
    private int width;
    private ResizeFactor resizeFactor;

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
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

        Image im  = new Image(path,image.getWidth()*this.getResizeFactor().getResizeX(),image.getHeight()*this.getResizeFactor().getResizeY(),false,false);

        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(iv);

        return stackPane;
    }
}