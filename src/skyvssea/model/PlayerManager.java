package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Player[] players = new Player[2];
    private Player currentPlayer;
    private HashMap<Player, ArrayList<Piece>> playerPieces = new HashMap<>();

    public PlayerManager(ArrayList<Piece> eaglePiecesList, ArrayList<Piece> sharkPiecesList) {
        initializePlayers();

        playerPieces.put(players[0], eaglePiecesList);
        playerPieces.put(players[1], sharkPiecesList);
    }

    private void initializePlayers() {
        players[0] = new Player("Shark's turn", Color.LIGHTCORAL);
        players[1] = new Player("Eagle's turn", Color.CORNFLOWERBLUE);
        currentPlayer = players[0];
    }

    @Requires("piece != null")
    public Player checkSide(Piece piece) {
        for (Map.Entry<Player, ArrayList<Piece>> entry : playerPieces.entrySet()) {
            if (entry.getValue().contains(piece)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Player changeTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
        return currentPlayer;
    }
}
