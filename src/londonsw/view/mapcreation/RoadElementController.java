package londonsw.view.mapcreation;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by LiuJia on 3/4/16.
 */
public class RoadElementController{

   public void RoadElement() throws IOException {
       Parent mapCreationScreen = FXMLLoader.load(getClass().getResource("../view/mapCreation/MapCreation.fxml"));
       ImageView roadImage = (ImageView) mapCreationScreen.lookup("#Grass");
       roadImage.setOnDragDetected(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               Dragboard db=roadImage.startDragAndDrop(TransferMode.ANY);
               ClipboardContent content=new ClipboardContent();
               content.putString(roadImage.getId());

               event.consume();
           }
       });
   }

}
