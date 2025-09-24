package org.example.factories;

import org.example.entities.Reservation;
import org.example.enums.RentalStrategyEnum;
import org.example.strategies.RentalStrategy;
import org.example.strategies.impl.DailyRentalStrategy;
import org.example.strategies.impl.HourlyRentalStrategy;

public class RentalStrategyFactory {
    public static RentalStrategy getRentalStrategy(RentalStrategyEnum rentalStrategyEnum) {
        return switch(rentalStrategyEnum) {
            case DAILY -> new DailyRentalStrategy();
            case HOURLY -> new HourlyRentalStrategy();
            default -> null;
        };
    }
}
