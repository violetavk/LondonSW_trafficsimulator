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

public class VehicleGUIDecorator extends VehicleDecorator {

    private double imageDimension;
    private Rectangle rectangle;
    private GridPane gridPane;
    private ResizeFactor resizeFactor;
    private Color color;
    private double verticalStartFudgeFactor;
    private double horizontalStartFudgeFactor;
    private Group group;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    private Pane pane;

    public VehicleGUIDecorator(Vehicle decoratedVehicle) {
        super(decoratedVehicle);
        VehicleController.addVehicleAndDecoratorPair(decoratedVehicle,this);
        imageDimension = 100.0;
        verticalStartFudgeFactor = 0;
        horizontalStartFudgeFactor = 0;
    }

    public void setResizeFactor(ResizeFactor resizeFactor) {
        this.resizeFactor = resizeFactor;
    }

    public ResizeFactor getResizeFactor() {
        return resizeFactor;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void drawCar() {
        double cellDimension = imageDimension;
        double x = cellDimension * this.getResizeFactor().getResizeX();
        double y = cellDimension * this.getResizeFactor().getResizeY();

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();
        double division = cellDimension * this.getResizeFactor().getResizeX() / numberLanes;
        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;
        double angle = 0.0;
//        double startPointX = x * this.getCurrentCoordinate().getX();

        Coordinate coordinate = this.getCurrentCoordinate();
        double[] start = coordinateToPixels(coordinate,this.getCurrentLane().getMovingDirection(),false);
        double startPointX = start[0];
        double startPointY = start[1];

        if(this.getCurrentLane().getRoad().runsVertically())
        {
            carDimensionX = cellDimension/numberLanes - 80*resizeFactor.getResizeX();
            carDimensionY -= 15;
            verticalStartFudgeFactor = -carDimensionX*resizeFactor.getResizeX();
        }
        else {
            carDimensionY = cellDimension/numberLanes - 80*resizeFactor.getResizeX();
            carDimensionX -= 15;
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

    public void moveVehicleGUI(int step, int state) {

        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();

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

            Coordinate fromTranslation = directionToTranslation(fromDirection);
            Coordinate toTranslation = directionToTranslation(toDirection);
            Coordinate overallTranslation = Coordinate.add(fromTranslation,toTranslation);
            Coordinate newPosition = Coordinate.add(overallTranslation,coordinate);

            // work out rotation
            int rotation = getRotationFromDirectionChange(fromDirection,toDirection);
//            System.out.println(fromDirection + " -> " + toDirection + ": " + fromTranslation + " + " + toTranslation + " = " + overallTranslation + " -> " + rotation + " -> New coord: " + newPosition);

            // work out actual animation
            double[] toPixels = coordinateToPixels(newPosition,toDirection,true);
            double toXPixels = toPixels[0];
            double toYPixels = toPixels[1];

//            System.out.println("Moving by " + (toXPixels - fromXPixels) + ", " + (toYPixels - fromYPixels));
            tt.setToX(toXPixels - fromXPixels);
            tt.setToY(toYPixels - fromYPixels);

            RotateTransition rt = new RotateTransition();
            rt.setNode(tt.getNode());
            rt.setByAngle(rotation);
            rt.setDuration(Duration.millis(Ticker.getTickInterval()));
            rt.play();
            tt.play();
            this.setVehicleState(1);

        }
        else // car driving straight down road
        {
            TranslateTransition tt = new TranslateTransition(Duration.millis(Ticker.getTickInterval()), this.getRectangle());

            Coordinate coordinate = this.getPreviousCoordinate();
            MapDirection toDirection = this.getCurrentLane().getMovingDirection();

            double fromXPixels = rectangle.xProperty().doubleValue();
            double fromYPixels = rectangle.yProperty().doubleValue();
//            System.out.println("***Moving from " + fromXPixels + ", " + fromYPixels);

            Coordinate toTranslation = directionToTranslation(toDirection);
            Coordinate newPosition = Coordinate.add(toTranslation,coordinate);

            double[] toPixels = coordinateToPixels(newPosition,toDirection,false);
            double toXPixels = toPixels[0];
            double toYPixels = toPixels[1];

            if(toDirection == MapDirection.NORTH || toDirection == MapDirection.SOUTH)
                tt.setToY(toYPixels - fromYPixels);
            else if(toDirection == MapDirection.EAST || toDirection == MapDirection.WEST)
                tt.setToX(toXPixels - fromXPixels);

            tt.play();
        }
    }

    private double[] coordinateToPixels(Coordinate c, MapDirection direction, boolean fromIntersection) {
        double[] pixels = new double[2];
        int x = c.getX();
        int y = c.getY();
//        System.out.println("Calculating location for "+c+"...");
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
//        System.out.println(" -> Location is " + pixels[0] + ", " + pixels[1]);

        return pixels;
    }

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