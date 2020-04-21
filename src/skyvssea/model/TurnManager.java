package skyvssea.model;

import javafx.scene.paint.Color;

public class TurnManager {
    private Player[] players = new Player[2];
    private Player currentPlayer;

    public TurnManager() {
        initializePlayers();
    }

    private void initializePlayers() {
        players[0] = new Player("Shark's turn",Color.BLUE);
        players[1] = new Player("Eagle's turn",Color.RED);
        currentPlayer = players[0];
    }

    public Player changeTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
        return currentPlayer;
    }
}
