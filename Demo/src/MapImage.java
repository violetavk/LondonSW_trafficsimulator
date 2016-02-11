import javax.swing.*;

/**
 * Created by santiago on 10/02/16.
 */
public class MapImage {

    String mapImageLocation;
    int mapImageheight, mapImagewidth;

    public MapImage(String imageLocation) {

        this.mapImageLocation = imageLocation;

    }

    public ImageIcon getMapImageDimensions() {

        ImageIcon mapIcon;

        try {
            mapIcon = new ImageIcon(this.getClass().getResource(this.mapImageLocation));

            this.mapImageheight = mapIcon.getIconHeight();
            this.mapImagewidth = mapIcon.getIconWidth();

            return mapIcon;

        } catch (Exception ex) {
            System.err.println("Can't load \"" + this.mapImageLocation + "\"");
            return new ImageIcon();
        }
    }
}
