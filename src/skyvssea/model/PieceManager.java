package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieceManager {
	private Map<Hierarchy, ArrayList<AbstractPiece>> sharkPieces = new HashMap<>();
    private Map<Hierarchy, ArrayList<AbstractPiece>> eaglePieces = new HashMap<>();
    private AbstractPiece currentPiece;

    public PieceManager(Map<Hierarchy, Integer> lineup) {
        initializePieces(lineup);
    }

    /**
     * Create initial lineup of pieces for both sides
     */
	//The precondition checks if the lineup numbers are not all zeroes
    @Requires("lineup.values().stream().mapToInt(Integer::intValue).sum() > 0")
    public void initializePieces(Map<Hierarchy, Integer> lineup) {
        AbstractPieceFactory sharkFactory = SharkFactory.getInstance();
        AbstractPieceFactory eagleFactory = EagleFactory.getInstance();
      
        for (Map.Entry<Hierarchy, Integer> entry : lineup.entrySet()) {
            createPiecesByHierarchy(eaglePieces, eagleFactory, entry);
            createPiecesByHierarchy(sharkPieces, sharkFactory, entry);
        }
    }

    private void createPiecesByHierarchy(Map<Hierarchy, ArrayList<AbstractPiece>> pieces, AbstractPieceFactory factory, Map.Entry<Hierarchy, Integer> creationInfo) {
		Hierarchy level = creationInfo.getKey();    
		int numPiecesToCreate = creationInfo.getValue();
		pieces.put(level, new ArrayList<>());
		for (int i = 0; i < numPiecesToCreate; i++) {
			pieces.get(level).add(factory.createPiece(level));        		
		}
	}

	 public AbstractPiece getRegisteredPiece() { return currentPiece; }

	    @Requires("currentPiece != null")
	    public void setCurrentPiece(AbstractPiece currentPiece) { this.currentPiece = currentPiece; }

	    public void clearCurrentPiece() { currentPiece = null; }

	    public Map<Hierarchy, ArrayList<AbstractPiece>> getSharkPieces() { return sharkPieces; }
	    public Map<Hierarchy, ArrayList<AbstractPiece>> getEaglePieces() { return eaglePieces; }

    public ArrayList<Map<Hierarchy, ArrayList<AbstractPiece>>> getAllPiecesList() {
        ArrayList<Map<Hierarchy, ArrayList<AbstractPiece>>> piecesList = new ArrayList<>();
        piecesList.add(sharkPieces);
        piecesList.add(eaglePieces);
        return piecesList;
    }

    @Requires("board != null")
    public ArrayList<Tile> setPiecesOnBoard(Board board) {
        ArrayList<Tile> startingPositions = new ArrayList<>();
        int midPoint = board.getRow() / 2;

        // Use ArrayList to reduce code duplication when traverse through HashMap
        ArrayList<Map<Hierarchy, ArrayList<AbstractPiece>>> piecesList = getAllPiecesList();

        for (Map<Hierarchy, ArrayList<AbstractPiece>> pieces : piecesList) {
            int pieceXCoord = 0;
            int pieceYCoord = midPoint;
            int pieceIndex = 0;
            int flip = 1;

            if (piecesList.indexOf(pieces) != 0) { pieceXCoord = board.getCol() - 1 ; }

            for (Map.Entry<Hierarchy, ArrayList<AbstractPiece>> entry : pieces.entrySet()) {
                for (AbstractPiece piece : entry.getValue()) {
                    pieceYCoord = pieceYCoord + (flip * pieceIndex);
                    flip = flip * -1;
    
                    Tile tile = board.getTile(pieceXCoord, pieceYCoord);
                    tile.setGameObject(piece);
                    startingPositions.add(tile);

                    pieceIndex++;
                }
            }
        }
        return startingPositions;
    }
    
    public void updatePieceStatus() {
        for (Map<Hierarchy, ArrayList<AbstractPiece>> playerPieces : getAllPiecesList()) {
            for (Map.Entry<Hierarchy, ArrayList<AbstractPiece>> pieces : playerPieces.entrySet()) {
                for (AbstractPiece piece : pieces.getValue()) {
                    piece.updateStatus();
                }
            }
        }
    }
}