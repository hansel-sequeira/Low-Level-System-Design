package org.example.factories.vehicle;

import org.example.entities.vehicle.ElectricVehicle;
import org.example.entities.vehicle.Vehicle;
import org.example.enums.VehicleSizeType;

public class ElectricVehicleFactory implements VehicleFactory {
    public Vehicle generateVehicle(VehicleSizeType vehicleSizeType) {
        switch(vehicleSizeType) {
            case SMALL : return new ElectricVehicle(VehicleSizeType.SMALL);
            case MEDIUM : return new ElectricVehicle(VehicleSizeType.MEDIUM);
            default: throw new IllegalArgumentException("Invalid vehicle size type");
        }
    }
}
