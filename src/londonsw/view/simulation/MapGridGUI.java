package londonsw.view.simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    private double resizeFactorX;
    private double resizeFactorY;

    public MapGridGUI(int width, int height) {
        super(width, height);
        resizeFactorX = 9.0/width;
        resizeFactorY = 9.0/height;
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

                // Iterate the Index using the loops
                rootGP.setRowIndex(iv,y);
                rootGP.setColumnIndex(iv,x);
                rootGP.getChildren().add(iv);
            }
        }

        rootGP.setGridLinesVisible(true);
        this.setGridPane(rootGP);
    }

    public void drawComponents() throws Exception
    {
        GridPane rootGP = new GridPane();

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){

                Pane roadStackPane = new Pane();

                Component[][] c = this.getGrid();

                Component current = this.getGrid()[y][x];

                if(current instanceof Road) {

                    LayoutGUI roadGUI = new LayoutGUI((Road) current);

                    roadGUI.setHeight(this.getHeight());
                    roadGUI.setWidth(this.getWidth());
                    roadGUI.setResizeFactor(resizeFactorX,resizeFactorY);

                    roadStackPane = roadGUI.drawRoad(((Road) current).runsVertically()?MapDirection.NORTH:MapDirection.EAST);
                }
                else if(current instanceof Intersection)
                {
                    LayoutGUI intersectionGUI = new LayoutGUI();

                    intersectionGUI.setHeight(this.getHeight());
                    intersectionGUI.setWidth(this.getWidth());
                    intersectionGUI.setResizeFactor(resizeFactorX,resizeFactorY);

                    roadStackPane = intersectionGUI.drawIntersection();
                }
                else {

                    LayoutGUI grassGUI = new LayoutGUI();

                    grassGUI.setHeight(this.getHeight());
                    grassGUI.setWidth(this.getWidth());
                    grassGUI.setResizeFactor(resizeFactorX,resizeFactorY);

                    roadStackPane = grassGUI.drawGrass();
                }

                rootGP.setRowIndex(roadStackPane,y);
                rootGP.setColumnIndex(roadStackPane,x);

                rootGP.getChildren().add(roadStackPane);
            }
        }

        rootGP.setGridLinesVisible(true);

        this.setGridPane(rootGP);

    }

}