package org.example.driver;

import org.example.entities.Move;
import org.example.entities.MoveHistory;
import org.example.entities.Player;
import org.example.enums.MoveType;

import java.util.*;

public class TicTacToeGame {
    private List<List<MoveType>> ticTacToeGrid;
    private MoveHistory moveHistory = new MoveHistory();
    private Queue<Player> playerQueue;
    private int maxMoves = 0;
    private int dimensions = 3;
    private int moveNumber = 0;
    Scanner sc = new Scanner(System.in);

    public TicTacToeGame(int dimensions) {
        this.dimensions = dimensions;
        ticTacToeGrid = new ArrayList<>(dimensions);
        for (int i = 0; i < dimensions; i++)
            ticTacToeGrid.add(new ArrayList<>(dimensions));
        for (int i = 0; i < dimensions; i++)
            for (int j = 0; j < dimensions; j++)
                ticTacToeGrid.get(i).add(null);
        maxMoves = dimensions * dimensions;
        System.out.println("Board has been created");
        playerQueue = new ArrayDeque<>();
        playerQueue.add(new Player("PlayerA", MoveType.X));
        playerQueue.add(new Player("PlayerB", MoveType.O));
        System.out.println("Players have been entered into the system.");
    }

    public boolean validateMove(int row, int col) {
        if (maxMoves == 0)
            return false;
        if (row < 0 || row >= dimensions || col < 0 || col >= dimensions || ticTacToeGrid.get(row).get(col) != null)
            return false;
        return true;
    }

    public boolean winnerCheck(Move move) {
        MoveType currentMoveType = move.getPlayer().getMoveType();
        int row = move.getRow(), col = move.getColumn();

        // Row check
        boolean rowWon = true;
        for (int i = 0; i < dimensions; i++) {
            if (ticTacToeGrid.get(row).get(i) != currentMoveType) {
                rowWon = false;
                break;
            }
        }
        if (rowWon) return true;

        // Column check
        boolean colWon = true;
        for (int i = 0; i < dimensions; i++) {
            if (ticTacToeGrid.get(i).get(col) != currentMoveType) {
                colWon = false;
                break;
            }
        }
        if (colWon) return true;

        // Left diagonal check
        if (row == col) {
            boolean leftDiagWon = true;
            for (int i = 0; i < dimensions; i++) {
                if (ticTacToeGrid.get(i).get(i) != currentMoveType) {
                    leftDiagWon = false;
                    break;
                }
            }
            if (leftDiagWon) return true;
        }

        // Right diagonal check
        if (row + col == dimensions - 1) {
            boolean rightDiagWon = true;
            for (int i = 0; i < dimensions; i++) {
                if (ticTacToeGrid.get(i).get(dimensions - 1 - i) != currentMoveType) {
                    rightDiagWon = false;
                    break;
                }
            }
            if (rightDiagWon) return true;
        }
        return false; // no win
    }

    private void terminateGame() {
        moveNumber = 0;
        maxMoves = 0;
        ticTacToeGrid = null;
        playerQueue = null;
        moveHistory = null;
    }


    public void playMove(int row, int col) {
        Player currentPlayer = playerQueue.poll();
        Move move = new Move(currentPlayer, moveNumber, row, col);
        System.out.println(currentPlayer.getPlayerName() + " plays " + currentPlayer.getMoveType() + " at: " + row + "," + col);
        playerQueue.offer(currentPlayer);
        moveNumber++;
        moveHistory.addMove(move);
        ticTacToeGrid.get(row).set(col, currentPlayer.getMoveType());
        boolean winCheck = winnerCheck(move);
        if (winCheck) {
            System.out.println("Congrats! " + currentPlayer.getPlayerName() + " has won the game.");
            System.out.println("Ending the game..");
            this.terminateGame();
        } else {
            if (moveNumber == maxMoves) {
                System.out.println("Stalemate. Ending the game.");
                this.terminateGame();
            }
        }
    }

    public void playGame() {
        while(maxMoves > 0) {
            int row = -1, col = -1;
            while(true) {
                System.out.println("Enter the row: ");
                row = sc.nextInt();
                System.out.println("Enter the column: ");
                col = sc.nextInt();
                if(validateMove(row, col) == false) {
                    System.out.println("Invalid move. Input again");
                } else break;
            }
            playMove(row, col);
        }
    }
}
