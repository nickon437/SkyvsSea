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

	@Requires("rootTile != null && distance >= 0")
	public Tile getTile(Tile rootTile, Direction dir, int distance) {
		int rootX = rootTile.getX();
		int rootY = rootTile.getY();
		Tile tile;
		switch (dir) {
			case NORTH:
				tile = getTile(rootX, rootY - distance);
				break;
			case NORTHEAST:
				tile = getTile(rootX + distance, rootY - distance);
				break;
			case EAST:
				tile = getTile(rootX + distance, rootY);
				break;
			case SOUTHEAST:
				tile = getTile(rootX + distance, rootY + distance);
				break;
			case SOUTH:
				tile = getTile(rootX, rootY + distance);
				break;
			case SOUTHWEST:
				tile = getTile(rootX - distance, rootY + distance);
				break;
			case WEST:
				tile = getTile(rootX - distance, rootY);
				break;
			case NORTHWEST:
				tile = getTile(rootX - distance, rootY - distance);
				break;
			default:
				tile = null;
		}
		return tile;
	}

	// Nick - A special case where highlighting an occupied tile. Having this allows highlightUnoccupiedTile() enforces strict preconditions.
	public void highlightCurrentTile() {
		currentTile.setHighlighted(true);
		highlightedTiles.add(currentTile);
	}

	@Requires("tile != null && !tile.hasPiece()")
	public void highlightUnoccupiedTile(Tile tile) {
		tile.setHighlighted(true);
		highlightedTiles.add(tile);
	}

	public void clearHighlightedTiles() {
		for (Tile tile : highlightedTiles) {
			tile.setHighlighted(false);
		}
		highlightedTiles.clear();
	}

	public Tile getCurrentTile() { return currentTile; }

	@Requires("currentTile != null")
	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	public void clearCurrentTile() {
		this.currentTile = null;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && y >= 0 && x < NUM_SIDE_CELL && y < NUM_SIDE_CELL) {
			return tiles[x][y];
		}
		return null;
	}

	public void setBaseColours() {
		for (int x = 0; x < NUM_SIDE_CELL; x ++) {
			for (int y = 0; y < NUM_SIDE_CELL; y ++) {
				tiles[x][y].setHighlighted(false);
			}
		}
	}
}
