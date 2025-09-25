package org.example.entities;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class MoveHistory {
    private List<Move> moveList = new ArrayList<>();


    public void addMove(Move move) {
        moveList.add(move);
        System.out.println("Tracked this move.");
    }
}
