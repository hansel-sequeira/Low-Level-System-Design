package org.example.runner;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.*;

import java.util.*;

@Getter
@Setter
public class GameEngine {
    private Board board;
    private Queue<Player> playerQueue;
    private Dice dice;
    private Map<String, Integer> playerPosition;
    private int finishPoint;

    void initializeGame(int endPoint, int diceSides, List<SkipPiece> skipPieceList, List<Player> players) {
        this.finishPoint = endPoint;
        for(Player player: players)
            playerQueue.offer(player);
        this.board = new Board(endPoint, skipPieceList);
        this.dice = new Dice(diceSides);
        playerPosition = new HashMap<>();
        for(Player player : players)
            playerPosition.put(player.getPlayerID(), 0);
    }

    private int rollDice() {
        return 1 + (int)(Math.random()*finishPoint); // (int)math.random*50 generates from 0 to 49
    }

    private int determinePosition(int newPoint) {
        return board.getSkipPieceMap().getOrDefault(newPoint, newPoint);
    }

    private void cleanupGame() {
        board = null;
        playerQueue.clear();
        dice = null;
        playerPosition.clear();
        finishPoint = -1;
    }

    private void playGame() {
        Player currentPlayer = playerQueue.poll();
        playerQueue.offer(currentPlayer);
        System.out.println("Player: " + currentPlayer.getPlayerID() + " is currently playing.");
        int currentPosition = playerPosition.get(currentPlayer.getPlayerID());
        boolean gameEnds = false;
        while(true) {
            int sideChosen = rollDice();
            int newPoint = sideChosen + currentPosition;
            System.out.println("Die has been rolled to: " + sideChosen);
            if(newPoint > finishPoint) {
                System.out.println("Rolling again.");
            } else {
                int finalPosition = determinePosition(newPoint);
                if(finalPosition > newPoint) System.out.println("Met a ladder!");
                else if(finalPosition < newPoint) System.out.println("Met a snake!");
                System.out.println("Moving to point: " + newPoint);
                playerPosition.put(currentPlayer.getPlayerID(), finalPosition);
                if(finalPosition == finishPoint) {
                    System.out.println("Congratulations! " + currentPlayer.getPlayerID() + " has won the game");
                    gameEnds = true;
                }
                break;
            }
        }
        if(gameEnds) cleanupGame();
    }
}
