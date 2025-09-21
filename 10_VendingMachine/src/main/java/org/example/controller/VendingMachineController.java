package org.example.controller;

import org.example.entitites.PaymentDetails;
import org.example.entitites.ProductDetail;
import org.example.entitites.VendingMachine;
import org.example.enums.VENDING_MACHINE_STATE;
import org.example.states.VendingMachineState;
import org.example.states.impl.DispenseState;
import org.example.states.impl.InitialState;
import org.example.states.impl.ProcessPaymentState;
import org.example.states.impl.ProductSelectedState;

public class VendingMachineController {
    private VendingMachine vendingMachine;
    private InitialState initialState;
    private ProcessPaymentState processPaymentState;
    private ProductSelectedState productSelectedState;
    private DispenseState dispenseState;

    public VendingMachineController(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        initialState = new InitialState(vendingMachine);
        processPaymentState = new ProcessPaymentState(vendingMachine);
        productSelectedState = new ProductSelectedState(vendingMachine);
        dispenseState = new DispenseState(vendingMachine);
        this.vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.INITIAL_STATE);
    }

    private VendingMachineState getCurrentState() {
        VENDING_MACHINE_STATE vendingMachineState = this.vendingMachine.getCurrentVendingMachineState();
        return switch (vendingMachineState) {
            case INITIAL_STATE -> initialState;
            case PROCESS_PAYMENT_STATE -> processPaymentState;
            case PRODUCT_SELECTED_STATE -> productSelectedState;
            case DISPENSE_STATE -> dispenseState;
        };
    }

    public void terminatedState() {
        getCurrentState().terminatedState();
    }

    public void chooseProduct(int productID) {
        getCurrentState().chooseProduct(productID);
    }

    public void insertCoins(PaymentDetails paymentDetails) {
        getCurrentState().insertCoins(paymentDetails);
        ProductDetail productDetail = new ProductDetail(123, "Orange juice", 20, 100); // find the product from the inventory
        boolean retVal = getCurrentState().processPayment(productDetail, paymentDetails); // processes payment and updates the inventory.
        if(retVal == true) {
            dispense();
        }
    }

    private void dispense() {
        getCurrentState().dispense();
    }

    public void collect() {
        System.out.println("Printed the bill. Product collected. Operation completed");
    }
}
