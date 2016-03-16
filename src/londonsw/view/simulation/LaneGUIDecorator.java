package londonsw.view.simulation;

import javafx.scene.shape.Polygon;
import londonsw.model.simulation.components.Lane;

/**
 * Created by felix on 16/03/2016.
 */
public class LaneGUIDecorator extends LaneDecorator {

    public LaneGUIDecorator(Lane decoratedLane) {
        super(decoratedLane);
    }

    public Polygon getArrow() {
        return arrow;
    }

    public void setArrow(Polygon arrow) {
        this.arrow = arrow;
    }

    private Polygon arrow;

}
