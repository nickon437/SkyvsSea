package skyvssea.model;

import skyvssea.view.PieceView;

import java.util.ArrayList;
import java.util.Map;

public class PieceManager {
    // Need review
    // Reason for using a hashmap to store the pieces: easier to assign the pieces to initial tile locations
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

    //TODO: SHould change the return type to listener interface; might be removed
//    public ArrayList<PieceView> getAllPieceViews() {
//        ArrayList<PieceView> allPieceViews = new ArrayList<>();
//        for (Piece piece : getAllPieces()) {
//            allPieceViews.add(piece.getPieceView());
//        }
//        return allPieceViews;
//    }

    public ArrayList<String> getNames() {
    	ArrayList<String> allPieceNames = new ArrayList<>();
    	for (Piece piece : getAllPieces()) {
    		allPieceNames.add(piece.getName());
    	}
    	return allPieceNames;
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
		// TODO: if the number of pieces and the starting position does not change, can reduce the code below with loops
		board.getTile(0, 3).setPiece(sharkPieces.get(Hierarchy.BIG).get(0));
		board.getTile(0, 4).setPiece(sharkPieces.get(Hierarchy.MEDIUM).get(0));
		board.getTile(0, 5).setPiece(sharkPieces.get(Hierarchy.BABY).get(0));
		board.getTile(0, 6).setPiece(sharkPieces.get(Hierarchy.SMALL).get(0));

		board.getTile(9, 3).setPiece(eaglePieces.get(Hierarchy.SMALL).get(0));
		board.getTile(9, 4).setPiece(eaglePieces.get(Hierarchy.BABY).get(0));
		board.getTile(9, 5).setPiece(eaglePieces.get(Hierarchy.MEDIUM).get(0));
		board.getTile(9, 6).setPiece(eaglePieces.get(Hierarchy.BIG).get(0));
		
	}
}
