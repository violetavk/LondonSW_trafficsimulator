package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.*;

/**
 * Created by felix on 20/02/2016.
 */
public class MapGridGUI extends MapGrid {

    /**
     * Creates a brand new MapGrid instance to be part of a Map
     *
     * @param width  width of the Map that this will be part of
     * @param height height of the Map that this will be part of
     */
    public MapGridGUI(int width, int height) {
        super(width, height);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    private GridPane gridPane;

    public void drawInitialLayout() throws Exception {

        GridPane rootGP = new GridPane();

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){

                Image im  = new Image("Grass.png");
                ImageView iv = new ImageView(im);

                //change image size

                //iv.setImage(im);
                //iv.setFitWidth(this.getWidth()*7);
                //iv.setFitHeight(this.getHeight()*7);
                //iv.setPreserveRatio(true);
                //iv.setSmooth(true);
                //iv.setCache(true);

                // Iterate the Index using the loops
                rootGP.setRowIndex(iv,y);
                rootGP.setColumnIndex(iv,x);
                rootGP.getChildren().add(iv);
            }
        }

        rootGP.setGridLinesVisible(true);
        this.setGridPane(rootGP);
    }

    public void drawComponents()
    {
        GridPane rootGP = new GridPane();

        Image im  = new Image("Grass.png");

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){

                Component[][] c = this.getGrid();

                Component current = this.getGrid()[y][x];
                if(current instanceof Road)
                {
                    if (((Road) current).runsVertically())
                        im = new Image("RoadNS.png");
                    else
                        im = new Image("RoadEW.png");
                }
                else if(current instanceof Intersection)
                {
                    im = new Image("Line.png"); //TODO change image to a proper intersection image
                }
                else
                    im = new Image("Grass.png");

                ImageView iv = new ImageView(im);

                // Iterate the Index using the loops
                rootGP.setRowIndex(iv,y);
                rootGP.setColumnIndex(iv,x);

                rootGP.getChildren().add(iv);
            }
        }

        rootGP.setGridLinesVisible(true);

        this.setGridPane(rootGP);

    }
}