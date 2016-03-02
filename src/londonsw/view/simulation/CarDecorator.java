package londonsw.view.simulation;

import londonsw.model.simulation.components.vehicles.Car;
import londonsw.model.simulation.components.vehicles.ICar;

/**
 * Created by felix on 26/02/2016.
 */
public abstract class CarDecorator implements ICar {

    protected Car decoratedCar;

    public CarDecorator(Car decoratedCar) {
        this.decoratedCar = decoratedCar;
    }


    @Override
    public int getCarId() {
        return this.decoratedCar.getCarId();
    }
}