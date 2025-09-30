package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.observers.ReplenishmentObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Warehouse {
    private String warehouseID;
    private Location location;
    private HashMap<String, Product> productList;
    private ReplenishmentObserver replenishmentObserver;

    public void onboardProduct(Product product) {
        productList.put(product.getProductID(), product);
    }

    public void consumeProduct(String productID, int qty) {
        Product product = productList.get(productID);
        System.out.println("Consumed the product");
        product.setInventoryStock(product.getInventoryStock() - qty);
        if(product.getInventoryStock() < 10) {
            System.out.println("Replenishing the product..");
            this.replenishmentObserver.handleReplenishment(productID, warehouseID);
        }
    }

    public void displayWarehouseState() {
        for(Map.Entry<String, Product> productEntry: productList.entrySet()) {
            Product product = productEntry.getValue();
            System.out.println(product.getProductID() + " : " + product.getInventoryStock());
        }
    }
}
