package londonsw.model.simulation;

import londonsw.model.simulation.components.TrafficLight;
import londonsw.model.simulation.components.vehicles.Vehicle;
import rx.Subscriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


 /**
 * Created by felix on 18/03/2016.
  * Logs what is happening in the system for every tick.
 */

public class Log extends Subscriber<Long> {

     private String fileName;
     private final String LOG_DIR = "./logs/";
     private String filePath;

     /**
      * Creates a log with the given file name to log what is happening in the system. It will be stored in the
      * directory LOG_DIR.
      * @param fileName the file name for the log
      */
     public Log(String fileName) {
         this.fileName = fileName;

         File directory = new File(LOG_DIR);
         if(!directory.exists()) {
             directory.mkdir();
         }

         filePath = LOG_DIR + fileName + ".log";
         File logFile = new File(filePath);
         if(!logFile.exists()) {
             try {
                 logFile.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

         Ticker.subscribe(this);
     }

     /**
      * Generates log information for that Ticker tick
      * @param aLong current time in the system
      */
     private void generate(long aLong) {

         ArrayList al = Ticker.getSubscribers();

         Logger logger = Logger.getLogger("SIMULATION");

         FileHandler fh;

         try {

             StringBuilder sb = new StringBuilder();

             sb.append("TICK!: " + aLong);
             sb.append(System.lineSeparator());

             for (Object o : al) {
                 if (o instanceof Vehicle) {

                     Vehicle vLog = (Vehicle) o;

                     sb.append(System.lineSeparator());
                     sb.append("---------VEHICLE---------");
                     sb.append(System.lineSeparator());
                     sb.append("ID: " + vLog.getId());
                     sb.append(System.lineSeparator());
                     sb.append("CURRENT LANE ID: " + vLog.getCurrentLane().getId());
                     sb.append(System.lineSeparator());
                     sb.append("CURRENT COORDINATES: " + vLog.getCurrentCoordinate().getX() + "," + vLog.getCurrentCoordinate().getY());
                     sb.append(System.lineSeparator());

                     if (vLog.getPreviousLane() != null) {
                         sb.append("PREVIOUS LANE ID: " + vLog.getPreviousLane().getId());
                         sb.append(System.lineSeparator());
                         sb.append("PREVIOUS LANE COORDINATES: " + vLog.getPreviousCoordinate().getX() + "," + vLog.getCurrentCoordinate().getY());
                         sb.append(System.lineSeparator());
                     }

                     sb.append("CURRENT CELL: " + vLog.getCurrentCell());
                     sb.append(System.lineSeparator());
                     sb.append("BEHAVIOUR: " + vLog.getVehicleBehavior());
                     sb.append(System.lineSeparator());
                     sb.append("PRIORITY: " + vLog.getVehiclePriority());
                     sb.append(System.lineSeparator());
                     sb.append("STATE: " + vLog.getVehicleState());

                 } else if (o instanceof TrafficLight) {
                     TrafficLight tLog = (TrafficLight) o;
                     sb.append(System.lineSeparator());
                     sb.append("------TRAFFIC LIGHT------");
                     sb.append(System.lineSeparator());
                     sb.append("ID: " + tLog.getId());
                     sb.append(System.lineSeparator());
                     sb.append("DURATION: " + tLog.getDuration());
                     sb.append(System.lineSeparator());
                     sb.append("STATE: " + tLog.getState());
                 }
             }

             sb.append(System.lineSeparator());
             sb.append("============================================================");

             fh = new FileHandler(filePath,true);

             logger.addHandler(fh);
             SimpleFormatter formatter = new SimpleFormatter();
             fh.setFormatter(formatter);
             logger.setUseParentHandlers(false);
             logger.info(sb.toString());
             fh.close();

         } catch (SecurityException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     @Override
     public void onCompleted() {  }

     /**
      * If there's some error with the ticker and this subscriber, this method would call.
      * Left not implemented on purpose
      * @param throwable
      */
     @Override
     public void onError(Throwable throwable) {    }

     /**
      * Called on Ticker tick, will generated a log entry for that tick
      * @param aLong current time in the sytem
      */
     @Override
     public void onNext(Long aLong) {
        generate(aLong);
     }
 }