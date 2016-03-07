package londonsw.view.simulation;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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

        division = division / numberLanes;

        double carDimensionX = cellDimension;
        double carDimensionY = cellDimension;
        double angle = 0.0;
        double startPointX =
                x
                        * this.getCurrentCoordinate().getX();

        if(this.getCurrentLane().getRoad().runsVertically())
        {
            startPointX+=(this.getCurrentLane().getRoadIndex() * division);
            carDimensionX = cellDimension/numberLanes;
            //angle = 90;
        }

        double startPointY =
                y
                        * this.getCurrentCoordinate().getY();

        //car runs Horizontally
        if(!this.getCurrentLane().getRoad().runsVertically())
        {
            startPointY+=(this.getCurrentLane().getRoadIndex() * division);
            carDimensionY = cellDimension/numberLanes;
            //angle = 0;
        }

        Rectangle r = new Rectangle(
                startPointX,
                startPointY,
                carDimensionX * this.getResizeFactor().getResizeX(),    //TODO: hardcode
                carDimensionY * this.getResizeFactor().getResizeY());    //TODO: hardcode

        r.setFill(Color.BLUE);

        r.setRotate(angle);

        this.setRectangle(r);
    }

    public void moveVehicleGUI(int step, int state) {
        final Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        int numberLanes = this.getCurrentLane().getRoad().getNumberLanes();

        //move according to moving direction

        if(state==0)
            timeline.stop();
        else
        if(state==2)    //car in intersection
        {
            double imageDimension = 100.0;
            double x = imageDimension * this.getResizeFactor().getResizeX();
            double y = imageDimension * this.getResizeFactor().getResizeY();
            double division =   x;

            division = division / numberLanes;

            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), this.getRectangle());

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

                //x+= this.getRectangle().getHeight()*this.getCurrentLane().getRoadIndex();

                tt.setByX(x);
                tt.setByY(y);

                //rotate certain degrees

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);

            }
            else
            if(this.getPreviousLane().getMovingDirection()== MapDirection.SOUTH && this.getCurrentLane().getMovingDirection()==MapDirection.WEST)
            {

                //y+=(this.getCurrentLane().getRoadIndex() * division);

                //y+=(this.getRectangle().getWidth()* this.getCurrentLane().getRoadIndex());

                tt.setByX(-x);
                tt.setByY(y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.WEST && this.getCurrentLane().getMovingDirection()==MapDirection.NORTH)
            {
                //x+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(-x);
                tt.setByY(-y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.NORTH && this.getCurrentLane().getMovingDirection()==MapDirection.EAST)
            {
                //y+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(x);
                tt.setByY(-y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.WEST && this.getCurrentLane().getMovingDirection()==MapDirection.SOUTH)
            {
                //x+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(-x);
                tt.setByY(y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            if(this.getPreviousLane().getMovingDirection()==MapDirection.EAST && this.getCurrentLane().getMovingDirection()==MapDirection.NORTH)
            {
                //x+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(x);
                tt.setByY(-y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.NORTH && this.getCurrentLane().getMovingDirection()==MapDirection.WEST)
            {
                //y+=(this.getCurrentLane().getRoadIndex() * division);

                tt.setByX(-x);
                tt.setByY(-y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }
            else
            if(this.getPreviousLane().getMovingDirection()==MapDirection.SOUTH && this.getCurrentLane().getMovingDirection()==MapDirection.EAST)
            {
                //y+= division * this.getCurrentLane().getRoadIndex();

                //y+= this.getRectangle().getWidth()*this.getCurrentLane().getRoadIndex();

                tt.setByX(x);
                tt.setByY(y);

                this.getRectangle().setRotate(this.getRectangle().getRotate()+90);
            }

            tt.play();

            this.setVehicleState(1);    //move car again

        }
        else
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

            java.lang.System.out.println(doubleProperty.getValue());

            final KeyValue kv = new KeyValue(doubleProperty,
                    distance);
            final KeyFrame kf = new KeyFrame(Duration.millis(Ticker.getTickInterval()), kv);

            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }
}