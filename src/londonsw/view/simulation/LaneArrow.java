package londonsw.view.simulation;

import javafx.scene.shape.Polygon;

/**
 * Created by felix on 16/03/2016.
 */
public class LaneArrow extends Polygon {

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    private String laneId;

}
