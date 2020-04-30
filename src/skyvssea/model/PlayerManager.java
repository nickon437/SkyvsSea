package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;
import skyvssea.model.piece.Piece;

import java.util.ArrayList;
import java.util.Map;

public class PlayerManager {
    private Player[] players = new Player[2];
    private Player currentPlayer;

	public PlayerManager(Map<Hierarchy, ArrayList<Piece>> eaglePieces, Map<Hierarchy, ArrayList<Piece>> sharkPieces) {
        initializePlayers(eaglePieces, sharkPieces);
    }

    private void initializePlayers(Map<Hierarchy, ArrayList<Piece>> eaglePieces, Map<Hierarchy, ArrayList<Piece>> sharkPieces) {
        players[0] = new Player("Eagle's turn", Color.valueOf("#E92707"), eaglePieces);
        players[1] = new Player("Shark's turn", Color.valueOf("#390593"), sharkPieces);
        currentPlayer = players[0];
    }

    @Requires("piece != null")
    public Player checkSide(Piece piece) {
    	for (Player player : players) {
            if (player.hasPiece(piece)) {
                return player;
            }
        }
    	return null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player changeTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
        return currentPlayer;
    }
}
