package org.example.states.impl;

import org.example.entitites.PaymentDetails;
import org.example.entitites.VendingMachine;
import org.example.enums.VENDING_MACHINE_STATE;
import org.example.states.VendingMachineState;

public class ProductSelectedState extends VendingMachineState {
    public ProductSelectedState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void insertCoins(PaymentDetails paymentDetails) {
        System.out.println("Accepted your payment of Rs. " + paymentDetails.getInputValue());
        System.out.println("Processing your payment...");
        this.vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.PROCESS_PAYMENT_STATE);
    }
}
