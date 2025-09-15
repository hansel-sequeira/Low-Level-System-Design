package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.gate.EntryGate;
import org.example.entities.gate.ExitGate;
import org.example.entities.gate.Gate;
import org.example.entities.parkingSpot.ParkingSpot;
import org.example.strategies.parking.ParkingSpotFinderStrategy;

import java.util.Map;

@Getter
@Setter
public class ParkingLot {
    private Map<String, ParkingSpot> parkingSpots;
    private Map<Integer, EntryGate> entryGates;
    private Map<Integer, ExitGate> exitGates;
    private static ParkingLot parkingLot = new ParkingLot();

    public static ParkingLot getInstance() {
        return parkingLot;
    }

    public EntryGate getEntryGate() {
        return entryGates.entrySet().iterator().next().getValue();
    }

    public ExitGate getExitGate() {
        return exitGates.entrySet().iterator().next().getValue();
    }

    void displayParkingSpots() {
        parkingSpots.values().forEach(parkingSpot -> System.out.println(parkingSpot.getParkingSpotID() + " is occupied: " + parkingSpot.isOccupied()));
    }

    void addParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpots.put(parkingSpot.getParkingSpotID(), parkingSpot);
    }

    void removeParkingSpot(String parkingSpotID) {
        parkingSpots.remove(parkingSpotID);
    }

}
