package londonsw.view.simulation;/**
 * Created by felix on 03/03/2016.
 */

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        ImageView car = new ImageView();
        car.setImage(new Image("car.png",100,100,false,false));

        //Image im = new Image(roadBackgroundPath, image.getHeight() * getResizeFactor().getResizeX(), image.getWidth() * getResizeFactor().getResizeY(), false, false);

        car.setX(-car.getImage().getWidth() / 2);
        car.setY(300 - car.getImage().getHeight());
        car.setRotate(90);

        PathElement[] path =
                {
                        new MoveTo(0, 300),
                        new ArcTo(100, 100, 0, 100, 400, false, false),
                        new LineTo(300, 400),
                        new ArcTo(100, 100, 0, 400, 300, false, false),
                        new LineTo(400, 100),
                        new ArcTo(100, 100, 0, 300, 0, false, false),
                        new LineTo(100, 0),
                        new ArcTo(100, 100, 0, 0, 100, false, false),
                        new LineTo(0, 300),
                        new ClosePath()
                };

        Path road = new Path();
        road.setStroke(Color.BLACK);
        road.setStrokeWidth(75);
        road.getElements().addAll(path);

        Path divider = new Path();
        divider.setStroke(Color.WHITE);
        divider.setStrokeWidth(4);
        divider.getStrokeDashArray().addAll(10.0, 10.0);
        divider.getElements().addAll(path);

        PathTransition anim = new PathTransition();
        anim.setNode(car);
        anim.setPath(road);
        anim.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        anim.setInterpolator(Interpolator.LINEAR);
        anim.setDuration(new Duration(6000));
        anim.setCycleCount(Timeline.INDEFINITE);

        Group root = new Group();
        root.getChildren().addAll(road, divider, car);
        root.setTranslateX(50);
        root.setTranslateY(50);
        root.setOnMouseClicked(me ->
        {
            Animation.Status status = anim.getStatus();
            if (status == Animation.Status.RUNNING &&
                    status != Animation.Status.PAUSED)
                anim.pause();
            else
                anim.play();
        });
        Scene scene = new Scene(root, 500, 500, Color.DARKGREEN);

        primaryStage.setTitle("PathTransition Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
