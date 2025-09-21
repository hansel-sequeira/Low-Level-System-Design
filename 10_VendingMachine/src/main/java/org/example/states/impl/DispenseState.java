package org.example.states.impl;

import org.example.entitites.ProductDetail;
import org.example.entitites.VendingMachine;
import org.example.enums.VENDING_MACHINE_STATE;
import org.example.states.VendingMachineState;

public class DispenseState extends VendingMachineState {
    public DispenseState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void dispense() {
        System.out.println("Dispensed the product and generated the bill. Moving to initial state.");
        this.vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.INITIAL_STATE);
    }
}
