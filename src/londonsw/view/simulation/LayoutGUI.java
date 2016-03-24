package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import londonsw.model.simulation.components.ResizeFactor;

/**
 * This class provides the basis for drawing map components to the GUI screen
 */
public class LayoutGUI {

    private int height;
    private int width;
    private ResizeFactor resizeFactor;

    /**
     * Gets the resize factor for the GUI square
     * @return the resize factor for this square
     */
    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    /**
     * Sets the resize factor for this GUI square
     * @param resizeFactor the resize factor to set for this square
     */
    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    /**
     * Sets the width of this GUI square
     * @param width the width to set for this GUI square
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the height of this GUI square
     * @param height the height to set for this GUI square
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * This method gets called to draw a Grass square in the map, i.e. when there is no other map component there,
     * it will look like grass
     * @return the StackPane representing grass
     */
    public StackPane drawGrass() {
        return drawLayout("Grass.png");
    }

    /**
     * The generic function that gets called by any map component drawing function. It draws the map component given
     * a path to an image.
     * @param path the path to the image for this GUI square
     * @return the StackPane representing this GUI square, based on the provided image
     */
    private StackPane drawLayout(String path) {
        Image image = new Image(path);

        Image im  = new Image(path,image.getWidth()*this.getResizeFactor().getResizeX(),image.getHeight()*this.getResizeFactor().getResizeY(),false,false);

        ImageView iv = new ImageView(im);

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(iv);

        return stackPane;
    }
}