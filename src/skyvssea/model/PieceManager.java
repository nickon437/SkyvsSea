package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.piece.AbstractEagle;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.piece.AbstractShark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PieceManager {

	private Map<Hierarchy, List<AbstractPiece>> sharkPieces = new TreeMap<>();
    private Map<Hierarchy, List<AbstractPiece>> eaglePieces = new TreeMap<>();
    private AbstractPiece registeredPiece;

    public PieceManager(List<AbstractPiece> eaglePieceList, List<AbstractPiece> sharkPieceList) {
        convertPieceListToMap(eaglePieceList, eaglePieces);
        convertPieceListToMap(sharkPieceList, sharkPieces);
    }

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

    private void createPiecesByHierarchy(Map<Hierarchy, List<AbstractPiece>> pieces, AbstractPieceFactory factory, Map.Entry<Hierarchy, Integer> creationInfo) {
        Hierarchy level = creationInfo.getKey();
        int numPiecesToCreate = creationInfo.getValue();
        pieces.put(level, new ArrayList<>());
        for (int i = 0; i < numPiecesToCreate; i++) {
            pieces.get(level).add(factory.createPiece(level));
        }
    }

    private void convertPieceListToMap(List<AbstractPiece> pieceList, Map<Hierarchy, List<AbstractPiece>> pieceMap) {
        // Create empty arraylist value
        for (Hierarchy hierarchy : Hierarchy.values()) {
            pieceMap.put(hierarchy, new ArrayList<>());
        }
        // Input piece
        for (AbstractPiece piece : pieceList) {
            if (pieceMap == null) System.out.println("pieceMap == null");
            if (piece == null) System.out.println("piece == null");
            pieceMap.get(piece.getLevel()).add(piece);
        }
    }

    public AbstractPiece getRegisteredPiece() {
        return registeredPiece;
    }

    public void setRegisteredPiece(AbstractPiece registeredPiece) {
        this.registeredPiece = registeredPiece;
    }

    public void clearRegisteredPiece() {
        registeredPiece = null;
    }

    @Requires("piece != null")
    @Ensures("getPiecesInHierarchy(piece).size() == old(getPiecesInHierarchy(piece).size()) + 1 && getPiecesInHierarchy(piece).contains(piece)")
    public void addPiece(AbstractPiece piece) {
    	List<AbstractPiece> pieceList = getPiecesInHierarchy(piece);
    	
    	if (pieceList != null) {
    		pieceList.add(piece);    		
    	}
    }

    @Requires("piece != null")
    @Ensures("getPiecesInHierarchy(piece).size() == old(getPiecesInHierarchy(piece).size()) - 1 && !getPiecesInHierarchy(piece).contains(piece)")
    public void removePiece(AbstractPiece piece) {
    	List<AbstractPiece> pieceList = getPiecesInHierarchy(piece);
    	
    	if (pieceList != null) {
    		pieceList.remove(piece);    		
    	}
    }
    
    public int countPiecesInHierarchy(AbstractPiece piece) {
    	List<AbstractPiece> pieceList = getPiecesInHierarchy(piece);
    	return pieceList != null ? pieceList.size() : -1;
    }

	private List<AbstractPiece> getPiecesInHierarchy(AbstractPiece piece) {
		List<AbstractPiece> pieceList = null;
    	if (piece instanceof AbstractEagle) {
    		pieceList = eaglePieces.get(piece.getLevel());
    	} else if (piece instanceof AbstractShark) {
    		pieceList = sharkPieces.get(piece.getLevel());
    	}
		return pieceList;
	}

    public Map<Hierarchy, List<AbstractPiece>> getSharkPieces() { return sharkPieces; }

    public Map<Hierarchy, List<AbstractPiece>> getEaglePieces() { return eaglePieces; }

    public List<Map<Hierarchy, List<AbstractPiece>>> getAllPiecesList() {
        List<Map<Hierarchy, List<AbstractPiece>>> piecesList = new ArrayList<>();
        piecesList.add(eaglePieces);
        piecesList.add(sharkPieces);
        return piecesList;
    }
    
    @Requires("board != null")
    public List<Tile> setPiecesOnBoard(Board board) {
        List<Tile> startingPositions = new ArrayList<>();
        int midPoint = board.getRow() / 2;

        // Use ArrayList to reduce code duplication when traverse through HashMap
        List<Map<Hierarchy, List<AbstractPiece>>> piecesList = getAllPiecesList();

        for (Map<Hierarchy, List<AbstractPiece>> pieces : piecesList) {
            int pieceXCoord = 0;
            int pieceYCoord = midPoint;
            int pieceIndex = 0;
            int flip = 1;

            if (piecesList.indexOf(pieces) != 0) {
                pieceXCoord = board.getCol() - 1;
            }

            for (Map.Entry<Hierarchy, List<AbstractPiece>> entry : pieces.entrySet()) {
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

    public void updatePieceStatus(HistoryManager historyManager) {
        for (Map<Hierarchy, List<AbstractPiece>> playerPieces : getAllPiecesList()) {
            for (Map.Entry<Hierarchy, List<AbstractPiece>> pieces : playerPieces.entrySet()) {
                for (AbstractPiece piece : pieces.getValue()) {
                    piece.updateStatus(historyManager);
                }
            }
        }
    }
}
