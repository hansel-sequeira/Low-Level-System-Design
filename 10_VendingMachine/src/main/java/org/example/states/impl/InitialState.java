package org.example.states.impl;

import org.example.entitites.VendingMachine;
import org.example.enums.VENDING_MACHINE_STATE;
import org.example.states.VendingMachineState;

public class InitialState extends VendingMachineState {
    public InitialState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void chooseProduct(int productID) {
        System.out.println("Choosing product: " + productID);
        System.out.println("This product is available! Cost of this product: Rs. 100");
        this.vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.PRODUCT_SELECTED_STATE);
    }
}
