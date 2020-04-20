package skyvssea.model;

import java.util.ArrayList;

public class Board {
	public static final int NUM_SIDE_CELL = 10;
	private Tile[][] tiles;
	private ArrayList<Tile> highlightedTiles = new ArrayList<>();
	private Tile currentTile;

	public Board(/* Group tileViews */) {
		//TODO: should create the Tile objects here instead of getting from boardPane
//		tiles = boardPane.getTiles();
		tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];
        
//        for (Node node : tileViews.getChildren()) {
//        	TilePane tilePane = (TilePane) node;
//        	int x = tilePane.getX();
//        	int y = tilePane.getY();
//        	tiles[x][y] = new Tile(tilePane, (x + y) % 2 == 0);
//        }
        
		for (int x = 0; x < NUM_SIDE_CELL; x ++) {
			for (int y = 0; y < NUM_SIDE_CELL; y ++) {
				tiles[x][y] = new Tile(x, y, (x + y) % 2 == 0);
			}
		}
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

	public void setBaseColours() {
		for (int x = 0; x < NUM_SIDE_CELL; x ++) {
			for (int y = 0; y < NUM_SIDE_CELL; y ++) {
				tiles[x][y].setHighlighted(false);
			}
		}
		
	}

}