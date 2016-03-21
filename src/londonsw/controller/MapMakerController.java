package londonsw.controller;

import javafx.stage.Stage;
import londonsw.view.mapcreation.ComponentType;
import londonsw.view.mapcreation.MapMakerScreen;

/**
 * The controller for the Map making aspect of this software
 */
public class MapMakerController {

    private int width;
    private int height;
    private Stage primaryStage;

    private static ComponentType currentFocused;
    private static ComponentType previousFocused;

    /**
     * Creates an instance of a MapMakerController with a stage in which to draw the Map making screen
     * @param primaryStage the stage in which to draw the Map making screen
     */
    public MapMakerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Tells this controller what width and height the user chose for their map
     * @param width the width of the user's new map
     * @param height the height of the user's new map
     */
    public void setWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Draws the screen using the stage given and displays it to the user
     * @throws Exception
     */
    public void drawScreen() throws Exception{
        MapMakerScreen mapMakerScreen = new MapMakerScreen(width, height);
        mapMakerScreen.drawScreen(primaryStage);
    }

    /**
     * Get the width the user chose for their new map
     * @return width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height the user chose for their map
     * @return height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets what the current focused is in the screen. This is what the user clicked last in the screen. It is of type
     * ComponentType enum, which can be a RoadNS image, Map_Square, Nothing, etc
     * @return ComponentType enum of what the user clicked last
     */
    public static ComponentType getCurrentFocused() {
        return currentFocused;
    }

    /**
     * Sets the current focused in the map, this is set after the user clicks something
     * @param focused what was last clicked on by the user, converted to type enum ComponentType
     */
    public static void setCurrentFocused(ComponentType focused) {
        currentFocused = focused;
    }

    /**
     * Gets what the user previously clicked (the click before the current click)
     * @return ComponentType of what was clicked on the click before the last
     */
    public static ComponentType getPreviousFocused() {
        return previousFocused;
    }

    /**
     * Sets the previously clicked before last item
     * @param prev sets the click before the last click to what was focused, converted to type ComponentType
     */
    public static void setPreviousFocused(ComponentType prev) {
        previousFocused = prev;
    }

}
