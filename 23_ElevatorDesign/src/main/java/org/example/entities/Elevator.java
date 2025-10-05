package org.example.entities;


import lombok.Data;
import org.example.observers.DisplayObserver;
import org.example.states.Movement;
import org.example.states.impl.IdleMovement;

import java.util.TreeSet;
import java.util.UUID;

@Data
public class Elevator {
    private String elevatorID;
    private int currentFloor;
    private Movement currentMovement;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;
    private DisplayObserver displayObserver;

    public Elevator() {
        this.elevatorID = UUID.randomUUID().toString();
        currentFloor = 0;
        currentMovement = new IdleMovement();
        upRequests = new TreeSet<>();
        downRequests = new TreeSet<>();
        displayObserver = new DisplayObserver();
    }

    // starting point for movement of the elevator.
    public void acceptRequest(int targetFloor) {
        // received from both external and internal sources.
        if(targetFloor > this.currentFloor)
            upRequests.add(targetFloor);
        else downRequests.add(targetFloor);
    }

}
