package org.example.factories.vehicle;

import org.example.entities.vehicle.NormalVehicle;
import org.example.entities.vehicle.Vehicle;
import org.example.enums.VehicleSizeType;

public class NormalVehicleFactory implements VehicleFactory {
    public Vehicle generateVehicle(VehicleSizeType vehicleSizeType) {
        switch(vehicleSizeType) {
            case SMALL : return new NormalVehicle(VehicleSizeType.SMALL);
            case MEDIUM : return new NormalVehicle(VehicleSizeType.MEDIUM);
            default: throw new IllegalArgumentException("Invalid vehicle size type");
        }
    }
}
