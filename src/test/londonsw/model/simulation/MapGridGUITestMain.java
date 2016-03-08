package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import londonsw.model.simulation.components.*;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.view.simulation.VehicleGUIDecorator;
import javafx.stage.FileChooser;

import java.io.File;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Map map = drawTestMapSimple();
        //Map map=drawTestMapExample();

       Map map = drawTestMapSingleLine();

        //Map map = drawTestMapBig();

        //Map map = drawTestMapBasic();


        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor((map.getWidth()/5) / width, (map.getHeight()/5) / height));    //TODO HARDCODE

        //mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5 / width, 5 / height));    //TODO HARDCODE

        GridPane rootGP = mapGridGUIDecorator.drawComponents();

        //Lane L1 = map.getRandomLane();

        Lane L1 = map.getRoads().get(0).getLanes().get(0);

        //Lane L1 = map.getRoads().get(0).getLanes().get(roadIndex);

        //Lane L1 = map.getRandomLane();

        Car C1 = new Car(0, L1);

        VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);

        vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());

        vehicleGUIDecorator.drawCar();

        Pane carPane = new Pane();

        carPane.getChildren().add(vehicleGUIDecorator.getRectangle());

        StackPane sp = new StackPane();

        sp.getChildren().add(rootGP);

        sp.getChildren().add(carPane);

        Scene scene = new Scene(sp);

        vehicleGUIDecorator.setVehicleState(1);

        System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());

        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);

        //primaryStage.setFullScreen(true);

    }

    public Map drawTestMapBasic() throws Exception {

        Map map = new Map(10, 10);

        Road r1 = new Road(new Coordinate(2, 1), new Coordinate(8, 1));
        Road r2 = new Road(new Coordinate(1, 2), new Coordinate(1, 8));
        Road r3 = new Road(new Coordinate(9, 2), new Coordinate(9, 8));
        Road r4 = new Road(new Coordinate(2, 9), new Coordinate(8, 9));

        r1.addLane(new Lane(r1.getStartLocation(), r1.getEndLocation(), MapDirection.EAST));

        r2.addLane(new Lane(r2.getEndLocation(), r2.getStartLocation(), MapDirection.NORTH));

        r3.addLane(new Lane(r3.getStartLocation(), r3.getEndLocation(), MapDirection.SOUTH));

        r4.addLane(new Lane(r4.getEndLocation(), r4.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1, 1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9, 1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1, 9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9, 9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public Map drawTestMapSingleLine() throws Exception {

        Map map = new Map(25, 25);

        Road r1 = new Road(new Coordinate(2, 1), new Coordinate(8, 1));
        Road r2 = new Road(new Coordinate(1, 2), new Coordinate(1, 8));
        Road r3 = new Road(new Coordinate(9, 2), new Coordinate(9, 8));
        Road r4 = new Road(new Coordinate(2, 9), new Coordinate(8, 9));

        Road r5 = new Road(new Coordinate(10, 1), new Coordinate(16, 1));
        Road r6 = new Road(new Coordinate(17, 2), new Coordinate(17, 8));
        Road r7 = new Road(new Coordinate(10, 9), new Coordinate(16, 9));

        Lane disabledLane = new Lane(r3.getStartLocation(), r3.getEndLocation(), MapDirection.SOUTH);

        //disable lane
        disabledLane.setState(0);

        r1.addLane(new Lane(r1.getStartLocation(), r1.getEndLocation(), MapDirection.EAST));

        r2.addLane(new Lane(r2.getEndLocation(), r2.getStartLocation(), MapDirection.NORTH));

        r3.addLane(disabledLane);

        r4.addLane(new Lane(r4.getEndLocation(), r4.getStartLocation(), MapDirection.WEST));

        r5.addLane(new Lane(r5.getStartLocation(), r5.getEndLocation(), MapDirection.EAST));

        r6.addLane(new Lane(r6.getStartLocation(), r6.getEndLocation(), MapDirection.SOUTH));

        r7.addLane(new Lane(r7.getEndLocation(), r7.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);
        map.addRoad(r5);
        map.addRoad(r6);
        map.addRoad(r7);

        Intersection i1 = new Intersection(new Coordinate(1, 1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9, 1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        i2.setEastRoad(r5);

        Intersection i3 = new Intersection(new Coordinate(1, 9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);

        Intersection i4 = new Intersection(new Coordinate(9, 9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);
        i4.setEastRoad(r7);

        Intersection i5 = new Intersection(new Coordinate(17, 1));
        i5.setWestRoad(r5);
        i5.setSouthRoad(r6);

        Intersection i6 = new Intersection(new Coordinate(17, 9));
        i6.setNorthRoad(r6);
        i6.setWestRoad(r7);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);
        map.addIntersection(i5);
        map.addIntersection(i6);

        return map;
    }



    public Map drawTestMapSimple() throws Exception {

        Map map = new Map(10,10);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));



        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH));

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public Map drawTestMapBig() throws Exception {

        Map map = new Map(18,18);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));

        Road r5 = new Road(new Coordinate(10,1), new Coordinate(16,1));
        Road r6 = new Road(new Coordinate(17,2), new Coordinate(17,8));
        Road r7 = new Road(new Coordinate(10,9), new Coordinate(16,9));

        Lane disabledLane = new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);

        //disable lane
        disabledLane.setState(0);

        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(disabledLane);

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        r5.addLane(new Lane(r5.getStartLocation(),r5.getEndLocation(), MapDirection.EAST));
        r5.addLane(new Lane(r5.getEndLocation(),r5.getStartLocation(), MapDirection.WEST));

        r6.addLane(new Lane(r6.getEndLocation(),r6.getStartLocation(), MapDirection.NORTH));
        r6.addLane(new Lane(r6.getStartLocation(),r6.getEndLocation(), MapDirection.SOUTH));

        r7.addLane(new Lane(r7.getStartLocation(),r7.getEndLocation(), MapDirection.EAST));
        r7.addLane(new Lane(r7.getEndLocation(),r7.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);
        map.addRoad(r5);
        map.addRoad(r6);
        map.addRoad(r7);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        i2.setEastRoad(r5);

        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);

        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);
        i4.setEastRoad(r7);

        Intersection i5 = new Intersection(new Coordinate(17,1));
        i5.setWestRoad(r5);
        i5.setSouthRoad(r6);

        Intersection i6 = new Intersection(new Coordinate(17,9));
        i6.setNorthRoad(r6);
        i6.setWestRoad(r7);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);
        map.addIntersection(i5);
        map.addIntersection(i6);

        return map;
    }

    //Added new Map with two lanes
    public Map drawTestMapExample() throws Exception {


        Map map = new Map(25,25);

        Road r01 = new Road(new Coordinate(2,1), new Coordinate(4,1));
        Road r02 = new Road(new Coordinate(1,2), new Coordinate(1,4));
        Road r03 = new Road(new Coordinate(1,6), new Coordinate(1,9));
        Road r04 = new Road(new Coordinate(1,11), new Coordinate(1,14));
        Road r05 = new Road(new Coordinate(6,1), new Coordinate(12,1));
        Road r06 = new Road(new Coordinate(14,1), new Coordinate(19,1));
        Road r07 = new Road(new Coordinate(20,2), new Coordinate(20,9));
        Road r08 = new Road(new Coordinate(20,11), new Coordinate(20,14));
        Road r09 = new Road(new Coordinate(9,15), new Coordinate(19,15));
        Road r10 = new Road(new Coordinate(2,15), new Coordinate(7,15));
        Road r11 = new Road(new Coordinate(5,2), new Coordinate(5,4));
        Road r12 = new Road(new Coordinate(2,5), new Coordinate(4,5));
        Road r13 = new Road(new Coordinate(2,10), new Coordinate(7,10));
        Road r14 = new Road(new Coordinate(9,10), new Coordinate(12,10));
        Road r15 = new Road(new Coordinate(14,10), new Coordinate(19,10));
        Road r16 = new Road(new Coordinate(8,11), new Coordinate(8,14));
        Road r17 = new Road(new Coordinate(13,2), new Coordinate(13,4));
        Road r18 = new Road(new Coordinate(13,6), new Coordinate(13,9));
        Road r19 = new Road(new Coordinate(6,5),new Coordinate(12,5));
        Road r20 = new Road(new Coordinate(1,16),new Coordinate(1,19));
        Road r21 = new Road(new Coordinate(8,16),new Coordinate(8,19));
        Road r22 = new Road(new Coordinate(2,20),new Coordinate(7,20));
        Road r23 = new Road(new Coordinate(9,20),new Coordinate(19,20));
        Road r24 = new Road(new Coordinate(20,16), new Coordinate(20,19));

        // one Lane

        Lane disabledLane = new Lane(r05.getStartLocation(),r05.getEndLocation(),MapDirection.EAST);

        //disabledLane.setState(0);

        r01.addLane(new Lane(r01.getStartLocation(),r01.getEndLocation(),MapDirection.EAST));
        r02.addLane(new Lane (r02.getEndLocation(),r02.getStartLocation(),MapDirection.NORTH));
        r03.addLane(new Lane(r03.getEndLocation(),r03.getStartLocation(),MapDirection.NORTH));
        r04.addLane(new Lane(r04.getEndLocation(),r04.getStartLocation(),MapDirection.NORTH));
        //r05.addLane(new Lane(r05.getStartLocation(),r05.getEndLocation(),MapDirection.EAST));
        r05.addLane(disabledLane);
        r06.addLane(new Lane(r06.getStartLocation(),r06.getEndLocation(),MapDirection.EAST));
        r07.addLane(new Lane(r07.getStartLocation(),r07.getEndLocation(),MapDirection.SOUTH));
        r08.addLane(new Lane(r08.getStartLocation(),r08.getEndLocation(),MapDirection.SOUTH));
        r09.addLane(new Lane(r09.getEndLocation(),r09.getStartLocation(),MapDirection.WEST));
        r10.addLane(new Lane(r10.getEndLocation(),r10.getStartLocation(),MapDirection.WEST));
        r11.addLane(new Lane(r11.getEndLocation(),r11.getStartLocation(),MapDirection.NORTH));
        r12.addLane(new Lane(r12.getStartLocation(),r12.getEndLocation(),MapDirection.EAST));
        r13.addLane(new Lane(r13.getStartLocation(),r13.getEndLocation(),MapDirection.EAST));
        r14.addLane(new Lane(r14.getStartLocation(),r14.getEndLocation(),MapDirection.EAST));
        r15.addLane(new Lane(r15.getStartLocation(),r15.getEndLocation(),MapDirection.EAST));
        r16.addLane(new Lane(r16.getEndLocation(),r16.getStartLocation(),MapDirection.NORTH));
        r17.addLane(new Lane(r17.getEndLocation(),r17.getStartLocation(),MapDirection.NORTH));
        r18.addLane(new Lane(r18.getEndLocation(),r18.getStartLocation(),MapDirection.NORTH));
        r19.addLane(new Lane(r19.getStartLocation(),r19.getEndLocation(),MapDirection.EAST));
        r20.addLane(new Lane(r20.getEndLocation(),r20.getStartLocation(),MapDirection.NORTH));
        r21.addLane(new Lane(r21.getEndLocation(),r21.getStartLocation(),MapDirection.NORTH));
        r22.addLane(new Lane(r22.getEndLocation(),r22.getStartLocation(),MapDirection.WEST));
        r23.addLane(new Lane(r23.getEndLocation(),r23.getStartLocation(),MapDirection.WEST));
        r24.addLane(new Lane(r24.getStartLocation(),r24.getEndLocation(),MapDirection.SOUTH));

        //two Lanes
/*
        r01.addLane(new Lane(r01.getEndLocation(),r01.getStartLocation(),MapDirection.WEST));
        r02.addLane(new Lane (r02.getStartLocation(),r02.getEndLocation(),MapDirection.SOUTH));
        r03.addLane(new Lane(r03.getStartLocation(),r03.getEndLocation(),MapDirection.SOUTH));
        r04.addLane(new Lane(r04.getStartLocation(),r04.getEndLocation(),MapDirection.SOUTH));
        r05.addLane(new Lane(r05.getEndLocation(),r05.getStartLocation(),MapDirection.WEST));
        r06.addLane(new Lane(r06.getEndLocation(),r06.getStartLocation(),MapDirection.WEST));
        r07.addLane(new Lane(r07.getEndLocation(),r07.getStartLocation(),MapDirection.NORTH));
        r08.addLane(new Lane(r08.getEndLocation(),r08.getStartLocation(),MapDirection.NORTH));
        r09.addLane(new Lane(r09.getStartLocation(),r09.getEndLocation(),MapDirection.EAST));
        r10.addLane(new Lane(r10.getStartLocation(),r10.getEndLocation(),MapDirection.EAST));
        r11.addLane(new Lane(r11.getStartLocation(),r11.getEndLocation(),MapDirection.SOUTH));
        r12.addLane(new Lane(r12.getEndLocation(),r12.getStartLocation(),MapDirection.WEST));
        r13.addLane(new Lane(r13.getEndLocation(),r13.getStartLocation(),MapDirection.WEST));
        r14.addLane(new Lane(r14.getEndLocation(),r14.getStartLocation(),MapDirection.WEST));
        r15.addLane(new Lane(r15.getEndLocation(),r15.getStartLocation(),MapDirection.WEST));
        r16.addLane(new Lane(r16.getStartLocation(),r16.getEndLocation(),MapDirection.SOUTH));
        r17.addLane(new Lane(r17.getStartLocation(),r17.getEndLocation(),MapDirection.SOUTH));
        r18.addLane(new Lane(r18.getStartLocation(),r18.getEndLocation(),MapDirection.SOUTH));
        r19.addLane(new Lane(r19.getEndLocation(),r19.getStartLocation(),MapDirection.WEST));
        r20.addLane(new Lane(r20.getStartLocation(),r20.getEndLocation(),MapDirection.SOUTH));
        r21.addLane(new Lane(r21.getStartLocation(),r21.getEndLocation(),MapDirection.SOUTH));
        r22.addLane(new Lane(r22.getStartLocation(),r22.getEndLocation(),MapDirection.EAST));
        r23.addLane(new Lane(r23.getStartLocation(),r23.getEndLocation(),MapDirection.EAST));
        r24.addLane(new Lane(r24.getEndLocation(),r24.getStartLocation(),MapDirection.NORTH));
*/

        Intersection i01 = new Intersection(new Coordinate(1,1));
        i01.setEastRoad(r01);
        i01.setSouthRoad(r02);

        Intersection i02 = new Intersection(new Coordinate(1,5));
        i02.setNorthRoad(r02);
        i02.setSouthRoad(r03);
        i02.setEastRoad(r12);

        Intersection i03 = new Intersection(new Coordinate(1,10));
        i03.setNorthRoad(r03);
        i03.setSouthRoad(r04);
        i03.setEastRoad(r13);

        Intersection i04 = new Intersection(new Coordinate(1,15));
        i04.setNorthRoad(r04);
        i04.setEastRoad(r10);
        i04.setSouthRoad(r20);

        Intersection i05 = new Intersection(new Coordinate(5,1));
        i05.setWestRoad(r01);
        i05.setEastRoad(r05);
        i05.setSouthRoad(r11);

        Intersection i06 = new Intersection(new Coordinate(13,1));
        i06.setWestRoad(r05);
        i06.setEastRoad(r06);
        i06.setSouthRoad(r17);

        Intersection i07 = new Intersection(new Coordinate(20,1));
        i07.setWestRoad(r06);
        i07.setSouthRoad(r07);

        Intersection i08 = new Intersection(new Coordinate(20,10));
        i08.setNorthRoad(r07);
        i08.setSouthRoad(r08);
        i08.setWestRoad(r15);

        Intersection i09 = new Intersection(new Coordinate(20,15));
        i09.setNorthRoad(r08);
        i09.setWestRoad(r09);
        i09.setSouthRoad(r24);

        Intersection i10 = new Intersection(new Coordinate(8,15));
        i10.setEastRoad(r09);
        i10.setWestRoad(r10);
        i10.setNorthRoad(r16);
        i10.setSouthRoad(r21);

        Intersection i11 = new Intersection(new Coordinate(5,5));
        i11.setWestRoad(r12);
        i11.setNorthRoad(r11);
        i11.setEastRoad(r19);

        Intersection i12 = new Intersection(new Coordinate(8,10));
        i12.setWestRoad(r13);
        i12.setEastRoad(r14);
        i12.setSouthRoad(r16);

        Intersection i13 = new Intersection(new Coordinate(13,10));
        i13.setWestRoad(r14);
        i13.setEastRoad(r15);
        i13.setNorthRoad(r18);

        Intersection i14 =new Intersection((new Coordinate(13,5)));
        i14.setNorthRoad(r17);
        i14.setSouthRoad(r18);
        i14.setWestRoad(r19);

        Intersection i15 =new Intersection(new Coordinate(1,20));
        i15.setNorthRoad(r20);
        i15.setEastRoad(r22);

        Intersection i16 =new Intersection(new Coordinate(8,20));
        i16.setWestRoad(r22);
        i16.setNorthRoad(r21);
        i16.setEastRoad(r23);

        Intersection i17= new Intersection (new Coordinate(20,20));
        i17.setWestRoad(r23);
        i17.setNorthRoad(r24);



        map.addRoad(r01);
        map.addRoad(r02);
        map.addRoad(r03);
        map.addRoad(r04);
        map.addRoad(r05);
        map.addRoad(r06);
        map.addRoad(r07);
        map.addRoad(r08);
        map.addRoad(r09);
        map.addRoad(r10);
        map.addRoad(r11);
        map.addRoad(r12);
        map.addRoad(r13);
        map.addRoad(r14);
        map.addRoad(r15);
        map.addRoad(r16);
        map.addRoad(r17);
        map.addRoad(r18);
        map.addRoad(r19);
        map.addRoad(r20);
        map.addRoad(r21);
        map.addRoad(r22);
        map.addRoad(r23);
        map.addRoad(r24);

        map.addIntersection(i01);
        map.addIntersection(i02);
        map.addIntersection(i03);
        map.addIntersection(i04);
        map.addIntersection(i05);
        map.addIntersection(i06);
        map.addIntersection(i07);
        map.addIntersection(i08);
        map.addIntersection(i09);
        map.addIntersection(i10);
        map.addIntersection(i11);
        map.addIntersection(i12);
        map.addIntersection(i13);
        map.addIntersection(i14);
        map.addIntersection(i15);
        map.addIntersection(i16);
        map.addIntersection(i17);

        return map;
    }



    public static void main(String[] args) {
        launch(args);
    }
}