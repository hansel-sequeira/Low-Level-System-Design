package org.example.entities.parkingSpot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

import java.util.UUID;

// makes sense for this to be an abstract class with children inheriting to account for
// additional state/behavior for certain kinds of parking spots (VIP reserved, handicap reserved,
// electric charging spot, etc.

@Getter
@Setter
public abstract class ParkingSpot {
    protected String parkingSpotID;
    protected VehicleSizeType vehicleSizeType;
    protected VehicleKind vehicleKind;
    protected boolean isOccupied;
    protected double basePrice;

    protected ParkingSpot(VehicleSizeType vehicleSizeType, VehicleKind vehicleKind, double basePrice) {
        this.parkingSpotID = UUID.randomUUID().toString();
        this.vehicleKind = vehicleKind;
        this.vehicleSizeType = vehicleSizeType;
        this.isOccupied = false;
        this.basePrice = basePrice;
    }
}
