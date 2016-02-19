package londonsw.model.simulation.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by violet on 12/02/2016.
 */
/*public enum VehicleBehavior {
    STRAIGHT, LEFT, RIGHT;


    //To pick a direction randomly
    private static final List<VehicleBehavior> VALUES= Collections.unmodifiableList(Arrays.asList(values()));
    private static final int size = VALUES.size();
    private static  final Random RANDOM= new Random();
    public static VehicleBehavior randomLetter(){
        return VALUES.get(RANDOM.nextInt(size));
    }



}*/
//give vehicle Behavior randomly

public enum VehicleBehavior{
    AVERAGE, CAUTIOUS, AGGRESSIVE;

    private static final java.util.List<VehicleBehavior> VALUES= Collections.unmodifiableList(Arrays.asList(values()));
    private static final int size = VALUES.size();
    private static  final Random RANDOM= new Random();
    public static VehicleBehavior randomLetter(){
        return VALUES.get(RANDOM.nextInt(size));
    }
}
