package org.example;

import org.example.entities.Location;
import org.example.entities.RentalStore;
import org.example.entities.Reservation;
import org.example.entities.Vehicle;
import org.example.enums.PaymentMethodEnum;
import org.example.enums.RentalStrategyEnum;
import org.example.enums.VehicleStatusEnum;
import org.example.managers.RentalStoreManager;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Vehicle vehicle = new Vehicle("123", "Toyota", "Corolla", 100, VehicleStatusEnum.AVAILABLE);
        RentalStore rentalStore = new RentalStore("1234", new Location("Mumbai", 50000), new ArrayList<>(), new ArrayList<>());
        RentalStoreManager rentalStoreManager = new RentalStoreManager(rentalStore);
        rentalStoreManager.addInventory(vehicle);


        rentalStoreManager.getAvailableVehicles();
        Reservation reservation = rentalStoreManager.bookVehicle(100, "123", System.currentTimeMillis(), RentalStrategyEnum.DAILY);
        Thread.sleep(2000);
        rentalStoreManager.returnVehicle(reservation, PaymentMethodEnum.CREDIT);
    }
}