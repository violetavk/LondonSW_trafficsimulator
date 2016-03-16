package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.*;

import java.util.ArrayList;

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
        StackPane roadPane;

        int roadCounter = 0;

        ArrayList<RoadGUIDecorator> roadArray = new ArrayList<>();

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {

                Component current = this.getGrid()[y][x];

                if (current instanceof Road) {

                    RoadGUIDecorator roadGUIDecorator = new RoadGUIDecorator((Road) current);

                    roadGUIDecorator.setResizeFactor(this.getResizeFactor());

                    roadPane = roadGUIDecorator.drawRoad();

                    roadPane.getChildren().get(1).setOnMouseClicked(event->
                            {
                                for (RoadGUIDecorator rd: roadArray
                                        ) {

                                    if(rd.decoratedRoad.getRoadId() == roadGUIDecorator.decoratedRoad.getRoadId()) {

                                        Node nGroup = rd.getPane().getChildren().get(1);

                                        Group group = (Group) nGroup;

                                        Node nArrow = group.getChildren().get(1);

                                        LaneArrow arrow = (LaneArrow) nArrow;

                                        arrow.setFill(Color.RED);

                                        System.out.println(arrow.getLaneId());

                                        //n.ge

                                    }

                                }
                            }
                    );

                    roadGUIDecorator.setCell(roadCounter);
                    roadGUIDecorator.setPane(roadPane);
                    roadGUIDecorator.setGridPaneCoordinates(new Coordinate(x,y));

                    roadArray.add(roadGUIDecorator);

                    roadCounter++;

                } else if (current instanceof Intersection) {

                    roadCounter = 0;

                    IntersectionDecorator intersectionDecorator = new IntersectionDecorator((Intersection) current);
                    intersectionDecorator.setResizeFactor(this.getResizeFactor());
                    roadPane = intersectionDecorator.drawIntersection();
                } else {

                    roadCounter = 0;

                    LayoutGUI grassGUI = new LayoutGUI();

                    grassGUI.setHeight(this.getHeight());
                    grassGUI.setWidth(this.getWidth());
                    grassGUI.setResizeFactor(this.getResizeFactor());

                    roadPane = grassGUI.drawGrass();
                }

                rootGP.add(roadPane, x, y);

            }
        }

        rootGP.setGridLinesVisible(true);

        return rootGP;
    }
}
