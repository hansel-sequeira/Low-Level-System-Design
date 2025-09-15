package org.example.entities.parkingSpot;

import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

import java.util.UUID;

public class NormalParkingSpot extends ParkingSpot {
    public NormalParkingSpot(VehicleSizeType vehicleSizeType, double basePrice) {
        super(vehicleSizeType, VehicleKind.NORMAL, basePrice);
    }
}
