package londonsw.view.simulation;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    public void drawCarS() {

        double cellDimension = imageDimension;
        double x = cellDimension * this.getResizeFactor().getResizeX();
        double y = cellDimension * this.getResizeFactor().getResizeY();

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();

        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;
        double angle = 0.0;
        double startPointX = x * this.getCurrentCoordinate().getX();
        double startPointY = y * this.getCurrentCoordinate().getY();

        if(this.getCurrentLane().getRoad().runsVertically())
        {
            startPointX+=(this.getCurrentLane().getRoadIndex());
        }

        //car runs Horizontally
        if(!this.getCurrentLane().getRoad().runsVertically())
        {
            //startPointY+=(this.getCurrentLane().getRoadIndex());
        }

        Rectangle rectangleBackground = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                carDimensionY * this.getResizeFactor().getResizeY());    //TODO: hardcode

        double carHeight = (carDimensionY * this.getResizeFactor().getResizeY()/numberLanes);

        Rectangle rectangleCar = new Rectangle
                (
                        startPointX, startPointY ,
                        (carDimensionX*this.getResizeFactor().getResizeX()),
                        (carHeight)
                );


        double conversion = (numberLanes-1) - this.getCurrentLane().getRoadIndex();

        double translation = 0.0;

        if(this.decoratedVehicle.getCurrentLane().getMovingDirection()==MapDirection.WEST)
        {
            translation = carHeight * conversion;
        }
        else
        if(this.decoratedVehicle.getCurrentLane().getMovingDirection()==MapDirection.EAST)
        {
            translation = carHeight * this.getCurrentLane().getRoadIndex();
        }
        else
        if(this.decoratedVehicle.getCurrentLane().getMovingDirection()==MapDirection.NORTH)
        {
            translation = carHeight * this.getCurrentLane().getRoadIndex();
        }
        else
        if(this.decoratedVehicle.getCurrentLane().getMovingDirection()==MapDirection.SOUTH)
        {
            translation = carHeight * conversion;
        }

        rectangleCar.setTranslateY(translation);

        if(this.getVehiclePriority()==5){
            rectangleCar.setFill(this.getColor());
        }
        else{
            rectangleCar.setFill(Color.BLUE);
        }

        switch (this.getCurrentLane().getMovingDirection())
        {
            case NORTH:

                angle = -90;

                break;

            case SOUTH:

                angle =90;

                break;

            case EAST:

                break;

            case WEST:

                angle = 180;

                break;
        }

        rectangleBackground.setFill(Color.SNOW);
        rectangleBackground.setRotate(angle);

        Pane carPane = new Pane();

        carPane.getChildren().addAll(rectangleBackground,rectangleCar);

        Group group = new Group(rectangleBackground,rectangleCar);

        group.setRotate(angle);

        this.setGroup(group);
    }

    public void moveVehicleGUIS(int step, int state) {
        final Timeline timeline = new Timeline();

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();

        /* move the car according to moving direction, below */

        if (state == 0) { // car must stop because red light, or something in the way
            timeline.stop();
        } else if (state == 2)  //car is at intersection
        {
            double x = this.imageDimension * this.getResizeFactor().getResizeX();
            double y = this.imageDimension * this.getResizeFactor().getResizeY();

            TranslateTransition tt = new TranslateTransition(Duration.millis(Ticker.getTickInterval()), this.getGroup());

            if (this.getPreviousLane().getMovingDirection() == this.getCurrentLane().getMovingDirection()) {
                switch (this.getPreviousLane().getMovingDirection()) {
                    case NORTH:
                        tt.setByY(-y * 2);
                        break;
                    case SOUTH:
                        tt.setByY(y * 2);
                        break;
                    case EAST:
                        tt.setByX(x * 2);
                        break;
                    case WEST:
                        tt.setByX(-x * 2);
                        break;
                }
            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.EAST && this.getCurrentLane().getMovingDirection() == MapDirection.SOUTH) {

                //Shift according to slot available

                tt.setByX(x);
                tt.setByY(y);

                //create rotation

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();

                TranslateTransition ttShift = new TranslateTransition();

                ttShift.setNode(this.getGroup().getChildren().get(1));

                //ttShift.setToY(this.getCurrentLane().getRoadIndex()*x/numberLanes);

                ttShift.setToY(0);

                ttShift.play();


            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.SOUTH && this.getCurrentLane().getMovingDirection() == MapDirection.WEST) {
                tt.setByX(-x);
                tt.setByY(y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.WEST && this.getCurrentLane().getMovingDirection() == MapDirection.NORTH) {
                tt.setByX(-x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.NORTH && this.getCurrentLane().getMovingDirection() == MapDirection.EAST) {
                tt.setByX(x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();

            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.WEST && this.getCurrentLane().getMovingDirection() == MapDirection.SOUTH) {
                tt.setByX(-x);
                tt.setByY(y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            if (this.getPreviousLane().getMovingDirection() == MapDirection.EAST && this.getCurrentLane().getMovingDirection() == MapDirection.NORTH) {
                tt.setByX(x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());

                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();

            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.NORTH && this.getCurrentLane().getMovingDirection() == MapDirection.WEST) {
                tt.setByX(-x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            } else if (this.getPreviousLane().getMovingDirection() == MapDirection.SOUTH && this.getCurrentLane().getMovingDirection() == MapDirection.EAST) {
                tt.setByX(x);
                tt.setByY(y);

                //create rotation

                RotateTransition rt = new RotateTransition();

                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }

            tt.play();

            this.setVehicleState(1);    //move car again

        } else // car driving straight down road
        {

            DoubleProperty doubleProperty = null;
            double distance = 0;
            double imageDimension = 100 * this.getResizeFactor().getResizeX();  //TODO: hardcode

            switch (this.getCurrentLane().getMovingDirection()) {

                case NORTH:

                    doubleProperty = this.getGroup().layoutYProperty();
                    distance = doubleProperty.getValue() - (imageDimension) * step;

                    break;
                case SOUTH:

                    doubleProperty = this.getGroup().layoutYProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case EAST:

                    doubleProperty = this.getGroup().layoutXProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case WEST:

                    doubleProperty = this.getGroup().layoutXProperty();
                    distance = doubleProperty.getValue() - (imageDimension) * step;

                    break;

            }

            java.lang.System.out.println(doubleProperty.getValue());

            final KeyValue kv = new KeyValue(doubleProperty,
                    distance);

            final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

            timeline.getKeyFrames().add(kf);

            timeline.play();
        }
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