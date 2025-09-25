package org.example.factories;

import org.example.driver.TicTacToeGame;

public class TicTacToeGameFactory {
    public static TicTacToeGame generateGameInstance(int dimensions) {
        return new TicTacToeGame(dimensions);
    }
}
