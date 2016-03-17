package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

                    roadPane.getChildren().get(1).setOnMouseClicked(event ->
                            {

                                if (event.getTarget() instanceof LaneArrow) {
                                    LaneArrow laneArrow = (LaneArrow) event.getTarget();

                                    for (RoadGUIDecorator rd : roadArray
                                            ) {


                                        if (rd.decoratedRoad.getId() == roadGUIDecorator.decoratedRoad.getId()) {

                                            Node nGroup = rd.getPane().getChildren().get(1);

                                            Group gRoad = (Group) nGroup;

                                            Group g = (Group) gRoad.getChildren().get(laneArrow.lane.getRoadIndex());

                                            Line lineArrow = (Line) g.getChildren().get(0);
                                            Polygon arrow = (Polygon) g.getChildren().get(1);

                                            lineArrow.setStroke(lineArrow.getStroke() == Color.RED ? Color.WHITE : Color.RED);
                                            arrow.setFill(arrow.getFill() == Color.RED ? Color.WHITE : Color.RED);

                                        }

                                    }

                                    System.out.println(laneArrow.lane.getId());
                                    laneArrow.lane.setState(laneArrow.lane.getState() == 0 ? 1 : 0);

                                }
                            }
                    );

                    roadGUIDecorator.setCell(roadCounter);
                    roadGUIDecorator.setPane(roadPane);
                    roadGUIDecorator.setGridPaneCoordinates(new Coordinate(x, y));

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
