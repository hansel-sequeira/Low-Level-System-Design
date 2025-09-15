package org.example.entities.gate;

import lombok.Getter;
import org.example.entities.Ticket;
import org.example.entities.parkingSpot.ParkingSpot;
import org.example.entities.vehicle.Vehicle;
import org.example.enums.GateType;
import org.example.enums.PricingStrategyType;
import org.example.strategies.parking.ParkingSpotFinderStrategy;

import java.util.UUID;

@Getter
public class EntryGate extends Gate{
    public EntryGate(int gateNumber) {
        super(gateNumber, GateType.ENTRY);
    }
                                            // ideally user passes an enum of parking spot finder strategy
    public Ticket registerEntry(Vehicle vehicle, ParkingSpotFinderStrategy parkingSpotFinderStrategy,
                         PricingStrategyType pricingStrategyType) {
        ParkingSpot parkingSpot = parkingSpotFinderStrategy.generateSpot(vehicle.getVehicleKind(), vehicle.getVehicleSizeType());
        Ticket ticket = new Ticket(UUID.randomUUID().toString(),
                vehicle, System.currentTimeMillis(), parkingSpot, pricingStrategyType);
        parkingSpot.setOccupied(true);
        return ticket;
    }
}
