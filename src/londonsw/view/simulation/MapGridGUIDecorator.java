package londonsw.view.simulation;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.*;

/**
 * Created by felix on 25/02/2016.
 */
public class MapGridGUIDecorator extends MapGridDecorator {

    private ResizeFactor resizeFactor;

    public MapGridGUIDecorator(MapGrid decoratedMapGrid) {
        super(decoratedMapGrid);
    }

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public GridPane drawComponents() throws Exception {
        GridPane rootGP = new GridPane();
        Pane roadStackPane;

        int roadCounter = 0;

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {

                Component current = this.getGrid()[y][x];

                if (current instanceof Road) {


                    RoadGUIDecorator roadGUIDecorator = new RoadGUIDecorator((Road) current);

                    roadGUIDecorator.setResizeFactor(this.getResizeFactor());

                    roadStackPane = roadGUIDecorator.drawRoad();
                    roadGUIDecorator.setCell(roadCounter);
                    roadGUIDecorator.setPane(roadStackPane);
                    roadGUIDecorator.setGridPaneCoordinates(new Coordinate(x,y));

                    roadCounter++;

                } else if (current instanceof Intersection) {

                    roadCounter = 0;

                    IntersectionDecorator intersectionDecorator = new IntersectionDecorator((Intersection) current);
                    intersectionDecorator.setResizeFactor(this.getResizeFactor());
                    roadStackPane = intersectionDecorator.drawIntersection();
                } else {

                    roadCounter = 0;

                    LayoutGUI grassGUI = new LayoutGUI();

                    grassGUI.setHeight(this.getHeight());
                    grassGUI.setWidth(this.getWidth());
                    grassGUI.setResizeFactor(this.getResizeFactor());

                    roadStackPane = grassGUI.drawGrass();
                }

                rootGP.add(roadStackPane, x, y);

            }
        }

        rootGP.setGridLinesVisible(true);

        return rootGP;
    }
}