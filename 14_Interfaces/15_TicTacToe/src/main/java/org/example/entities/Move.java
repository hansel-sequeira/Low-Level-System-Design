package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.enums.MoveType;

@Getter
@AllArgsConstructor
public class Move {
    private Player player;
    private int moveNumber;
    private int row;
    private int column;
}
