package org.example.controllers;

import org.example.entities.Warehouse;
import org.example.strategies.ReplenishmentStrategy;

import java.util.List;

public class WarehouseController {
    private static final WarehouseController warehouseController = new WarehouseController();
    private List<Warehouse> warehouseList;

    private WarehouseController() {}
    public static WarehouseController getInstance() {
        return warehouseController;
    }

    public void setReplenishmentStrategy(ReplenishmentStrategy replenishmentStrategy) {
        warehouseController.setReplenishmentStrategy(replenishmentStrategy);
    }

    public void getInventoryState() {
        for(Warehouse warehouse: warehouseList)
            warehouse.displayWarehouseState();
    }

    public void consumeProduct(String productID, String warehouseID, int qty) {
        Warehouse warehouse = warehouseList.stream().filter(w -> w.getWarehouseID().equals(warehouseID)).findFirst().get();
        warehouse.consumeProduct(productID, qty);
    }
}
