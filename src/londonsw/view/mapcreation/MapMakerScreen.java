package londonsw.view.mapcreation;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import londonsw.controller.MapMakerController;
import londonsw.model.simulation.Map;
import londonsw.model.simulation.components.*;
import londonsw.view.simulation.MapGridGUIDecorator;

import java.util.Optional;


@SuppressWarnings("Duplicates")
public class MapMakerScreen {

    private int width;
    private int height;

    private ImageView intersectionImgView;
    private ImageView roadNSImgView;
    private ImageView roadEWImgView;
    private ImageView grassImgView;


    public MapMakerScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawScreen(Stage primaryStage) throws Exception {
        // Create the base BorderPane for the whole window
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: papayawhip");

        // Add some instructions to the user
        String text = "Instructions:\n" +
                "1. Click on the map component that you would like to place in the map\n" +
                "2. Click on the place in the map where you want to place the component\n" +
                "3. Repeat until you built the map you want!\n" +
                "4. Hit the 'Save' button when you are done";
        Label instructions = new Label(text);
        instructions.setFont(Font.font("Arial", FontWeight.BOLD,12));
        instructions.setPadding(new Insets(5, 5, 5, 5));
        borderPane.setTop(instructions);

        // Create the blank Map
        Pane mapPane = new Pane();
        Map map = new Map(width, height);
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
//        ResizeFactor resizeFactor = ResizeFactor.getSuggestedResizeFactor(width,height);
        System.out.println("Using RF of " + .25);
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(.25,.25));
        GridPane mapGridPane = mapGridGUIDecorator.drawComponents();
        mapGridPane.setPadding(new Insets(0,0,5,5));
        mapPane.getChildren().add(mapGridPane);
        borderPane.setCenter(mapPane);
        MapMakerController.setCurrentFocused(ComponentType.NOTHING);

        VBox sideComponents = new VBox();
        GridPane sideComponents2 = new GridPane();

        /* Add "Components" label */
        Label componentsLabel = new Label("Components");
        componentsLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
        componentsLabel.setPadding(new Insets(15,5,0,20));
        sideComponents.getChildren().add(componentsLabel);

        /* Add Intersection square image */
        VBox intersectionPane = new VBox();
        Label intersectionLabel = new Label("Intersection");
        intersectionLabel.setPadding(new Insets(5,5,0,30));
        intersectionLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,12));
        Image intersectionImg = new Image("IntersectionX.png",60,60,true,false);
        intersectionImgView = new ImageView(intersectionImg);
        StackPane intersectionStackPane = new StackPane(intersectionImgView);
        intersectionStackPane.setPadding(new Insets(0,10,10,10));
        intersectionPane.getChildren().add(intersectionLabel);
        intersectionPane.getChildren().add(intersectionStackPane);
        sideComponents.getChildren().add(intersectionPane);

        /* Add RoadNS square image */
        VBox roadNSPane = new VBox();
        Label roadNSLabel = new Label("Road (North-South)");
        roadNSLabel.setPadding(new Insets(5,5,0,15));
        roadNSLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,12));
        Image roadNSImg = new Image("RoadBackgroundNS.png",60,60,true,false);
        roadNSImgView = new ImageView(roadNSImg);
        StackPane roadNSStackPane = new StackPane(roadNSImgView);
        roadNSStackPane.setPadding(new Insets(0,10,10,10));
        roadNSPane.getChildren().add(roadNSLabel);
        roadNSPane.getChildren().add(roadNSStackPane);
        sideComponents.getChildren().add(roadNSPane);

        /* Add RoadEW square image */
        VBox roadEWPane = new VBox();
        Label roadEWLabel = new Label("Road (East-West)");
        roadEWLabel.setPadding(new Insets(5,5,0,15));
        roadEWLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,12));
        Image roadEWImg = new Image("RoadBackgroundEW.png",60,60,true,false);
        roadEWImgView = new ImageView(roadEWImg);
        StackPane roadEWStackPane = new StackPane(roadEWImgView);
        roadEWStackPane.setPadding(new Insets(0,10,10,10));
        roadEWPane.getChildren().add(roadEWLabel);
        roadEWPane.getChildren().add(roadEWStackPane);
        sideComponents.getChildren().add(roadEWPane);

        /* Add Grass square image to empty out cells */
        VBox grassPane = new VBox();
        Label grassLabel = new Label("Grass (clear square)");
        grassLabel.setPadding(new Insets(5,5,0,15));
        grassLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,12));
        Image grassImg = new Image("Grass.png",60,60,true,false);
        grassImgView = new ImageView(grassImg);
        StackPane grassStackPane = new StackPane(grassImgView);
        grassStackPane.setPadding(new Insets(0,10,10,10));
        grassPane.getChildren().add(grassLabel);
        grassPane.getChildren().add(grassStackPane);
        sideComponents.getChildren().add(grassPane);

        /* Add Save, Reset buttons */
        VBox buttonsPane = new VBox();
        buttonsPane.setPadding(new Insets(0,0,0,20));
        Label toolsLabel = new Label("Tools");
        toolsLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
        toolsLabel.setPadding(new Insets(15,5,5,0));
        buttonsPane.getChildren().add(toolsLabel);
        Button saveButton = new Button("Save Map");
        buttonsPane.getChildren().add(saveButton);
        Button resetButton = new Button("Reset Map");
        buttonsPane.getChildren().add(resetButton);

        sideComponents.getChildren().add(buttonsPane);


        /* Add click processing for Map grid squares */
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Node current = getNodeFromIndex(i, j, mapGridPane);
                final int x = j;
                final int y = i;
                current.setOnMouseClicked((MouseEvent click) -> {
                    MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
                    MapMakerController.setCurrentFocused(ComponentType.MAP_SQUARE);
                    current.requestFocus();
                });
                current.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    ComponentType previous = MapMakerController.getPreviousFocused();
                    if(previous == ComponentType.INTERSECTION) {
                        addIntersection(x,y,map,mapGridGUIDecorator,mapGridPane,intersectionImgView);
                    }
                    else if(previous == ComponentType.ROADNS) {
                        addRoadNS(x,y,map,mapGridGUIDecorator,mapGridPane,roadNSImgView);
                    }
                    else if(previous == ComponentType.ROADEW) {
                        addRoadEW(x,y,map,mapGridGUIDecorator,mapGridPane,roadEWImgView);
                    }
                    else if(previous == ComponentType.GRASS) {
                        addGrass(x,y,map,mapGridGUIDecorator,mapGridPane,grassImgView);
                    }
                });
            }
        }

        /* Add intersection icon click processing */
        DropShadow ds = new DropShadow(15, Color.BLUE);
        intersectionImgView.setOnMouseClicked(click -> {
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.INTERSECTION);
            intersectionImgView.requestFocus();
        });
        intersectionImgView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue)
                intersectionImgView.setEffect(ds);
            else
                intersectionImgView.setEffect(null);
        });

        /* Add roadNS icon click processing */
        roadNSImgView.setOnMouseClicked(click -> {
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.ROADNS);
            roadNSImgView.requestFocus();
        });
        roadNSImgView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue)
                roadNSImgView.setEffect(ds);
            else
                roadNSImgView.setEffect(null);
        });

        /* Add roadEW icon click processing */
        roadEWImgView.setOnMouseClicked(click -> {
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.ROADEW);
            roadEWImgView.requestFocus();
        });
        roadEWImgView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue)
                roadEWImgView.setEffect(ds);
            else
                roadEWImgView.setEffect(null);
        });

        /* Add grass icon click processing */
        grassImgView.setOnMouseClicked(click -> {
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.GRASS);
            grassImgView.requestFocus();
        });
        grassImgView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue)
                grassImgView.setEffect(ds);
            else
                grassImgView.setEffect(null);
        });

        /* Add save button functionality */
        saveButton.setOnMouseClicked(click -> {
            System.out.println("Clicked Save Map");
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Save Map");
            nameDialog.setHeaderText("Please provide a name for your map (no spaces or special characters).\nSaved maps go into the /maps directory of your working directory.");
            nameDialog.setContentText("File name");
            Optional<String> result = nameDialog.showAndWait();
            result.ifPresent(name -> {
                name = name.concat(".map");
                System.out.println("Chose the name " + name);
                Map finalMap = buildAndSaveMap(map);
            });
        });

        borderPane.setRight(sideComponents);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
    }

    private Map buildAndSaveMap(Map map) {
        /*
        Plan:
            Step 1:
                - If found intersection, add it to map as a new intersection
                - If found road, do a while loop going down or up while next is still instance of road, register the length
                and add to the new map with 2 lanes
            Step 2:
                - Go thru all the intersections and register the roads to it (northRoad,eastRoad, etc.), and register all
                traffic lights

         */
        System.out.println("Building and saving map...");
        int width = map.getWidth();
        int height = map.getHeight();
        Map fixed = new Map(width,height);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Component current = map.getGrid().get(x, y);
                if(current instanceof Intersection) {
                    System.out.println("Found Intersection at " + x + ", " + y);
                }
                else if(current instanceof Road) {
                    Road road = (Road) current;
                    if(road.runsVertically()) {
                        System.out.println("Found Vertical Road at " + x + ", " + y);
                    } else {
                        System.out.println("Found Horizontal Road at " + x + ", " + y);
                    }
                }
            }
        }
        return fixed;
    }

    private Node getNodeFromIndex(int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    private StackPane addIntersection(int x, int y, Map map, MapGridGUIDecorator mapGridGUIDecorator, GridPane mapGridPane, ImageView imgView) {
        Coordinate coord = new Coordinate(x,y);
        Intersection intersection = new Intersection(coord);
        map.addIntersection(intersection);
        StackPane sp = mapGridGUIDecorator.redrawCell(x,y,mapGridPane);

        sp.setOnMouseClicked(click -> {
            ComponentType currentFocused = MapMakerController.getCurrentFocused();
            if(currentFocused == ComponentType.ROADNS) {
                addRoadNS(x,y,map,mapGridGUIDecorator,mapGridPane,roadNSImgView);
            } else if(currentFocused == ComponentType.ROADEW) {
                addRoadEW(x,y,map,mapGridGUIDecorator,mapGridPane,roadEWImgView);
            } else if(currentFocused == ComponentType.GRASS) {
                addGrass(x,y,map,mapGridGUIDecorator,mapGridPane,grassImgView);
            }
        });

        // put focus back on Intersection
        MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
        MapMakerController.setCurrentFocused(ComponentType.INTERSECTION);
        imgView.requestFocus();

        return sp;
    }

    private StackPane addRoadNS(int x, int y, Map map, MapGridGUIDecorator mapGridGUIDecorator, GridPane mapGridPane, ImageView imgView) {
        StackPane sp = null;
        Coordinate coord = new Coordinate(x,y);
        Road road = new Road(coord,coord);
        try {
            road.addLane(new Lane(coord,coord,MapDirection.NORTH));
            road.addLane(new Lane(coord,coord,MapDirection.SOUTH));
            map.addRoad(road);
            sp = mapGridGUIDecorator.redrawCell(x,y,mapGridPane);

            sp.setOnMouseClicked(click -> {
                ComponentType currentFocused = MapMakerController.getCurrentFocused();
                if(currentFocused == ComponentType.INTERSECTION) {
                    addIntersection(x,y,map,mapGridGUIDecorator,mapGridPane,intersectionImgView);
                } else if(currentFocused == ComponentType.GRASS) {
                    addGrass(x,y,map,mapGridGUIDecorator,mapGridPane,grassImgView);
                } else if(currentFocused == ComponentType.ROADEW) {
                    addRoadEW(x,y,map,mapGridGUIDecorator,mapGridPane,roadEWImgView);
                }
            });

            // put focus back on RoadNS
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.ROADNS);
            imgView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    private StackPane addRoadEW(int x, int y, Map map, MapGridGUIDecorator mapGridGUIDecorator, GridPane mapGridPane, ImageView imgView) {
        StackPane sp = null;
        Coordinate coord = new Coordinate(x,y);
        Road road = new Road(coord,coord);
        try {
            road.addLane(new Lane(coord,coord,MapDirection.EAST));
            road.addLane(new Lane(coord,coord,MapDirection.WEST));
            map.addRoad(road);
            sp = mapGridGUIDecorator.redrawCell(x,y,mapGridPane);

            sp.setOnMouseClicked(click -> {
                ComponentType currentFocused = MapMakerController.getCurrentFocused();
                if(currentFocused == ComponentType.INTERSECTION) {
                    addIntersection(x,y,map,mapGridGUIDecorator,mapGridPane,intersectionImgView);
                } else if(currentFocused == ComponentType.ROADNS) {
                    addRoadNS(x,y,map,mapGridGUIDecorator,mapGridPane,roadNSImgView);
                } else if(currentFocused == ComponentType.GRASS) {
                    addGrass(x,y,map,mapGridGUIDecorator,mapGridPane,grassImgView);
                }
            });

            // put focus back on RoadEW
            MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
            MapMakerController.setCurrentFocused(ComponentType.ROADEW);
            imgView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    private void addGrass(int x, int y, Map map, MapGridGUIDecorator mapGridGUIDecorator, GridPane mapGridPane, ImageView imgView) {
        Coordinate coord = new Coordinate(x,y);
        map.clearCell(coord);

        StackPane sp = mapGridGUIDecorator.redrawCell(x,y,mapGridPane);

        sp.setOnMouseClicked(click -> {
            ComponentType currentFocused = MapMakerController.getCurrentFocused();
            if(currentFocused == ComponentType.INTERSECTION) {
                addIntersection(x,y,map,mapGridGUIDecorator,mapGridPane,intersectionImgView);
            } else if(currentFocused == ComponentType.ROADNS) {
                addRoadNS(x,y,map,mapGridGUIDecorator,mapGridPane,roadNSImgView);
            } else if(currentFocused == ComponentType.ROADEW) {
                addRoadEW(x,y,map,mapGridGUIDecorator,mapGridPane,roadEWImgView);
            }
        });

        // put focus back on Grass
        MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
        MapMakerController.setCurrentFocused(ComponentType.GRASS);
        imgView.requestFocus();

    }

}
