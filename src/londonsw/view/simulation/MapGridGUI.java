package londonsw.view.simulation;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.Component;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.Road;

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

    public void drawInitialLayout(Stage primaryStage)
    {
        GridPane root = new GridPane();

        for(int y = 0; y < this.getHeight(); y++){
            for(int x = 0; x < this.getWidth(); x++){

                Image im  = new Image("Grass.png");
                ImageView iv = new ImageView(im);

                // Iterate the Index using the loops
                root.setRowIndex(iv,y);
                root.setColumnIndex(iv,x);

                root.getChildren().add(iv);
            }
        }

        Scene scene = new Scene(root, 500, 500);
        //Scene scene = new Scene(root, 3200, 1800);
        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawComponents(Stage primaryStage)
    {
        GridPane root = new GridPane();
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
                root.setRowIndex(iv,y);
                root.setColumnIndex(iv,x);

                root.getChildren().add(iv);
            }
        }

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
