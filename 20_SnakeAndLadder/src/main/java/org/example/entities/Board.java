package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Board {
    private int endPoint;
    private Map<Integer, Integer> skipPieceMap;


    public Board(int endPoint, List<SkipPiece> skipPieceList) {
        this.endPoint = endPoint;
        for(SkipPiece skipPiece : skipPieceList)
            skipPieceMap.put(skipPiece.getStartPoint(), skipPiece.getEndPoint());
    }

}
