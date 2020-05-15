package skyvssea.model;

import com.google.java.contract.Requires;

import java.util.ArrayList;

public class Board {
	public int col;
	public int row;
	private Tile[][] tiles;
	private ArrayList<Tile> highlightedTiles = new ArrayList<>();
	private Tile currentTile;

	public Board(int col, int row) {
		this.col = col;
		this.row = row;
		this.tiles = new Tile[col][row];

		for (int x = 0; x < col; x ++) {
			for (int y = 0; y < row; y ++) {
				tiles[x][y] = new Tile(x, y);
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

	public void highlightTile(Tile tile) {
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
		if (x >= 0 && y >= 0 && x < col && y < row) {
			return tiles[x][y];
		}
		return null;
	}

	public void setBaseColours() {
		for (int x = 0; x < col; x ++) {
			for (int y = 0; y < row; y ++) {
				tiles[x][y].setHighlighted(false);
			}
		}
	}
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
}
