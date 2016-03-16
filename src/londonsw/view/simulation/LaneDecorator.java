package londonsw.view.simulation;

import londonsw.model.simulation.components.Lane;
import londonsw.model.simulation.components.vehicles.ILane;

/**
 * Created by felix on 16/03/2016.
 */
public abstract class LaneDecorator implements ILane {

    protected Lane decoratedLane;

    public LaneDecorator(Lane decoratedLane) {
        this.decoratedLane = decoratedLane;
    }

    @Override
    public String getLaneID() {
        return decoratedLane.getLaneID();
    }

    @Override
    public void setLaneID(String laneID) {
        decoratedLane.setLaneID(laneID);
    }
}