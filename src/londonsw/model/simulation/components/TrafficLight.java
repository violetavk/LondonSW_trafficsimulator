package londonsw.model.simulation.components;
/**
 * Created by yakubu on 19/02/2016.
 */
public class TrafficLight {

        LightColour state = LightColour.RED;

    /**
     *
     */
        public void nextState() {
            switch (state) {
                case RED:
                    state = LightColour.GREEN;
                    break;
                case YELLOW:
                    state = LightColour.RED;
                    break;
                case GREEN:
                    state = LightColour.YELLOW;
                    break;
                default:
                    state = LightColour.RED;
                    break;
            }
        }

    /**
     * @param no
     */
        public void change(int no) {
            for (int i = 0; i < no; i++) {
                nextState();
            }
        }
    }

