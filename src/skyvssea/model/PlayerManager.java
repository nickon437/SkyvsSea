package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
//    private HashMap<Player, ArrayList<Piece>> playerPieces = new HashMap<>();

	public PlayerManager(/* ArrayList<Piece> eaglePiecesList, ArrayList<Piece> sharkPiecesList */) {
        initializePlayers();

//        playerPieces.put(players[0], sharkPiecesList);
//        playerPieces.put(players[1], eaglePiecesList);
    }

    private void initializePlayers() {
        player1 = new Player("Shark's turn", Color.LIGHTCORAL, Team.SHARK);
        player2 = new Player("Eagle's turn", Color.CORNFLOWERBLUE, Team.EAGLE);
        currentPlayer = player1;
    }

    @Requires("piece != null")
    public Player checkSide(Piece piece) {
    	if (piece.getTeam() == Team.SHARK) {
    		return player1;
    	} else {
    		return player2;
    	}
    	
//        for (Map.Entry<Player, ArrayList<Piece>> entry : playerPieces.entrySet()) {
//            if (entry.getValue().contains(piece)) {
//                return entry.getKey();
//            }
//        }
//        return null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player changeTurn() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        return currentPlayer;
    }
}
