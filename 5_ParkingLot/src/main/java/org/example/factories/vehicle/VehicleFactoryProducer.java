package org.example.factories.vehicle;

import org.example.enums.VehicleKind;

public class VehicleFactoryProducer {


    // this is an abstract factory. It does not make sense for the subfactories produced to have
    // static getMaterial() -> since all the subfactory children will implement from an interface.
    // and static method of the interface/class cannot be overridden!

    public static VehicleFactory generateVehicleFactory(VehicleKind vehicleKind) {
        switch (vehicleKind) {
            case NORMAL : return new NormalVehicleFactory();
            case ELECTRIC: return new ElectricVehicleFactory();
            default: throw new IllegalArgumentException("Invalid vehicle factory type passed");
        }
    }
}