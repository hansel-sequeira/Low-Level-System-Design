package org.example.strategies;

import org.example.entities.Reservation;

public interface RentalStrategy {
    int computeCost(Reservation reservation);
}
