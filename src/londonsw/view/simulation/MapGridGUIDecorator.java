package londonsw.view.simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import londonsw.controller.MapMakerController;
import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.*;
import londonsw.view.mapcreation.ComponentType;

import java.util.ArrayList;

/**
 * This is the class that handles drawing of the entire map. It cycles through the Model grid and draws
 * each map component on the screen.
 */
public class MapGridGUIDecorator extends MapGridDecorator {

    private ResizeFactor resizeFactor;

    /**
     * Creates an instance of this decorator to represent a single Map
     * @param decoratedMapGrid the MapGrid (underlying Map data structure) to represent graphically
     */
    public MapGridGUIDecorator(MapGrid decoratedMapGrid) {
        super(decoratedMapGrid);
    }

    /**
     * Gets the resize factor for this decorator
     * @return the resize factor for this decorator
     */
    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    /**
     * Sets the resize factor for this decorator
     * @param resizeFactor the resize factor to set for this decorator
     */
    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    /**
     * Draws the components for this Map
     * @return a GridPane representing the map, where every cell in the GridPane corresponds to a coordinate in the Map
     * @throws Exception
     */
    public GridPane drawComponents() throws Exception {
        GridPane rootGP = new GridPane();
        StackPane roadPane;

        int roadCounter = 0;

        ArrayList<RoadGUIDecorator> roadArray = new ArrayList<>();

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {

                Component current = this.getGrid()[y][x];

                if (current instanceof Road) { // draws a Road

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

                                    System.out.println("Lane ID: " + laneArrow.lane.getId() + " Lane State: " + laneArrow.lane.getState());
                                    laneArrow.lane.setState(laneArrow.lane.getState() == 0 ? 1 : 0);

                                }
                            }
                    );

                    roadGUIDecorator.setCell(roadCounter);
                    roadGUIDecorator.setPane(roadPane);
                    roadGUIDecorator.setGridPaneCoordinates(new Coordinate(x, y));

                    roadArray.add(roadGUIDecorator);

                    roadCounter++;

                } else if (current instanceof Intersection) { // draws an Intersection
                    roadCounter = 0;

                    IntersectionDecorator intersectionDecorator = new IntersectionDecorator((Intersection) current);
                    intersectionDecorator.setResizeFactor(this.getResizeFactor());
                    roadPane = intersectionDecorator.drawIntersection();
                } else { // draws Grass
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

    /**
     * Redraws the cell based on what is currently in the cell. Used by MapMaker mode.
     * @param x the x coordinate of the new component to be redrawn
     * @param y the y coordinate of the new component to be redrawn
     * @param gp the GridPane representing the Map
     * @return a StackPane representation of the newly redrawn grid cell
     */
    public StackPane redrawCell(int x, int y, GridPane gp) {
        Component component = this.getGrid()[y][x];
        StackPane sp = new StackPane();
        if(component instanceof Intersection) {
            IntersectionDecorator intersectionDecorator = new IntersectionDecorator((Intersection) component);
            intersectionDecorator.setResizeFactor(this.getResizeFactor());
            sp = intersectionDecorator.drawIntersection();
        }
        else if(component instanceof Road) {
            RoadGUIDecorator roadGUIDecorator = new RoadGUIDecorator((Road) component);
            roadGUIDecorator.setResizeFactor(this.getResizeFactor());
            sp = roadGUIDecorator.drawRoad();
        }
        else if(component == null) { // Grass
            LayoutGUI grassGUI = new LayoutGUI();
            grassGUI.setHeight(this.getHeight());
            grassGUI.setWidth(this.getWidth());
            grassGUI.setResizeFactor(this.getResizeFactor());
            sp = grassGUI.drawGrass();
        }
        gp.add(sp, x, y);
        return sp;
    }
}
