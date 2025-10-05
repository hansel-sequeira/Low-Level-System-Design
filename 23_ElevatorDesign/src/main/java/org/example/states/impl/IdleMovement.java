package org.example.states.impl;

import org.example.entities.Elevator;
import org.example.states.Movement;

public class IdleMovement implements Movement {
    @Override
    public void move(Elevator elevator) {
        if(elevator.getUpRequests().isEmpty() && elevator.getDownRequests().isEmpty()) {
           return;
        } else if(elevator.getUpRequests().isEmpty()) {
            elevator.setCurrentMovement(new DownMovement());
            elevator.getCurrentMovement().move(elevator);
        } else {
            elevator.setCurrentMovement(new UpMovement());
            elevator.getCurrentMovement().move(elevator);
        }
    }
}
