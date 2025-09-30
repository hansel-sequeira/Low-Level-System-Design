package org.example.strategies.impl;

import org.example.strategies.ReplenishmentStrategy;

public class TransferFromWarehouseReplStrategy implements ReplenishmentStrategy {
    @Override
    public void replenish(String productID, String warehouseID) {
        System.out.println("Transferring excess supply of product: " + productID + " across inventories.");
    }
}
