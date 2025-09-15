package org.example;


import org.example.entities.ParkingLot;
import org.example.entities.Ticket;
import org.example.entities.gate.EntryGate;
import org.example.entities.gate.ExitGate;
import org.example.entities.parkingSpot.NormalParkingSpot;
import org.example.entities.parkingSpot.ParkingSpot;
import org.example.entities.vehicle.Vehicle;
import org.example.enums.PaymentStrategyType;
import org.example.enums.PricingStrategyType;
import org.example.enums.VehicleKind;
import org.example.enums.VehicleSizeType;
import org.example.factories.vehicle.ElectricVehicleFactory;
import org.example.factories.vehicle.NormalVehicleFactory;
import org.example.factories.vehicle.VehicleFactory;
import org.example.factories.vehicle.VehicleFactoryProducer;
import org.example.strategies.parking.FirstFreeSpotStrategy;

import java.util.HashMap;

/*

core functionalities:

-> support multiple kinds of vehicles
-> different kinds of parking slot sizes.
-> allow for entry and exit.
-> entry: choose a suitable parking spot and generate ticket.
-> exit: collect payment (multiple payment modes) and free up spot.

open ended:
-> apart from size, will parking spots differ in number of properties/behaviour?
    Eg: VIP/handicap reservation, electric parking spot, etc.
-> are we managing only one parking lot?
-> are there multiple floors?
-> multiple entries/exits?
-> concurrent booking
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // starter code starts here

        VehicleFactory vehicleFactory = VehicleFactoryProducer.generateVehicleFactory(VehicleKind.NORMAL);
        Vehicle vehicle = vehicleFactory.generateVehicle(VehicleSizeType.MEDIUM);

        ParkingLot parkingLot = ParkingLot.getInstance();
        ParkingSpot parkingSpot = new NormalParkingSpot(VehicleSizeType.MEDIUM, 100);
        HashMap<String, ParkingSpot> parkingSpotHashMap = new HashMap<>();
        HashMap<Integer, EntryGate> entryGateHashMap = new HashMap<>();
        HashMap<Integer, ExitGate> exitGateHashMap = new HashMap<>();
        parkingSpotHashMap.put(parkingSpot.getParkingSpotID(), parkingSpot);
        entryGateHashMap.put(1, new EntryGate(1));
        exitGateHashMap.put(2, new ExitGate(2));
        parkingLot.setParkingSpots(parkingSpotHashMap);

        parkingLot.setEntryGates(entryGateHashMap);
        parkingLot.setExitGates(exitGateHashMap);
        // starter code ends here

        EntryGate entryGate = parkingLot.getEntryGate();
        Ticket ticket = entryGate.registerEntry(vehicle, FirstFreeSpotStrategy.getInstance(parkingLot), PricingStrategyType.HOURLY);
        Thread.sleep(2000);
        ExitGate exitGate = parkingLot.getExitGate();
        exitGate.registerExit(ticket, PaymentStrategyType.UPI);
    }
}