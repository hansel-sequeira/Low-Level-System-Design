package org.example.strategies.pricing;

import org.example.entities.Ticket;

public interface PricingStrategy {
    double computeCost(Ticket ticket);
}
