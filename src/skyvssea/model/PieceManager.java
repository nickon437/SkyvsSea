package skyvssea.model;

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

    public AbstractPiece getRegisteredPiece() {
        return registeredPiece;
    }

    @Requires("registeredPiece != null")
    public void setRegisteredPiece(AbstractPiece registeredPiece) {
        this.registeredPiece = registeredPiece;
    }

    public void clearRegisteredPiece() {
        registeredPiece = null;
    }

    public void addPiece(AbstractPiece piece) {
        if (piece instanceof AbstractEagle) {
            sharkPieces.get(piece.getLevel()).add(piece);
        } else if (piece instanceof AbstractShark) {
            eaglePieces.get(piece.getLevel()).add(piece);
        }
    }

    public int removePiece(AbstractPiece piece) {
        for (Map<Hierarchy, List<AbstractPiece>> pieceMap : getAllPiecesList()) {
            List<AbstractPiece> pieceList = pieceMap.get(piece.getLevel());
            if (pieceList.remove(piece)) {
                return pieceList.size();
            }
        }
        return -1;
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
