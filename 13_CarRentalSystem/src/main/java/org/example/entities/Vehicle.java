package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.VehicleStatusEnum;

@Getter
@Setter
@AllArgsConstructor
public class Vehicle {
    private String vehicleID;
    private String make;
    private String model;
    private int basePrice;
    private VehicleStatusEnum vehicleStatusEnum;
}
