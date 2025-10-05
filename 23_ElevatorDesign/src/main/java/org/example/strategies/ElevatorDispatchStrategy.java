package org.example.strategies;

import org.example.entities.Elevator;
import org.example.entities.ExternalRequest;

import java.util.List;

public interface ElevatorDispatchStrategy {
    Elevator dispatchElevator(List<Elevator> elevatorList, ExternalRequest request);
}
