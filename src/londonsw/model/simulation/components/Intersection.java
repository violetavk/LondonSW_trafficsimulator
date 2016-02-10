package londonsw.model.simulation.components;

/**
 * This class is our "node" in our directed graph
 * It will hold anywhere between 1 and 4 traffic lights
 * It will connect anywhere between 2 and 4 roads
 * Each will have a location in the map
 */

/* the traffic light belongs to the road
 * in each intersection, a can can choose(maybe randomly) Which road he can enter based on the array IntersectionRoad
 */

public class Intersection {

    int[] IntersectionRoad; // the array stores all the roadsID for the intersection

    int x,y;// location

    //xPos x coordinate of the intersection
    //yPos y coordinate of the intersection

    public Intersection(int xPos,int yPos){
        this.x=xPos;
        this.y=yPos;
    }

    public void JoinRoad(Road R1,Road R2){//Add ID of R1 and R1 into the array IntersectionRoad

    }

}
