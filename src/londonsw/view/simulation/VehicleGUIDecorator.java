package londonsw.view.simulation;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.Coordinate;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Vehicle;

/**
 * This class is to draw vehicles and display them in the GUI. Each vehicle will have exactly one VehicleGUIDecorator
 * associated with it.
 */
public class VehicleGUIDecorator extends VehicleDecorator {

    private double imageDimension;
    private Rectangle rectangle;
    private ResizeFactor resizeFactor;
    private Color color;
    private double verticalStartFudgeFactor;
    private double horizontalStartFudgeFactor;
    private Pane pane;

    /**
     * Creates a new instance of this decorator class
     * @param decoratedVehicle the Vehicle to be associated with
     */
    public VehicleGUIDecorator(Vehicle decoratedVehicle) {
        super(decoratedVehicle);
        VehicleController.addVehicleAndDecoratorPair(decoratedVehicle,this);
        imageDimension = 100.0;
        verticalStartFudgeFactor = 0;
        horizontalStartFudgeFactor = 0;
    }

    /**
     * Sets the resize factor for this vehicle to display properly
     * @param resizeFactor the resize factor for the simulation
     */
    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    /**
     * Gets the resize factor for the simulation
     * @return
     */
    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    /**
     * Gets the rectangle of the vehicle
     * @return the rectangle representing the vehicle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets the rectangle of the vehicle
     * @param rectangle the rectangle to represent the vehicle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the pane of the vehicle
     * @return the pane the vehicle resides in
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Sets the pane of the vehicle
     * @param pane the pane to set
     */
    public void setPane(Pane pane) {
        this.pane = pane;
    }

    /**
     * Gets the color of the vehicle
     * @return the Color of this vehicle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the vehicle
     * @param color the color of the vehicle
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Draws the vehicle to display in the simulation. Each vehicle would have this method called on it.
     */
    public void drawCar() {
        double cellDimension = imageDimension;

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();
        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;
        double angle = 0.0;

        // determine the start location of the vehicle in the map
        Coordinate coordinate = this.getCurrentCoordinate();
        double[] start = coordinateToPixels(coordinate,this.getCurrentLane().getMovingDirection());
        double startPointX = start[0];
        double startPointY = start[1];

        // determine the size of the vehicle based on the size of the map
        if(this.getCurrentLane().getRoad().runsVertically())
        {
            carDimensionX = cellDimension/numberLanes - 50*resizeFactor.getResizeX();
            carDimensionY -= 15;
            if(resizeFactor.getResizeX() <= 0.25) {
                carDimensionX = cellDimension/numberLanes - 80*resizeFactor.getResizeX();
            }
            verticalStartFudgeFactor = -carDimensionX*resizeFactor.getResizeX();
        }
        else {
            carDimensionY = cellDimension/numberLanes - 50*resizeFactor.getResizeX();
            carDimensionX -= 15;
            if(resizeFactor.getResizeX() <= 0.25) {
                carDimensionY = cellDimension/numberLanes - 80*resizeFactor.getResizeX();
            }
            horizontalStartFudgeFactor = -carDimensionY*resizeFactor.getResizeX();
        }

        Rectangle r = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX * this.getResizeFactor().getResizeX(),
                carDimensionY * this.getResizeFactor().getResizeY());

        /**
         * Simulating Ambulance using fill Transitions
         *
         */
        FillTransition ft = new FillTransition();
        ft.setShape(r);
        ft.setDuration(Duration.millis(500));
        if(this.getVehiclePriority()==5) {
            ft.setFromValue(this.getColor());
            ft.setToValue(Color.BLUE);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.setInterpolator(Interpolator.LINEAR);
            ft.play();
        }

        else
            r.setFill(Color.YELLOW);

        r.setRotate(angle);

        this.setRectangle(r);
    }

    /**
     * Moves the vehicle in the GUI based on the state of the vehicle. A vehicle is either stopped, moving through
     * an intersection, moving straight down a road, or left the simulation.
     * @param step how much to move the vehicle by
     * @param state the state of the vehicle
     */
    public void moveVehicleGUI(int step, int state) {

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        /* move the car according to moving direction, below */
        if(state == 0) { // car must stop because red light, or something in the way
            timeline.stop();
        }
        else if(state == 2)  //car is at intersection
        {
            TranslateTransition tt = new TranslateTransition(Duration.millis(Ticker.getTickInterval()), this.getRectangle());
            MapDirection fromDirection = this.getPreviousLane().getMovingDirection();
            MapDirection toDirection = this.getCurrentLane().getMovingDirection();

            Coordinate coordinate = this.getPreviousCoordinate();

            double fromXPixels = rectangle.xProperty().doubleValue();
            double fromYPixels = rectangle.yProperty().doubleValue();

            // determine new position of vehicle
            Coordinate fromTranslation = directionToTranslation(fromDirection);
            Coordinate toTranslation = directionToTranslation(toDirection);
            Coordinate overallTranslation = Coordinate.add(fromTranslation,toTranslation);
            Coordinate newPosition = Coordinate.add(overallTranslation,coordinate);

            // work out rotation
            int rotation = getRotationFromDirectionChange(fromDirection,toDirection);

            // determine pixel locations for the actual animation
            double[] toPixels = coordinateToPixels(newPosition,toDirection);
            double toXPixels = toPixels[0];
            double toYPixels = toPixels[1];

            // set the movement translation
            tt.setToX(toXPixels - fromXPixels);
            tt.setToY(toYPixels - fromYPixels);

            // move and rotate the car
            RotateTransition rt = new RotateTransition();
            rt.setNode(tt.getNode());
            rt.setByAngle(rotation);
            rt.setDuration(Duration.millis(Ticker.getTickInterval()));
            rt.play();
            tt.play();
            this.setVehicleState(1);

        }
        else if(state == 3) {
            //Car deleted state
            this.getPane().getChildren().remove(this.getRectangle());
        }
        else // car driving straight down road
        {
            TranslateTransition tt = new TranslateTransition(Duration.millis(Ticker.getTickInterval()), this.getRectangle());

            Coordinate coordinate = this.getPreviousCoordinate();
            MapDirection toDirection = this.getCurrentLane().getMovingDirection();

            double fromXPixels = rectangle.xProperty().doubleValue();
            double fromYPixels = rectangle.yProperty().doubleValue();

            // determine new location for car to move to
            Coordinate toTranslation = directionToTranslation(toDirection);
            Coordinate newPosition = Coordinate.add(toTranslation,coordinate);

            // determine pixel locations for the actual animation
            double[] toPixels = coordinateToPixels(newPosition,toDirection);
            double toXPixels = toPixels[0];
            double toYPixels = toPixels[1];

            // set the translation animation based on moving direction
            if(toDirection == MapDirection.NORTH || toDirection == MapDirection.SOUTH)
                tt.setToY(toYPixels - fromYPixels);
            else if(toDirection == MapDirection.EAST || toDirection == MapDirection.WEST)
                tt.setToX(toXPixels - fromXPixels);

            // play the animation
            tt.play();
        }
    }

    /**
     * Determines the pixel location for a vehicle if it occupies Coordinate c and traveling in the MapDirection direction.
     * Each grid cell location is determined by the image dimension, resized by the resize factor, and the lane the
     * vehicle should occupy.
     * @param c the Coordinate where the vehicle will be
     * @param direction the MapDirection that coordinate is moving, to determine the proper lane for the vehicle
     * @return an array of length 2, where index 0 is the x-coordinate and index 1 is the y-coordinate. This is so you can get
     * both x- and y-locations at once, without calling the function twice.
     */
    private double[] coordinateToPixels(Coordinate c, MapDirection direction) {
        double[] pixels = new double[2];
        int x = c.getX();
        int y = c.getY();
        if(direction == MapDirection.NORTH) {
            pixels[0] = x * imageDimension * resizeFactor.getResizeX() + (.1 * imageDimension * resizeFactor.getResizeX() + horizontalStartFudgeFactor);
            pixels[1] = y * imageDimension * resizeFactor.getResizeX() - horizontalStartFudgeFactor;
        }
        else if(direction == MapDirection.EAST) {
            pixels[0] = x * imageDimension * resizeFactor.getResizeX() + (.1 * imageDimension * resizeFactor.getResizeX() - verticalStartFudgeFactor);
            pixels[1] = y * imageDimension * resizeFactor.getResizeX() + (.1 * imageDimension * resizeFactor.getResizeX() + verticalStartFudgeFactor);
        }
        else if(direction == MapDirection.WEST) {
            pixels[0] = x * imageDimension * resizeFactor.getResizeX() - verticalStartFudgeFactor;
            pixels[1] = y * imageDimension * resizeFactor.getResizeX() + (.6 * imageDimension * resizeFactor.getResizeX() + verticalStartFudgeFactor);
        }
        else if(direction == MapDirection.SOUTH) {
            pixels[0] = x * imageDimension * resizeFactor.getResizeX() + (.6 * imageDimension * resizeFactor.getResizeX()) + horizontalStartFudgeFactor;
            pixels[1] = y * imageDimension * resizeFactor.getResizeX() + (.1 * imageDimension * resizeFactor.getResizeX()) - horizontalStartFudgeFactor;
        }

        return pixels;
    }

    /**
     * Determines the vehicle rotation required based on where it is coming from and where it is going to. Possible
     * rotations are -90 for turning left, 90 for turning right, or 0 for driving straight.
     * @param fromDirection the direction the vehicle is coming from
     * @param toDirection the direction the vehicle is moving to
     * @return integer representing how much to rotate the vehicle by (90, -90, or 0)
     */
    private int getRotationFromDirectionChange(MapDirection fromDirection, MapDirection toDirection) {
        if(
                (fromDirection == MapDirection.SOUTH && toDirection == MapDirection.EAST) ||
                (fromDirection == MapDirection.NORTH && toDirection == MapDirection.WEST) ||
                (fromDirection == MapDirection.EAST && toDirection == MapDirection.NORTH) ||
                (fromDirection == MapDirection.WEST && toDirection == MapDirection.SOUTH)) {
            return -90;
        }
        else if(
                (fromDirection == MapDirection.EAST && toDirection == MapDirection.SOUTH) ||
                (fromDirection == MapDirection.SOUTH && toDirection == MapDirection.WEST) ||
                (fromDirection == MapDirection.WEST && toDirection == MapDirection.NORTH) ||
                (fromDirection == MapDirection.NORTH && toDirection == MapDirection.EAST)) {
            return 90;
        }
        else {
            return 0;
        }
    }

    /**
     * Translates a moving direction into a Coordinate translation. For instance, moving north means moving 0 squares in the x
     * direction and -1 square in the y direction. This is used for determining overall movement of vehicles.
     * @param fromDirection the direction the vehicle is moving from or to
     * @return a Coordinate representing the 2-d translation required for that map direction
     */
    private Coordinate directionToTranslation(MapDirection fromDirection) {
        switch (fromDirection) {
            case NORTH:
                return new Coordinate(0,-1);
            case SOUTH:
                return new Coordinate(0,1);
            case EAST:
                return new Coordinate(1,0);
            case WEST:
                return new Coordinate(-1,0);
        }

        return null;
    }
}