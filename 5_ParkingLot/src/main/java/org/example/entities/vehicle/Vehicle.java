package org.example.entities.vehicle;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;

import java.util.UUID;

@Getter
@Setter
public abstract class Vehicle {

    // Even though among the children, the only differing property is VehicleType:Enum.
    // it makes sense to go with inheritance when creating a Vehicle instance to account for
    // electric vehicles in the future -> they will have additional properties/methods: charge() etc.

    protected String vehicleID;
    protected VehicleSizeType vehicleSizeType;
    protected VehicleKind vehicleKind;
    protected Vehicle(VehicleSizeType vehicleType, VehicleKind vehicleKind) {
        this.vehicleID = UUID.randomUUID().toString();
        this.vehicleSizeType = vehicleType;
        this.vehicleKind = vehicleKind;
    }
}
