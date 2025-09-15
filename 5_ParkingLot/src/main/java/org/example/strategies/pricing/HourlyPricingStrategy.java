package org.example.strategies.pricing;

import org.example.entities.Ticket;

public class HourlyPricingStrategy implements PricingStrategy {
    @Override
    public double computeCost(Ticket ticket) {
        long durationMillis = System.currentTimeMillis() - ticket.getStartTime();
        long hours = Math.max(1, durationMillis/(60 * 60 * 1000));
        return hours * ticket.getParkingSpot().getBasePrice();
    }
}
