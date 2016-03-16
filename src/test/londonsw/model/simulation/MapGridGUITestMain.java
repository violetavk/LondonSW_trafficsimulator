package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.ResizeFactor;
import londonsw.model.simulation.components.vehicles.Ambulance;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.view.simulation.VehicleGUIDecorator;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Map map = MapExamples.drawMap1_1();

//        Map map = MapExamples.drawTestMapExample();
        Map map = Map.loadMap("BigMap.map");

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor((map.getWidth()/(5*1.0)) / width, (map.getHeight()/(5*1.0)) / height));    //TODO HARDCODE

        //mapGridGUIDecorator.setResizeFactor(new ResizeFactor(1,1));

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(.25,.25));

        GridPane rootGP = mapGridGUIDecorator.drawComponents();
//        map.saveMap("BigMap.map");
        /* NOTE: Saving a map can only be called once all components have been drawn */


        /**
         *  Ambulance inherits from vehicle
         */
        Lane al = map.getRandomLane();
        Ambulance a = new Ambulance(0,al);
        VehicleGUIDecorator ambulanceGUIDecorator = new VehicleGUIDecorator(a);
        ambulanceGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
        ambulanceGUIDecorator.setColor(Color.RED);
        ambulanceGUIDecorator.drawCar();
        Pane alPane = new Pane();
        alPane.getChildren().add(ambulanceGUIDecorator.getRectangle());
        //sp.getChildren().add(alPane);
        ambulanceGUIDecorator.setVehicleState(1);

        /**
         * We would have a button to spawn an ambulance: single click deploys the ambulance and double click removes it.
         * Button button = new Button("spawn ambulance"); button.setOnMouseClicked(event -> {do some action})
         * As we don't have the buttons yet, stack pane is used to demonstrate
         */
      /*  sp.setOnMouseClicked(event -> {

            switch (event.getClickCount()){
                case 1:
                    sp.getChildren().add(alPane);
                break;

                case 2:
                    sp.getChildren().remove(alPane);
                break;

            }

        });
*/

       // System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());

        StackPane sp = new StackPane();
        sp.getChildren().add(rootGP);

        Car testCar;
        int c=0;
        for (int i=0; i<1; i++){
            testCar = generateCar(map,mapGridGUIDecorator,sp);
            if (testCar!=null)
                c++;}
        System.out.println("Number of cars is "+ c);

        /**
         * We can now use a single button to spawn and un-spawn the ambulance
         */
        sp.setOnMouseClicked(event -> {if(sp.getChildren().contains(alPane)){
            sp.getChildren().remove(alPane);}
        else{
            sp.getChildren().add(alPane);
        };
        });


        Scene scene = new Scene(sp);
        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);

        //primaryStage.setFullScreen(true);

    }



    public Car generateCar(Map map, MapGridGUIDecorator mapGridGUIDecorator, StackPane sp){

        Lane L1 =map.getRandomLane();
//<<<<<<< HEAD
//        //Lane L1 =map.getRoads().get(0).getLanes().get(1);
//
//        Lane L2;
//
//        if(L1!=null)
//        for (int a=0; a<map.getRoads().size();a++)
//        {
//            for(int b=0; b<map.getRoads().get(a).getNumberLanes();b++)
//            {
//                L2= map.getRoads().get(a).getLanes().get(b);
//                for (int i=0; i<L1.getLength();i++) {
//                    if (L1.isCellEmpty(i)) {
//                        Car C1 = new Car(i, L1);
//                        //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
//                        VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
//                        vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
//                        vehicleGUIDecorator.drawCar();
//                        Pane carPane = new Pane();
//
//                        carPane.setPickOnBounds(false); //allows me to click intersections
//
//                        carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
//                        //carPane.getChildren().add(vehicleGUIDecorator.getGroup());
//                        sp.getChildren().add(carPane);
//                        vehicleGUIDecorator.setVehicleState(1);
//                        System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
//                        return C1;
//
//=======
       // Lane L1 =map.getRoads().get(0).getLanes().get(0);

        Lane L2;

        if(L1!=null && (!L1.isFull())) {
            for (int a = 0; a < map.getRoads().size(); a++) {
                for (int b = 0; b < map.getRoads().get(a).getNumberLanes(); b++) {
                    // L2= map.getRoads().get(a).getLanes().get(b);
                    //L1=map.getRoads().get(8).getLanes().get(1);
                    L1 = map.getRandomLane();
                    for (int i = 0; i < L1.getLength(); i++) {
                        if (L1.isCellEmpty(i)) {
                            Car C1 = new Car(i, L1);
                            //C1.setVehicleBehavior(VehicleBehavior.AGGRESSIVE);
                            VehicleGUIDecorator vehicleGUIDecorator = new VehicleGUIDecorator(C1);
                            vehicleGUIDecorator.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
                            vehicleGUIDecorator.drawCar();
                            Pane carPane = new Pane();
                            carPane.getChildren().add(vehicleGUIDecorator.getRectangle());
                            sp.getChildren().add(carPane);
                            vehicleGUIDecorator.setVehicleState(1);
                            System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY());
                            return C1;

                        }
//>>>>>>> RawanMoh-master
                    }
                }
            }
        }
        
        return null;

    }


        public static void main(String[] args) {
        launch(args);
    }
}