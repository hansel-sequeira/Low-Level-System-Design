package org.example.managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.builders.ReservationBuilder;
import org.example.entities.RentalStore;
import org.example.entities.Reservation;
import org.example.entities.Vehicle;
import org.example.enums.PaymentMethodEnum;
import org.example.enums.RentalStrategyEnum;
import org.example.enums.VehicleStatusEnum;
import org.example.factories.PaymentMethodFactory;
import org.example.factories.RentalStrategyFactory;
import org.example.strategies.PaymentStrategy;
import org.example.strategies.RentalStrategy;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RentalStoreManager {
    private RentalStore rentalStore;

    public void addInventory(Vehicle vehicle) {
        this.rentalStore.getVehicleInventory().add(vehicle);
    }

    public void getAvailableVehicles() {
        List<Vehicle> vehicleList = rentalStore.getVehicleInventory();
        for(Vehicle vehicle: vehicleList) {
            if(vehicle.getVehicleStatusEnum() == VehicleStatusEnum.AVAILABLE)
                System.out.println(vehicle.getVehicleID() + " : "  + vehicle.getModel());
        }
    }

    public Reservation bookVehicle(int userID, String vehicleID, long startDate, RentalStrategyEnum rentalStrategyEnum) {
        ReservationBuilder reservationBuilder = new ReservationBuilder();
        Vehicle vehicle = rentalStore.getVehicleInventory().stream()
                .filter(v -> v.getVehicleID().equals(vehicleID)).findFirst().get();
        Reservation reservation = reservationBuilder.setRentalStrategyEnum(rentalStrategyEnum)
                .setVehicle(vehicle)
                .setUserID(userID)
                .setStartTime(startDate)
                .build();
        System.out.println("Reservation with ID: " + reservation.getReservationID() + " has been created.");
        rentalStore.getReservationList().add(reservation);
        vehicle.setVehicleStatusEnum(VehicleStatusEnum.OCCUPIED);
        return reservation;
    }

    public void returnVehicle(Reservation reservation, PaymentMethodEnum paymentMethodEnum) {
        Vehicle vehicle = reservation.getVehicle();

        PaymentStrategy paymentStrategy = PaymentMethodFactory.getPaymentStrategy(paymentMethodEnum);
        RentalStrategy rentalStrategy = RentalStrategyFactory.getRentalStrategy(reservation.getRentalStrategyEnum());
        int cost = rentalStrategy.computeCost(reservation);
        paymentStrategy.processPayment(cost);
        rentalStore.getReservationList().remove(reservation);
        vehicle.setVehicleStatusEnum(VehicleStatusEnum.AVAILABLE);
    }
}
