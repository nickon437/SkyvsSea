package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;

public class PlayerManager {
    private Player[] players = new Player[2];
    private Player currentPlayer;

	public PlayerManager(Map<Hierarchy, ArrayList<Piece>> eaglePieces, Map<Hierarchy, ArrayList<Piece>> sharkPieces) {
        initializePlayers(eaglePieces, sharkPieces);
    }

    private void initializePlayers(Map<Hierarchy, ArrayList<Piece>> eaglePieces, Map<Hierarchy, ArrayList<Piece>> sharkPieces) {
        players[0] = new Player("Eagle's turn", Color.LIGHTCORAL, eaglePieces);
        players[1] = new Player("Shark's turn", Color.CORNFLOWERBLUE, sharkPieces);
        currentPlayer = players[0];
    }

    @Requires("piece != null")
    public Player checkSide(Piece piece) {
//    	if (piece.getTeam() == Team.SHARK) {
//    		return player1;
//    	} else {
//    		return player2;
//    	}

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
