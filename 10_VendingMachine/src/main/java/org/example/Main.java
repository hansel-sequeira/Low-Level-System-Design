package org.example;

import org.example.controller.VendingMachineController;
import org.example.entitites.PaymentDetails;
import org.example.entitites.ProductDetail;
import org.example.entitites.VendingMachine;
import org.example.enums.PAYMENT_MODE;

import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        VendingMachine vendingMachine = new VendingMachine();
        HashMap<Integer, ProductDetail> hashMap = new HashMap<>();
        hashMap.put(1, new ProductDetail(123, "Orange juice", 20, 100));
        vendingMachine.setProductList(hashMap);

        VendingMachineController vendingMachineController = new VendingMachineController(vendingMachine);
        vendingMachineController.chooseProduct(1);
        vendingMachineController.insertCoins(new PaymentDetails(PAYMENT_MODE.COINS, 20));
        vendingMachineController.collect();
    }
}