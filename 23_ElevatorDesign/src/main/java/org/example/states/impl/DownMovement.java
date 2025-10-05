package org.example.states.impl;

import org.example.entities.Elevator;
import org.example.states.Movement;

public class DownMovement implements Movement {
    @Override
    public void move(Elevator elevator) {
        while(true) {
            if(elevator.getUpRequests().isEmpty() && elevator.getDownRequests().isEmpty()) {
                elevator.setCurrentMovement(new IdleMovement());
                break;
            } else if(elevator.getDownRequests().isEmpty()) {
                elevator.setCurrentMovement(new UpMovement());
                elevator.getCurrentMovement().move(elevator);
                break;
            } else {
                int newFloor = elevator.getDownRequests().first();
                elevator.setCurrentFloor(newFloor);
                elevator.getDisplayObserver().display(elevator.getElevatorID(), newFloor);
                elevator.getDownRequests().pollFirst();
            }
        }
    }
}
