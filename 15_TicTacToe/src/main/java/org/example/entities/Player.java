package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.enums.MoveType;

@Getter
@AllArgsConstructor
public class Player {
    private String playerName;
    private MoveType moveType;
}
