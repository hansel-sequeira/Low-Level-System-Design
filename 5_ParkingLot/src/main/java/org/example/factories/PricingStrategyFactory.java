package org.example.factories;

import org.example.enums.PricingStrategyType;
import org.example.strategies.pricing.HourlyPricingStrategy;
import org.example.strategies.pricing.PricingStrategy;

public class PricingStrategyFactory {
    public static PricingStrategy producePricingStrategy(PricingStrategyType pricingStrategyType) {
        switch(pricingStrategyType) {
            case HOURLY : return new HourlyPricingStrategy();
            default: throw new IllegalArgumentException("Invalid pricing strategy provided!");
        }
    }
}
