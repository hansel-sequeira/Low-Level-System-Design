package org.example.entities.parkingSpot;

import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

import java.util.UUID;

public class ElectricParkingSpot extends ParkingSpot{

    public void chargeVehicle() {
        System.out.println("Charging the electric vehicle");
    }

    public ElectricParkingSpot(VehicleSizeType vehicleSizeType, double basePrice) {
        super(vehicleSizeType, VehicleKind.ELECTRIC, basePrice);
    }
}
