package org.example.strategies.impl;

import org.example.entities.Reservation;
import org.example.strategies.RentalStrategy;

public class HourlyRentalStrategy implements RentalStrategy {
    @Override
    public int computeCost(Reservation reservation) {
        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - reservation.getStartTime();
        int daycount = (int) Math.min(durationMillis/(24 * 60 * 60 * 1000), 1);
        return reservation.getVehicle().getBasePrice() * daycount;
    }
}
