package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.entities.parkingSpot.ParkingSpot;
import org.example.entities.vehicle.Vehicle;
import org.example.enums.PricingStrategyType;
import org.example.strategies.pricing.PricingStrategy;

@Getter
@Setter
@AllArgsConstructor
public class Ticket {
    private String ticketID;
    private Vehicle vehicle; // has access to vehicleKind and vehicleSize
    private long startTime;
    private ParkingSpot parkingSpot;
    private PricingStrategyType pricingStrategyType;
}
