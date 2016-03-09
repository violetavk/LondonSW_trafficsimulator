package londonsw.view.simulation;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.Ticker;
import londonsw.model.simulation.components.MapDirection;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Vehicle;

/**
 * Created by felix on 04/03/2016.
 */
public class VehicleGUIDecorator extends VehicleDecorator {

    public VehicleGUIDecorator(Vehicle decoratedVehicle) {
        super(decoratedVehicle);
        VehicleController.addVehicleAndDecoratorPair(decoratedVehicle,this);
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

        //division = division / numberLanes;

        division = 1;

        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;
        double angle = 0.0;
        double startPointX = x * this.getCurrentCoordinate().getX();

        if(this.getCurrentLane().getRoad().runsVertically())
        {
            startPointX+=(this.getCurrentLane().getRoadIndex() * division);
            //carDimensionX = cellDimension/numberLanes;
        }

        double startPointY = y * this.getCurrentCoordinate().getY();

        //car runs Horizontally
        if(!this.getCurrentLane().getRoad().runsVertically())
        {
            startPointY+=(this.getCurrentLane().getRoadIndex() * division);
            //carDimensionY = cellDimension/numberLanes;
        }

        Rectangle r = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                carDimensionY * this.getResizeFactor().getResizeY());    //TODO: hardcode

        r.setRotate(angle);

        r.setFill(Color.BLUE);

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
            double imageDimension = 100.0;
            double x = imageDimension * this.getResizeFactor().getResizeX();
            double y = imageDimension * this.getResizeFactor().getResizeY();

            TranslateTransition tt = new TranslateTransition(Duration.millis(Ticker.getTickInterval()), this.getRectangle());//TODO: change to 1 tick duration

            if(this.getPreviousLane().getMovingDirection()==this.getCurrentLane().getMovingDirection()) {
                switch (this.getPreviousLane().getMovingDirection())
                {
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
            }
            else
            if(this.getPreviousLane().getMovingDirection()== MapDirection.EAST && this.getCurrentLane().getMovingDirection()==MapDirection.SOUTH)
            {
                //x+=((this.getRectangle().getWidth()/numberLanes)*this.getCurrentLane().getRoadIndex()-(this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getHeight()/numberLanes)*this.getCurrentLane().getRoadIndex();

                tt.setByX(x);
                tt.setByY(y);

                //create rotation

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            else
            if(this.getPreviousLane().getMovingDirection()== MapDirection.SOUTH && this.getCurrentLane().getMovingDirection()==MapDirection.WEST)
            {
                //x+=(this.getRectangle().getHeight()/numberLanes)*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getWidth()/numberLanes*this.getCurrentLane().getRoadIndex()- (this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();

                tt.setByX(-x);
                tt.setByY(y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.WEST && this.getCurrentLane().getMovingDirection()==MapDirection.NORTH)
            {
                //x+=((this.getRectangle().getWidth()/numberLanes)*this.getCurrentLane().getRoadIndex()+(this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getHeight()/numberLanes)*this.getCurrentLane().getRoadIndex();

                tt.setByX(-x);
                tt.setByY(-y);

                //this.getRectangle().setRotate(this.getRectangle().getRotate()+90);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.NORTH && this.getCurrentLane().getMovingDirection()==MapDirection.EAST)
            {

                //x+=(this.getRectangle().getHeight()/numberLanes)*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getWidth()/numberLanes*this.getCurrentLane().getRoadIndex() + (this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();

                //x+=(this.getRectangle().getWidth()/numberLanes);
                //y+=this.getRectangle().getWidth()/numberLanes;


                tt.setByX(x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();

            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.WEST && this.getCurrentLane().getMovingDirection()==MapDirection.SOUTH)
            {
                //x+=((this.getRectangle().getWidth()/numberLanes)*this.getCurrentLane().getRoadIndex()-(this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getWidth()/numberLanes)*this.getCurrentLane().getRoadIndex();

                tt.setByX(-x);
                tt.setByY(y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            if(this.getPreviousLane().getMovingDirection()==MapDirection.EAST && this.getCurrentLane().getMovingDirection()==MapDirection.NORTH)
            {
                //x+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.NORTH && this.getCurrentLane().getMovingDirection()==MapDirection.WEST)
            {
                //y+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(-x);
                tt.setByY(-y);

                RotateTransition rt = new RotateTransition();
                rt.setNode(tt.getNode());
                rt.setByAngle(-90);
                rt.setDuration(Duration.millis(Ticker.getTickInterval()));
                rt.play();
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.SOUTH && this.getCurrentLane().getMovingDirection()==MapDirection.EAST)
            {
                //x+=(this.getRectangle().getHeight()/numberLanes)*this.getCurrentLane().getRoadIndex();
                //y+=(this.getRectangle().getWidth()/numberLanes*this.getCurrentLane().getRoadIndex() - (this.getRectangle().getHeight()/numberLanes))*this.getCurrentLane().getRoadIndex();

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

        }
        else // car driving straight down road
        {

            DoubleProperty doubleProperty = null;
            double distance = 0;
            double imageDimension = 100 * this.getResizeFactor().getResizeX();  //TODO: hardcode

            switch (this.getCurrentLane().getMovingDirection()) {

                case NORTH:

                    doubleProperty = this.getRectangle().yProperty();
                    distance = doubleProperty.getValue() - (imageDimension) * step;

                    break;
                case SOUTH:

                    doubleProperty = this.getRectangle().yProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case EAST:
                    doubleProperty = this.getRectangle().xProperty();
                    distance = doubleProperty.getValue() + (imageDimension) * step;

                    break;
                case WEST:
                    doubleProperty = this.getRectangle().xProperty();
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
}