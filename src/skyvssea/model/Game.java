package skyvssea.model;

import java.util.ArrayList;
import java.util.Map;

class Game {
	//Need review
	// Reason for using a hashmap to store the pieces: easier to assign the pieces to initial tile locations
	private Map<Hierarchy, ArrayList<Piece>> sharkPieces;
	private Map<Hierarchy, ArrayList<Piece>> eaglePieces;
	private Board board;
	
	public Game() {
		initializePieces();
		board = new Board();
		board.setPiecesOnTiles(sharkPieces, eaglePieces);
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
}
