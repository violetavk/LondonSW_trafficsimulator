package londonsw.model.simulation;

import londonsw.model.simulation.components.Component;

public class MapGrid {

    private int width;
    private int height;
    private Component[][] grid;

    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Component[width][height];
    }



}
