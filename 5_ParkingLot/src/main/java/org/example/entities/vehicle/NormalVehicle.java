package org.example.entities.vehicle;

import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

public class NormalVehicle extends Vehicle {
    public NormalVehicle(VehicleSizeType vehicleType) {
        super(vehicleType, VehicleKind.NORMAL);
    }
}
