package org.example.observers;

public class DisplayObserver {
    public void display(String elevatorID, int currentFloor) {
        System.out.println(elevatorID + " is at floor: " + currentFloor);
    }
}
