package org.example.factories;

import org.example.enums.PaymentStrategyType;
import org.example.strategies.payment.PaymentStrategy;
import org.example.strategies.payment.UPIPaymentStrategy;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentStrategyType paymentStrategyType) {
        switch(paymentStrategyType) {
            case UPI: return new UPIPaymentStrategy();
            default: throw new IllegalArgumentException("Invalid payment strategy passed");
        }
    }
}
