package org.example.strategies.payment;

public class UPIPaymentStrategy implements PaymentStrategy{

    @Override
    public boolean processPayment(double cost) {
        System.out.printf("UPI payment of Rs.%f successful!%n", cost);
        return true;
    }
}
