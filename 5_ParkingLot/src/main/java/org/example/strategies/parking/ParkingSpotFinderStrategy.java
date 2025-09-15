package org.example.strategies.parking;

import org.example.entities.parkingSpot.ParkingSpot;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

public interface ParkingSpotFinderStrategy {
    ParkingSpot generateSpot(VehicleKind vehicleKind, VehicleSizeType vehicleSizeType);
}
