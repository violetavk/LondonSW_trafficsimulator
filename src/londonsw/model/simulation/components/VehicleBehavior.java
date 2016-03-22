package londonsw.model.simulation.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This enum is to represent different types of behaviours vehicles can have. For instance, a Car might have average
 * behaviour, but an Ambulance has aggressive behaviour.
 */
public enum VehicleBehavior{
    AVERAGE, CAUTIOUS, AGGRESSIVE;

    private static final java.util.List<VehicleBehavior> VALUES= Collections.unmodifiableList(Arrays.asList(values()));
    private static final int size = VALUES.size();
    private static  final Random RANDOM= new Random();
    public static VehicleBehavior randomLetter(){
        return VALUES.get(RANDOM.nextInt(size));
    }
}
