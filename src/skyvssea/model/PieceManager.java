package skyvssea.model;

import skyvssea.view.PieceView;

import java.util.ArrayList;
import java.util.HashMap;
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

//    public ArrayList<Piece> getAllPieces() {
//        ArrayList<Piece> allPieces = new ArrayList<>();
//        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getSharkPieces().entrySet()) {
//            allPieces.addAll(entry.getValue());
//        }
//        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : getEaglePieces().entrySet()) {
//            allPieces.addAll(entry.getValue());
//        }
//        return allPieces;
//    }

    // T-O-D-O: Should change the return type to listener interface; might be removed
    // Nick - Re-implement this because we use PieceManager to link Piece and PieceView now
//    public ArrayList<PieceView> getAllPieceViews() {
//        ArrayList<PieceView> allPieceViews = new ArrayList<>();
//        for (Piece piece : getAllPieces()) {
//            allPieceViews.add(getPieceView(piece));
//        }
//        return allPieceViews;
//    }
    
//    public Piece getPiece(String name) {
//    	ArrayList<Piece> pieces = getAllPieces();
//    	for (Piece piece : pieces) {
//    		if (piece.getName().equals(name)) {
//    			return piece;
//    		}
//    	}
//    	return null;
//    }
    
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

	public ArrayList<Tile> setPiecesOnBoard(Board board) {
        // Nick - TODO: Need to refactor this to loop for flexible board size and number of pieces

		ArrayList<Tile> startingPositions = new ArrayList<>();
		Piece bigShark = sharkPieces.get(Hierarchy.BIG).get(0);
        Piece mediumShark = sharkPieces.get(Hierarchy.MEDIUM).get(0);
        Piece babyShark = sharkPieces.get(Hierarchy.BABY).get(0);
        Piece smallShark = sharkPieces.get(Hierarchy.SMALL).get(0);
        board.getTile(0, 3).setPiece(bigShark);
        startingPositions.add(board.getTile(0, 3));
		board.getTile(0, 4).setPiece(mediumShark);
        startingPositions.add(board.getTile(0, 4));
		board.getTile(0, 5).setPiece(babyShark);
		startingPositions.add(board.getTile(0, 5));
		board.getTile(0, 6).setPiece(smallShark);
		startingPositions.add(board.getTile(0, 6));

        Piece bigEagle = eaglePieces.get(Hierarchy.BIG).get(0);
        Piece mediumEagle = eaglePieces.get(Hierarchy.MEDIUM).get(0);
        Piece babyEagle = eaglePieces.get(Hierarchy.BABY).get(0);
        Piece smallEagle = eaglePieces.get(Hierarchy.SMALL).get(0);
		board.getTile(9, 3).setPiece(bigEagle);
		startingPositions.add(board.getTile(9, 3));
		board.getTile(9, 4).setPiece(mediumEagle);
		startingPositions.add(board.getTile(9, 4));
		board.getTile(9, 5).setPiece(babyEagle);
		startingPositions.add(board.getTile(9, 5));
		board.getTile(9, 6).setPiece(smallEagle);
		startingPositions.add(board.getTile(9, 6));
		
		return startingPositions;
	}

}
