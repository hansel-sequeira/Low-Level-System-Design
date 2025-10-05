package org.example.strategies.impl;

import org.example.entities.Elevator;
import org.example.entities.ExternalRequest;
import org.example.enums.Direction;
import org.example.states.impl.DownMovement;
import org.example.states.impl.UpMovement;
import org.example.strategies.ElevatorDispatchStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class SameDirectionDispatchStrategy implements ElevatorDispatchStrategy {
    @Override
    public Elevator dispatchElevator(List<Elevator> elevatorList, ExternalRequest request) {
        List<Elevator> filteredElevators = elevatorList.stream().filter(e -> {
            Direction currentElevatorDirection = (e.getCurrentMovement() instanceof UpMovement ? Direction.UP :
            e.getCurrentMovement() instanceof DownMovement ? Direction.DOWN : null);
            return currentElevatorDirection != null && currentElevatorDirection.equals(request.getIntendedDirection());
        }).collect(Collectors.toList());
        if(filteredElevators.isEmpty()) throw new RuntimeException("No elevators found"); // ideally still need to pick the closest elevator, which is
        // albeit in the opposite direction.
        Elevator bestElevator = filteredElevators.get(0);
        int minDist = Math.abs(request.getFloor() - bestElevator.getCurrentFloor());
        for(Elevator e: filteredElevators) {
            int absDistance = Math.abs(request.getFloor() - e.getCurrentFloor());
            if(absDistance < minDist) {
                minDist = absDistance;
                bestElevator = e;
            }
        }
        return bestElevator;
    }
}
