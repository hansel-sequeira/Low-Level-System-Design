package org.example.strategies.impl;

import org.example.strategies.PaymentStrategy;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void processPayment(int cost) {
        System.out.println("Payment of Rs. " + cost + " was successful.");
    }
}
