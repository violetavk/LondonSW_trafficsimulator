package londonsw.view.simulation;

import londonsw.model.simulation.components.*;

import java.util.ArrayList;

/**
 * Created by felix on 25/02/2016.
 */
public abstract class RoadDecorator implements IRoad {

    protected Road decoratedRoad;

    public RoadDecorator(Road decoratedRoad) {
        this.decoratedRoad = decoratedRoad;
    }

    @Override
    public ArrayList<Lane> getLanes() {
        return this.decoratedRoad.getLanes();
    }

    @Override
    public void addLane(Lane lane) {
        this.decoratedRoad.addLane(lane);
    }

    @Override
    public Lane getLaneAtIndex(int index) {
        return this.decoratedRoad.getLaneAtIndex(index);
    }

    @Override
    public Coordinate getEndLocation() {
        return this.decoratedRoad.getEndLocation();
    }

    @Override
    public int getNumberLanes() {
        return decoratedRoad.getNumberLanes();
    }

    @Override
    public Intersection getIntersection() {
        return this.decoratedRoad.getIntersection();
    }

    @Override
    public void setIntersection(Intersection intersection) {
        this.decoratedRoad.setIntersection(intersection);
    }

    @Override
    public int getLength() {
        return this.decoratedRoad.getLength();
    }

    @Override
    public boolean runsVertically() {
        return this.decoratedRoad.runsVertically();
    }

    @Override
    public String getRoadId() {
        return this.decoratedRoad.getRoadId();
    }

    @Override
    public void setRoadId(String roadId) {
        decoratedRoad.setRoadId(roadId);
    }
}