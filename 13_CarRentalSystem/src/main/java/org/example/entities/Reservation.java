package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.RentalStrategyEnum;
import org.example.strategies.RentalStrategy;

@Getter
@Setter
public class Reservation {
    private String reservationID;
    private Vehicle vehicle;
    private int userID;
    private long startTime;
    private RentalStrategyEnum rentalStrategyEnum;
}
