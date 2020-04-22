package skyvssea.model;

import com.google.java.contract.Requires;

import java.util.ArrayList;
import java.util.Map;

public class PieceManager {
    private Map<Hierarchy, ArrayList<Piece>> sharkPieces;
    private Map<Hierarchy, ArrayList<Piece>> eaglePieces;
    private Piece currentPiece;

	public PieceManager() {
        initializePieces();
    }

    /**
     * Create initial lineup of pieces for both sides
     */
    public void initializePieces() {
        //Using singleton pattern to create the factories (not sure if it's appropriate though)
        PieceFactory sharkFactory = SharkFactory.getInstance();
        PieceFactory eagleFactory = EagleFactory.getInstance();
        sharkPieces = sharkFactory.createInitialLineUp();
        eaglePieces = eagleFactory.createInitialLineUp();
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    @Requires("currentPiece != null")
    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void clearCurrentPiece() {
        currentPiece = null;
    }

    public Map<Hierarchy, ArrayList<Piece>> getSharkPieces() {
        return sharkPieces;
    }

    public Map<Hierarchy, ArrayList<Piece>> getEaglePieces() {
        return eaglePieces;
    }

    public ArrayList<Piece> getSharkPiecesList() {
        ArrayList<Piece> sharkPiecesList = new ArrayList<>();
        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getSharkPieces().entrySet()) {
            sharkPiecesList.addAll(entry.getValue());
        }
        return sharkPiecesList;
    }

    public ArrayList<Piece> getEaglePiecesList() {
        ArrayList<Piece> eaglePiecesList = new ArrayList<>();
        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getEaglePieces().entrySet()) {
            eaglePiecesList.addAll(entry.getValue());
        }
        return eaglePiecesList;
    }

    @Requires("board != null")
    public ArrayList<Tile> setPiecesOnBoard(Board board) {
        ArrayList<Tile> startingPositions = new ArrayList<>();
        int midPoint = Board.NUM_SIDE_CELL / 2;

        // Use ArrayList to reduce code duplication when traverse through HashMap
        ArrayList<Map<Hierarchy, ArrayList<Piece>>> piecesList = new ArrayList<>();
        piecesList.add(sharkPieces);
        piecesList.add(eaglePieces);

        for (Map<Hierarchy, ArrayList<Piece>> pieces : piecesList) {
            int pieceXCoord = 0;
            int pieceYCoord = midPoint;
            int pieceIndex = 0;
            int flip = 1;

            if (piecesList.indexOf(pieces) != 0) {
                pieceXCoord = Board.NUM_SIDE_CELL - 1;
            }

            for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : pieces.entrySet()) {
                for (Piece piece : entry.getValue()) {
                    pieceYCoord = pieceYCoord + (flip * pieceIndex);
                    flip = flip * -1;
    
                    Tile tile = board.getTile(pieceXCoord, pieceYCoord);
                    tile.setPiece(piece);
                    startingPositions.add(tile);

                    pieceIndex++;
                }
            }
        }

        return startingPositions;
    }
}
