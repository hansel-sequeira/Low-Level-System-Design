package org.example.factories;

import org.example.enums.PaymentMethodEnum;
import org.example.strategies.PaymentStrategy;
import org.example.strategies.impl.CreditCardPaymentStrategy;

public class PaymentMethodFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentMethodEnum paymentMethodEnum) {
        return switch(paymentMethodEnum) {
            case CREDIT -> new CreditCardPaymentStrategy();
            default -> null;
        };
    }
}
