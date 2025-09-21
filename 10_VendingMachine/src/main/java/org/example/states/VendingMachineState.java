package org.example.states;

import org.example.entitites.PaymentDetails;
import org.example.entitites.ProductDetail;
import org.example.entitites.VendingMachine;
import org.example.enums.PAYMENT_MODE;
import org.example.enums.VENDING_MACHINE_STATE;

public class VendingMachineState {
    protected VendingMachine vendingMachine;

    public void terminatedState() {
        System.out.println("Terminating the operation. Back to the idle state.");
        vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.INITIAL_STATE);
    }

    public VendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void chooseProduct(int productID) { // gets the product, checks for inventory availability and updates state
        throw new RuntimeException("Illegal operation");
    }

    public void insertCoins(PaymentDetails paymentDetails) {
        throw new RuntimeException("Illegal operation");
    }

    public boolean processPayment(ProductDetail productDetail, PaymentDetails paymentDetails) {
        throw new RuntimeException("Illegal operation");
    }
    public void dispense() {
        throw new RuntimeException("Illegal operation");
    }
}
