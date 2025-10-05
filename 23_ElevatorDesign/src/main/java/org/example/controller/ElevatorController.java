package org.example.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.Elevator;
import org.example.entities.ExternalRequest;
import org.example.enums.Direction;
import org.example.strategies.ElevatorDispatchStrategy;

import java.util.List;

@Getter
@Setter
// single instance per building
public class ElevatorController {
    private List<Elevator> elevatorList;
    private ElevatorDispatchStrategy elevatorDispatchStrategy;

    public ElevatorController(List<Elevator> elevatorList, ElevatorDispatchStrategy elevatorDispatchStrategy) {
        this.elevatorList = elevatorList;
        this.elevatorDispatchStrategy = elevatorDispatchStrategy;
    }

    public Elevator acceptExternalRequest(int floorNumber, Direction direction) {
        ExternalRequest request = new ExternalRequest(floorNumber, direction);
        Elevator elevator = elevatorDispatchStrategy.dispatchElevator(elevatorList, request);
        elevator.acceptRequest(floorNumber); // elevator will now service this request
        return elevator;
    }

}
