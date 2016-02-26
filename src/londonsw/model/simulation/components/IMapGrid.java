package londonsw.model.simulation.components;

/**
 * Created by felix on 25/02/2016.
 */
public interface IMapGrid {

    Component[][] getGrid();

    int getWidth();

    int getHeight();

    boolean addComponent(Component component);

}
