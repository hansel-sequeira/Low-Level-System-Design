package org.example.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Direction;

@Getter
@Setter
public class ExternalRequest{
    private int floor;
    private Direction intendedDirection;

    public ExternalRequest(int floor, Direction direction) {
        this.floor = floor;
        this.intendedDirection = direction;
    }
}
