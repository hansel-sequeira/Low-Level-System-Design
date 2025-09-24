package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RentalStore {
    private String storeID;
    private Location location;
    List<Vehicle> vehicleInventory;
    List<Reservation> reservationList;
}
