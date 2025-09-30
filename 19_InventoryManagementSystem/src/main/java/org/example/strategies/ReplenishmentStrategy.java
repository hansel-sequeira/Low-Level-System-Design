package org.example.strategies;

public interface ReplenishmentStrategy {
    void replenish(String productID, String warehouseID);
}
