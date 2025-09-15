package org.example.entities.gate;

import lombok.Getter;
import org.example.entities.Ticket;
import org.example.enums.GateType;
import org.example.enums.PaymentStrategyType;
import org.example.factories.PricingStrategyFactory;
import org.example.factories.PaymentStrategyFactory;
import org.example.strategies.payment.PaymentStrategy;
import org.example.strategies.pricing.PricingStrategy;

@Getter
public class ExitGate extends Gate{
    public ExitGate(int gateNumber) {
        super(gateNumber, GateType.EXIT);
    }

    public void registerExit(Ticket ticket, PaymentStrategyType paymentStrategyType) {
        // generate and process payment
        PricingStrategy pricingStrategy = PricingStrategyFactory.producePricingStrategy(ticket.getPricingStrategyType());
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentStrategyType);
        paymentStrategy.processPayment(pricingStrategy.computeCost(ticket));
        ticket.getParkingSpot().setOccupied(false);
    }
}
