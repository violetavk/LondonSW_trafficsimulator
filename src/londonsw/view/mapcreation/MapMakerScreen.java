package londonsw.view.mapcreation;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.Intersection;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.view.simulation.MapGridGUIDecorator;
import org.reactfx.EventStream;
import org.reactfx.EventStreams;

/**
 * Created by violet on 16/03/2016.
 */
public class MapMakerScreen {

    private Map map;
    private int width;
    private int height;

    public MapMakerScreen(int width, int height) {
        System.out.println("Created new MapMakerScreen");
        this.width = width;
        this.height = height;
    }

    public void drawScreen(Stage primaryStage) throws Exception {
        System.out.println("Actually drawing the screen");

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
        double resizeFactor = width > height ? width : height;
        System.out.println("Calculated RF of " + resizeFactor/100);
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(resizeFactor/100, resizeFactor/100));
        GridPane mapGridPane = mapGridGUIDecorator.drawComponents();
        mapGridPane.setPadding(new Insets(0,0,5,5));
        mapPane.getChildren().add(mapGridPane);
        borderPane.setCenter(mapPane);
        MapMakerController.setCurrentFocused(ComponentType.NOTHING);

        VBox sideComponents = new VBox();

        // Add "components" label
        Label componentsLabel = new Label("Components");
        componentsLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
        componentsLabel.setPadding(new Insets(5,5,5,5));
        sideComponents.getChildren().add(componentsLabel);

        // Add Intersection component square image
        VBox intersectionPane = new VBox();
        Label intersectionLabel = new Label("Intersection");
        intersectionLabel.setPadding(new Insets(5,5,0,15));
        intersectionLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,12));
        Image intersectionImg = new Image("IntersectionX.png",60,60,true,false);
        ImageView intersectionImgView = new ImageView(intersectionImg);
        StackPane stackPane = new StackPane(intersectionImgView);
        stackPane.setPadding(new Insets(0,10,10,10));
        intersectionPane.getChildren().add(intersectionLabel);
        intersectionPane.getChildren().add(stackPane);
        sideComponents.getChildren().add(intersectionPane);

        // Add RoadNS


        // Add RoadEW


        // Add Map click processing
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Node current = getNodeFromIndex(i, j, mapGridPane);
                final int x = j;
                final int y = i;
                current.setOnMouseClicked((MouseEvent click) -> {
                    MapMakerController.setPreviousFocused(MapMakerController.getCurrentFocused());
                    current.requestFocus();
                    MapMakerController.setCurrentFocused(ComponentType.MAP_SQUARE);
                });
                current.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    ComponentType previous = MapMakerController.getPreviousFocused();
                    if(previous == ComponentType.INTERSECTION) {
                        System.out.println("Adding new intersection at " + x + ", " + y);
                        Intersection intersection = new Intersection(new Coordinate(x,y));
                        map.addIntersection(intersection);
                        mapGridGUIDecorator.redrawCell(x,y,mapGridPane);
                    }
                });
            }
        }



        // Add intersection click processing
        DropShadow ds = new DropShadow(15, Color.BLUE);
        intersectionImgView.setOnMouseClicked(click -> {
            System.out.println("Clicked Intersection!");
            intersectionImgView.requestFocus();
            MapMakerController.setCurrentFocused(ComponentType.INTERSECTION);
            System.out.println("Current focused is " + MapMakerController.getCurrentFocused());
        });
        intersectionImgView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue) {
                intersectionImgView.setEffect(ds);
            }
            else {
                intersectionImgView.setEffect(null);
            }
        });

        borderPane.setRight(sideComponents);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        System.out.println("Showing new scene");
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
}
