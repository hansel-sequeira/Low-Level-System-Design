package org.example.factories.vehicle;

import org.example.entities.vehicle.Vehicle;
import org.example.enums.VehicleSizeType;

public interface VehicleFactory {
    Vehicle generateVehicle(VehicleSizeType vehicleSizeType);
}
