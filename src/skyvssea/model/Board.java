package skyvssea.model;

import skyvssea.view.BoardPane;
import skyvssea.view.TilePane;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Node;

public class Board {
	public static final int NUM_SIDE_CELL = 10;
	private Tile[][] tiles;
	private ArrayList<Tile> highlightedTiles = new ArrayList<>();
	private Tile currentTile;

	public Board(Group tileViews) {
		//TODO: should create the Tile objects here instead of getting from boardPane
//		tiles = boardPane.getTiles();
		tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];
        
        for (Node node : tileViews.getChildren()) {
        	TilePane tilePane = (TilePane) node;
        	int x = tilePane.getX();
        	int y = tilePane.getY();
        	tiles[x][y] = new Tile(tilePane, (x + y) % 2 == 0);
        }
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

	public Tile[][] getTiles() { return tiles; }

	// Nick - This method and clearHighlightedTiles() are originally in Controller class. Not sure if these methods should be there or here
	public void highlightUnoccupiedTiles(Tile tile) {
		if (!tile.hasPiece()) {
			tile.setHighlighted(true);
			highlightedTiles.add(tile);
		}
	}

	public void clearHighlightedTiles() {
		for (Tile tile : highlightedTiles) {
			tile.setHighlighted(false);
		}
		highlightedTiles.clear();
	}

	public Tile getCurrentTile() { return currentTile; }

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

}