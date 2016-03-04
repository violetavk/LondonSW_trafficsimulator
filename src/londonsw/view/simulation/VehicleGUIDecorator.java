package londonsw.view.simulation;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Vehicle;

/**
 * Created by felix on 04/03/2016.
 */
public class VehicleGUIDecorator extends VehicleDecorator {

    public VehicleGUIDecorator(Vehicle decoratedVehicle) {
        super(decoratedVehicle);
    }

    private Rectangle rectangle;
    private GridPane gridPane;
    private ResizeFactor resizeFactor;

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

    public void drawCar() {

        double cellDimension = 100;
        double x = cellDimension * this.getResizeFactor().getResizeX();
        double y = cellDimension * this.getResizeFactor().getResizeY();

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();
        double division =   cellDimension * this.getResizeFactor().getResizeX();

        division = division / numberLanes;

        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;

        double startPointX =
                x
                        * this.getCurrentCoordinate().getX();

        if(this.getCurrentLane().getRoad().runsVertically())
        {
            startPointX+=(this.getCurrentLane().getRoadIndex() * division);
            carDimensionX = cellDimension/numberLanes;
        }

        double startPointY =
                y
                        * this.getCurrentCoordinate().getY();

        //car runs Horizontally
        if(!this.getCurrentLane().getRoad().runsVertically())
        {
            startPointY+=(this.getCurrentLane().getRoadIndex() * division);
            carDimensionY = cellDimension/numberLanes;
        }

        Rectangle r = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                carDimensionY * this.getResizeFactor().getResizeY());    //TODO: hardcode

        r.setFill(Color.BLUE);

        this.setRectangle(r);
    }

    public void moveVehicleGUI(int step, int state) {
        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        //move according to moving direction

        if(state==0)
            timeline.stop();
        else
        if(state==2)
        {
            double cellDimension = 100;
            double x = cellDimension * this.getResizeFactor().getResizeX();
            double y = cellDimension * this.getResizeFactor().getResizeY();


            Path path = new Path();
            PathTransition pathTransition = new PathTransition();

//                path.getElements().add (new MoveTo(
//                    y* (this.decoratedCar.getPreviousLane().getExit().getY())));
//                    x* (this.decoratedCar.getPreviousLane().getExit().getX()),

            MoveTo moveTo = new MoveTo();

            //moveTo.setY(y*this.decoratedCar.getPreviousLane().getExit().getY());
            //moveTo.setX(x*this.decoratedCar.getPreviousLane().getExit().getX());

            moveTo.setX(this.getRectangle().getX());
            moveTo.setY(this.getRectangle().getY());

            path.getElements().add(moveTo);

            //path.getElements().add (new MoveTo(
            //        25,25));

            path.getElements().add (new ArcTo(
                    50,
                    50,
                    0,
                    x*this.getCurrentLane().getEntry().getX(),
                    y*this.getCurrentLane().getEntry().getY(),
                    false,true)
            );

            pathTransition.setPath(path);
            pathTransition.setNode(this.getRectangle());
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setInterpolator(Interpolator.LINEAR);

            pathTransition.setDuration(    Duration.millis(Ticker.getTickInterval()));

            pathTransition.play();
            //pathTransition.setNode(rect);

            //pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            //pathTransition.setCycleCount(4f);
            //pathTransition.setAutoReverse(true);



        }
        else if(state==3)
        {
//move to next coordinates

            double cellDimension = 100;
            double x = cellDimension * this.getResizeFactor().getResizeX();
            double y = cellDimension * this.getResizeFactor().getResizeY();


            Path path = new Path();
            PathTransition pathTransition = new PathTransition();

            MoveTo moveTo = new MoveTo(x*this.getPreviousLane().getExit().getX(),y*this.getPreviousLane().getExit().getY());
            path.getElements().add(moveTo);
            path.getElements().add(new LineTo(x*this.getCurrentLane().getEntry().getX(),y*this.getCurrentLane().getEntry().getY()));

            pathTransition.setPath(path);
            pathTransition.setNode(this.getRectangle());
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setInterpolator(Interpolator.LINEAR);

            pathTransition.setDuration(    Duration.millis(Ticker.getTickInterval()));

            pathTransition.play();


        }
        else {
            DoubleProperty doubleProperty = null;
            double distance = 0;
            double imageDimension = 100 * this.getResizeFactor().getResizeX();  //TODO: hardcode


//            if(this.decoratedCar.getPreviousLane()!=null) {
//                Lane previousLane = this.decoratedCar.getPreviousLane();
//
//                if (Lane.Rotate(previousLane, this.decoratedCar.currentLane)) {
//                     this.getRectangle().setRotate(90);
//                    this.setRectangle(this.getRectangle());
//                }
//            }


            switch (this.getCurrentLane().getMovingDirection()) {

                case NORTH:

                    doubleProperty = this.getRectangle().yProperty();
                    distance = doubleProperty.getValue() - (imageDimension) * step;

                    break;
                case SOUTH:
                    //this.getRectangle().setRotate(90);

                    doubleProperty = this.getRectangle().yProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case EAST:
                    doubleProperty = this.getRectangle().xProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case WEST:
                    //this.getRectangle().setRotate(180);
                    doubleProperty = this.getRectangle().xProperty();
                    distance = doubleProperty.getValue() - (imageDimension) * step;

                    break;

            }

            final KeyValue kv = new KeyValue(doubleProperty,
                    distance);
            final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }
}