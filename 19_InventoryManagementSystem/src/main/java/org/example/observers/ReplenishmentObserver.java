package org.example.observers;

import lombok.Getter;
import lombok.Setter;
import org.example.strategies.ReplenishmentStrategy;

@Getter
@Setter
public class ReplenishmentObserver {
    private static ReplenishmentObserver replenishmentObserver = new ReplenishmentObserver();
    private ReplenishmentStrategy replenishmentStrategy;

    private ReplenishmentObserver() {}
    public static ReplenishmentObserver getInstance() {
        return replenishmentObserver;
    }

    public void handleReplenishment(String productID, String warehouseID) {
        System.out.println("Observer is handling replenishment for product: " + productID + " in warehouse: " + warehouseID);
        replenishmentStrategy.replenish(warehouseID, productID);
    }
}
