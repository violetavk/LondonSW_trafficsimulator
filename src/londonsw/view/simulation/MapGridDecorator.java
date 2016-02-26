package londonsw.view.simulation;

import londonsw.model.simulation.MapGrid;
import londonsw.model.simulation.components.Component;
import londonsw.model.simulation.components.IMapGrid;

/**
 * Created by felix on 25/02/2016.
 */
public abstract class MapGridDecorator implements IMapGrid {
    protected MapGrid decoratedMapGrid;

    public MapGridDecorator(MapGrid decoratedMapGrid) {
        this.decoratedMapGrid = decoratedMapGrid;
    }

    @Override
    public Component[][] getGrid() {
        return this.decoratedMapGrid.getGrid();
    }

    @Override
    public int getWidth() {
        return this.decoratedMapGrid.getWidth();
    }

    @Override
    public int getHeight() {
        return this.decoratedMapGrid.getHeight();
    }

    @Override
    public boolean addComponent(Component component) {
        return this.decoratedMapGrid.addComponent(component);
    }
}