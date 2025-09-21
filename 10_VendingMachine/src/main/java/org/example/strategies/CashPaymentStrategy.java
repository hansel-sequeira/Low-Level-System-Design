package org.example.strategies;

import org.example.entitites.PaymentDetails;

public class CashPaymentStrategy implements PaymentStrategy{
    private static CashPaymentStrategy cashPaymentStrategy = new CashPaymentStrategy();
    private CashPaymentStrategy() {}

    public static CashPaymentStrategy getInstance() {
        return cashPaymentStrategy;
    }

    @Override
    public void processPayment(PaymentDetails paymentDetails) {
        System.out.println("Processing the cash payment of Rs. " + paymentDetails.getInputValue());
    }
}
