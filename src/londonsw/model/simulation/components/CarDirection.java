package londonsw.model.simulation.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by violet on 12/02/2016.
 */
public enum CarDirection {
    STRAIGHT, LEFT, RIGHT;


    //To pick a direction randomly
    private static final List<CarDirection> VALUES= Collections.unmodifiableList(Arrays.asList(values()));
    private static final int size = VALUES.size();
    private static  final Random RANDOM= new Random();
    public static CarDirection randomLetter(){
        return VALUES.get(RANDOM.nextInt(size));
    }



}
