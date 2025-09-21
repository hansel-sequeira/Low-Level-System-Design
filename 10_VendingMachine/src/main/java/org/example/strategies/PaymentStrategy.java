package org.example.strategies;

import org.example.entitites.PaymentDetails;

public interface PaymentStrategy {
    void processPayment(PaymentDetails paymentDetails);
}
