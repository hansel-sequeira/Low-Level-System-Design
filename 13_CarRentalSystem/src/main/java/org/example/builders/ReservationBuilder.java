package org.example.builders;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.RentalStore;
import org.example.entities.Reservation;
import org.example.entities.Vehicle;
import org.example.enums.RentalStrategyEnum;

import java.util.UUID;

public class ReservationBuilder {
    private int reservationID;
    private Vehicle vehicle;
    private int userID;
    private long startTime;
    private RentalStrategyEnum rentalStrategyEnum;

    public ReservationBuilder setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public ReservationBuilder setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public ReservationBuilder setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public ReservationBuilder setRentalStrategyEnum(RentalStrategyEnum rentalStrategyEnum) {
        this.rentalStrategyEnum = rentalStrategyEnum;
        return this;
    }

    public Reservation build() {
        Reservation reservation = new Reservation();
        reservation.setReservationID(UUID.randomUUID().toString());
        reservation.setRentalStrategyEnum(rentalStrategyEnum);
        reservation.setVehicle(vehicle);
        reservation.setUserID(userID);
        reservation.setStartTime(startTime);
        return reservation;
    }
}
