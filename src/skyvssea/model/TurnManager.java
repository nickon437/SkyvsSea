package skyvssea.model;

import javafx.scene.paint.Color;

public class TurnManager {
    public Player[] players = new Player[2];
    public Player currentPlayer;
    public boolean isPlayerOneTurn = true;

    public void setUpPlayers() {
        players[0] = new Player("Shark Leader",Color.BLUE);
        players[1] = new Player("Eagle Leader",Color.RED);
        currentPlayer = players[0];
    }

    public Player nextTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
        return currentPlayer;
    }
}
