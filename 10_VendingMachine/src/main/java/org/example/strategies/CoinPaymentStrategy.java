package org.example.strategies;

import org.example.entitites.PaymentDetails;

public class CoinPaymentStrategy implements PaymentStrategy{
    private static CoinPaymentStrategy coinPaymentStrategy = new CoinPaymentStrategy();
    private CoinPaymentStrategy() {}

    public static CoinPaymentStrategy getInstance() {
        return coinPaymentStrategy;
    }

    @Override
    public void processPayment(PaymentDetails paymentDetails) {
        System.out.println("Processing the coin payment of Rs. " + paymentDetails.getInputValue());
    }
}
