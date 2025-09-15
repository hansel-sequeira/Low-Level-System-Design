package org.example.entities.vehicle;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

@Getter
@Setter
public class ElectricVehicle extends Vehicle{
    public ElectricVehicle(VehicleSizeType vehicleType) {
        super(vehicleType, VehicleKind.ELECTRIC);
    }
    public void charge() {
        System.out.println("Charging the electric vehicle");
    }
}
