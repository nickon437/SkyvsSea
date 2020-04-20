package skyvssea.model;

import skyvssea.view.PieceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieceManager {
    // Need review
    // Reason for using a hashmap to store the pieces: easier to assign the pieces to initial tile locations
    private Map<Hierarchy, ArrayList<Piece>> sharkPieces;
    private Map<Hierarchy, ArrayList<Piece>> eaglePieces;
    private HashMap<Piece, PieceView> pieceViewPairs = new HashMap<>();
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


        ArrayList<Piece> allPieces = getAllPieces();
        for (Piece piece : allPieces) {
            PieceView pieceView = new PieceView(piece.getName());
            pieceViewPairs.put(piece, pieceView);
        }
    }

    private ArrayList<Piece> getAllPieces() {
        ArrayList<Piece> allPieces = new ArrayList<>();
        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getSharkPieces().entrySet()) {
            allPieces.addAll(entry.getValue());
        }
        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getEaglePieces().entrySet()) {
            allPieces.addAll(entry.getValue());
        }
        return allPieces;
    }

    // T-O-D-O: Should change the return type to listener interface; might be removed
    // Nick - Re-implement this because we use PieceManager to link Piece and PieceView now
    public ArrayList<PieceView> getAllPieceViews() {
        ArrayList<PieceView> allPieceViews = new ArrayList<>();
        for (Piece piece : getAllPieces()) {
            allPieceViews.add(getPieceView(piece));
        }
        return allPieceViews;
    }

    public PieceView getPieceView(Piece piece) {
        return pieceViewPairs.get(piece);
    }
    
    public Piece getPiece(String name) {
    	ArrayList<Piece> pieces = getAllPieces();
    	for (Piece piece : pieces) {
    		if (piece.getName().equals(name)) {
    			return piece;
    		}
    	}
    	return null;
    }
    
    public Piece getCurrentPiece() { return currentPiece; }

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

	public void setPiecesOnBoard(Board board) {
        // Nick - TODO: Need to refactor this to loop for flexible board size and number of pieces
		// Jiang - TODO: if the number of pieces and the starting position does not change, can reduce the code below with loops
        Piece bigShark = sharkPieces.get(Hierarchy.BIG).get(0);
        Piece mediumShark = sharkPieces.get(Hierarchy.MEDIUM).get(0);
        Piece babyShark = sharkPieces.get(Hierarchy.BABY).get(0);
        Piece smallShark = sharkPieces.get(Hierarchy.SMALL).get(0);
        board.getTile(0, 3).setPiece(bigShark, getPieceView(bigShark));
		board.getTile(0, 4).setPiece(mediumShark, getPieceView(mediumShark));
		board.getTile(0, 5).setPiece(babyShark, getPieceView(babyShark));
		board.getTile(0, 6).setPiece(smallShark, getPieceView(smallShark));

        Piece bigEagle = eaglePieces.get(Hierarchy.BIG).get(0);
        Piece mediumEagle = eaglePieces.get(Hierarchy.MEDIUM).get(0);
        Piece babyEagle = eaglePieces.get(Hierarchy.BABY).get(0);
        Piece smallEagle = eaglePieces.get(Hierarchy.SMALL).get(0);
		board.getTile(9, 3).setPiece(bigEagle, getPieceView(bigEagle));
		board.getTile(9, 4).setPiece(mediumEagle, getPieceView(mediumEagle));
		board.getTile(9, 5).setPiece(babyEagle, getPieceView(babyEagle));
		board.getTile(9, 6).setPiece(smallEagle, getPieceView(smallEagle));
	}
}
