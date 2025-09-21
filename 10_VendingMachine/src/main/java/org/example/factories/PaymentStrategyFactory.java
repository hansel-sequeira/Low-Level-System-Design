package org.example.factories;

import org.example.enums.PAYMENT_MODE;
import org.example.strategies.CashPaymentStrategy;
import org.example.strategies.CoinPaymentStrategy;
import org.example.strategies.PaymentStrategy;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy(PAYMENT_MODE paymentMode) {
        return switch(paymentMode) {
            case CASH -> CashPaymentStrategy.getInstance();
            case COINS -> CoinPaymentStrategy.getInstance();
        };
    }
}
