package org.example.states.impl;

import org.example.entities.Elevator;
import org.example.states.Movement;

public class UpMovement implements Movement {
    @Override
    public void move(Elevator elevator) {
        while(true) {
            if(elevator.getUpRequests().isEmpty() && elevator.getDownRequests().isEmpty()) {
                elevator.setCurrentMovement(new IdleMovement());
                break;
            } else if(elevator.getUpRequests().isEmpty()) {
                elevator.setCurrentMovement(new DownMovement());
                elevator.getCurrentMovement().move(elevator);
                break;
            } else {
                int newFloor = elevator.getUpRequests().first();
                elevator.setCurrentFloor(newFloor);
                elevator.getDisplayObserver().display(elevator.getElevatorID(), newFloor);
                elevator.getUpRequests().pollFirst();
            }
        }
    }
}
