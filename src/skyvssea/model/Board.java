package skyvssea.model;

import java.util.ArrayList;
import java.util.Map;

import skyvssea.controller.Controller;
import skyvssea.view.TilePane;

class Board {
	Tile[][] tiles;
	
	public Board() {
		 //TODO: instantiate the tiles here
		
	}

	/**
	 * Set pieces on their respective initial tile 
	 * @param sharkPieces the intial lineup of shark pieces
	 * @param eaglePieces the intial lineup of eagle pieces
	 */
	public void setPiecesOnTiles(Map<Hierarchy, ArrayList<Piece>> sharkPieces, Map<Hierarchy, ArrayList<Piece>> eaglePieces) {
		// TODO: if the number of pieces and the starting position does not change, can reduce the code below with loops
		tiles[0][3].setPiece(sharkPieces.get(Hierarchy.BIG).get(0));
		tiles[0][4].setPiece(sharkPieces.get(Hierarchy.MEDIUM).get(0));
		tiles[0][5].setPiece(sharkPieces.get(Hierarchy.BABY).get(0));
		tiles[0][6].setPiece(sharkPieces.get(Hierarchy.SMALL).get(0));
		
		tiles[9][3].setPiece(eaglePieces.get(Hierarchy.SMALL).get(0));
		tiles[9][4].setPiece(eaglePieces.get(Hierarchy.BABY).get(0));
		tiles[9][5].setPiece(eaglePieces.get(Hierarchy.MEDIUM).get(0));
		tiles[9][6].setPiece(eaglePieces.get(Hierarchy.BIG).get(0));
	}

}
