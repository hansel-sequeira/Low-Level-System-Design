package org.example.states.impl;

import org.example.entitites.PaymentDetails;
import org.example.entitites.ProductDetail;
import org.example.entitites.VendingMachine;
import org.example.enums.VENDING_MACHINE_STATE;
import org.example.factories.PaymentStrategyFactory;
import org.example.states.VendingMachineState;
import org.example.strategies.PaymentStrategy;

public class ProcessPaymentState extends VendingMachineState {
    public ProcessPaymentState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public boolean processPayment(ProductDetail productDetail, PaymentDetails paymentDetails) { // this method will not be called by the client.
        // it will be invoked internally by the vending machine controller
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentDetails.getPaymentMode());
        paymentStrategy.processPayment(paymentDetails);
        int currentQty = productDetail.getCurrentQty();
        productDetail.setCurrentQty(currentQty-1);
        System.out.println("Payment processes succesfully and updated the inventory. Moving to the dispense state");
        this.vendingMachine.setCurrentVendingMachineState(VENDING_MACHINE_STATE.DISPENSE_STATE);
        return true;
    }
}
