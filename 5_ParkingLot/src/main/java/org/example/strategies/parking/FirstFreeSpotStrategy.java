package org.example.strategies.parking;

import lombok.Setter;
import org.example.entities.ParkingLot;
import org.example.entities.parkingSpot.ParkingSpot;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

import java.util.Optional;

public class FirstFreeSpotStrategy implements ParkingSpotFinderStrategy{
    // just checks if VehicleSizeType and VehicleKind match. So it doesn't make sense to have
    // FirstFreeElectricSpotStrategy and FirstFreeNormalSpotStrategy classes.

    private static final FirstFreeSpotStrategy firstFreeSpotStrategy = new FirstFreeSpotStrategy();
    private static ParkingLot parkingLot;

    private FirstFreeSpotStrategy() {}
    public static FirstFreeSpotStrategy getInstance(ParkingLot parkingLot) {
        FirstFreeSpotStrategy.parkingLot = parkingLot;
        return firstFreeSpotStrategy;
    }

    @Override
    public ParkingSpot generateSpot(VehicleKind vehicleKind, VehicleSizeType vehicleSizeType) {
        Optional<ParkingSpot> parkingSpotOptional = parkingLot.getParkingSpots().values().stream().filter(parkingSpot ->
                parkingSpot.getVehicleSizeType().equals(vehicleSizeType) &&
                parkingSpot.getVehicleKind().equals(vehicleKind) && !parkingSpot.isOccupied()).findFirst();
        if(parkingSpotOptional.isEmpty())
            throw new RuntimeException("No parking spot found");
        else return parkingSpotOptional.get();
    }
}
