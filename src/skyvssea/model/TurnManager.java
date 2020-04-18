package skyvssea.model;

import javafx.scene.paint.Color;

public class TurnManager {
    public Player[] players = new Player[2];
    public Player currentPlayer;
    public boolean isPlayerOneTurn = true;

    public void setUpPlayers() {
        initPlayer();
    }

    public void initPlayer(){
        players[0] = new Player("shark leader",Color.BLUE);
        players[1] = new Player("eagle leader",Color.RED);
        currentPlayer = players[0];
    }

    private void nextTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
    }
}
