package londonsw.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import londonsw.view.mapcreation.ComponentType;
import londonsw.view.mapcreation.MapMakerScreen;


import java.io.IOException;



public class MapMakerController {

    private int width;
    private int height;
    private Stage primaryStage;

    private static ComponentType currentFocused;
    private static ComponentType previousFocused;

    public MapMakerController(Stage primaryStage) {
        System.out.println("Created new MapMakerController");
        this.primaryStage = primaryStage;
    }

    public void setWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawScreen() throws Exception{
        System.out.println("Called drawScreen in the controller...");
        MapMakerScreen mapMakerScreen = new MapMakerScreen(width, height);
        mapMakerScreen.drawScreen(primaryStage);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static ComponentType getCurrentFocused() {
        return currentFocused;
    }

    public static void setCurrentFocused(ComponentType focused) {
        currentFocused = focused;
    }

    public static ComponentType getPreviousFocused() {
        return previousFocused;
    }

    public static void setPreviousFocused(ComponentType prev) {
        previousFocused = prev;
    }

}
