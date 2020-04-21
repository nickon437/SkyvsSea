package skyvssea.model;

import com.google.java.contract.Requires;

import java.util.ArrayList;

public class Board {
	public static final int NUM_SIDE_CELL = 10;
	private Tile[][] tiles;
	private ArrayList<Tile> highlightedTiles = new ArrayList<>();
	private Tile currentTile;

	public Board() {
		tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];

		for (int x = 0; x < NUM_SIDE_CELL; x ++) {
			for (int y = 0; y < NUM_SIDE_CELL; y ++) {
				tiles[x][y] = new Tile(x, y, (x + y) % 2 == 0);
			}
		}
	}

	public Tile[][] getTiles() { return tiles; }

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

	// Nick - TODO: Modify precondition when provide flexible board size later
	@Requires("x >= 0 && y >= 0 && x < NUM_SIDE_CELL && y < NUM_SIDE_CELL")
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
