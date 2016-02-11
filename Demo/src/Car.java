import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by santiago on 10/02/16.
 */
public class Car {

    int widthCar, heightCar;
    Color colorCar;

    public Car(int width, int height, Color color) {
        this.widthCar = width;
        this.heightCar = height;
        this.colorCar = color;
    }
}

class AnimatedCar extends Car
{
    public AnimatedCar(int width, int height, Color color) {
        super(width, height, color);
    }

    public BufferedImage MovingCar()
    {
        int w = this.widthCar;
        int h = this.heightCar;

        BufferedImage image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(this.colorCar);
        g.fillRect(0,0,w,h);
        g.dispose();

        return image;
    }
}