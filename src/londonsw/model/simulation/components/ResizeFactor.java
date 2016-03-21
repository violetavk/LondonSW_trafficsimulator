package londonsw.model.simulation.components;

/**
 * Created by felix on 28/02/2016.
 */
public class ResizeFactor {

    private double resizeX;
    private double resizeY;

    public ResizeFactor(double resizeX, double resizeY) {

        this.resizeX = resizeX;
        this.resizeY = resizeY;
    }

    /**
     * Desc
     * @return
     */
    public double getResizeX() {
        return resizeX;
    }

    /**
     *
     * @param resizeX
     */
    public void setResizeX(double resizeX) {
        this.resizeX = resizeX;
    }

    public double getResizeY() {
        return resizeY;
    }

    public void setResizeY(double resizeY) {
        this.resizeY = resizeY;
    }

//    public static ResizeFactor getSuggestedResizeFactor(int mapWidth, int mapHeight) {
//        double resizeFactor = 1;
//         // TODO just figure out some formula.. maybe.. make sure simulation doesn't break
//
//        if(mapWidth >= 5 && mapWidth <= 15) {
//            if(mapHeight >= 5 && mapHeight <= 7) {
//                resizeFactor = 0.7;
//            }
//            if(mapHeight >= 8 && mapHeight <= 9) {
//                resizeFactor = 0.6;
//            }
//            if(mapHeight >= 10 && mapHeight <= 12) {
//                resizeFactor = 0.5;
//            }
//            if(mapHeight >= 13 && mapHeight <= 15) {
//                resizeFactor = 0.4;
//            }
//            if(mapHeight >= 16 && mapHeight <= 18) {
//                resizeFactor = 0.35;
//            }
//            if(mapHeight >= 19 && mapHeight <= 22) {
//                resizeFactor = 0.3;
//            }
//            if(mapHeight >= 23 && mapHeight <= 26) {
//                resizeFactor = .25;
//            }
//            if(mapHeight >= 27 && mapHeight <= 30) {
//                resizeFactor = 0.22;
//            }
//            if(mapHeight >= 31 && mapHeight <= 33) {
//                resizeFactor = 0.2;
//            }
//            if(mapHeight >= 34 && mapHeight <= 38) {
//                resizeFactor = 0.17;
//            }
//            if(mapHeight >= 39 && mapHeight <= 40) {
//                resizeFactor = 0.15;
//            }
//        }
//        if(mapWidth >= 16 && mapWidth <= 20) {
//            if(mapHeight >= 5 && mapHeight <= 7) {
//                resizeFactor = 0.6;
//            }
//            if(mapHeight >= 8 && mapHeight <= 9) {
//                resizeFactor = 0.6;
//            }
//            if(mapHeight >= 10 && mapHeight <= 12) {
//                resizeFactor = 0.5;
//            }
//            if(mapHeight >= 13 && mapHeight <= 15) {
//                resizeFactor = 0.4;
//            }
//            if(mapHeight >= 16 && mapHeight <= 18) {
//                resizeFactor = 0.35;
//            }
//            if(mapHeight >= 19 && mapHeight <= 22) {
//                resizeFactor = 0.3;
//            }
//            if(mapHeight >= 23 && mapHeight <= 26) {
//                resizeFactor = .25;
//            }
//            if(mapHeight >= 27 && mapHeight <= 30) {
//                resizeFactor = 0.22;
//            }
//            if(mapHeight >= 31 && mapHeight <= 33) {
//                resizeFactor = 0.2;
//            }
//            if(mapHeight >= 34 && mapHeight <= 38) {
//                resizeFactor = 0.17;
//            }
//            if(mapHeight >= 39 && mapHeight <= 40) {
//                resizeFactor = 0.15;
//            }
//        }
//
//        return new ResizeFactor(resizeFactor,resizeFactor);
//    }

    public String toString() {
        return resizeX+","+resizeY;
    }
}
