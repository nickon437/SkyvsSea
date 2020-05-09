package skyvssea.model;

import com.google.java.contract.Requires;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.TargetType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	public static final int NUM_SIDE_CELL = 10;
	private Tile[][] tiles;
	private ArrayList<Tile> highlightedTiles = new ArrayList<>();
	private Tile currentTile;

	public Board() {
		tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];

		for (int x = 0; x < NUM_SIDE_CELL; x ++) {
			for (int y = 0; y < NUM_SIDE_CELL; y ++) {
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

	public void highlightPossibleKillTiles(PlayerManager playerManager) {
	    Tile selectedTile = getCurrentTile();
	    AbstractPiece selectedPiece = selectedTile.getPiece();
	    
	    int attackRange = selectedPiece.getAttackRange();
	    
	    List<Direction> tempDirections = new ArrayList<>();
	    Direction attackDirections[] = selectedPiece.getAttackDirections();
	    tempDirections.addAll(Arrays.asList(attackDirections));
	    
	    for (int count = 1; count <= attackRange; count++) {
	        ArrayList<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	            Tile currentTile = getTile(selectedTile, direction, count);
	            if (currentTile == null) {
	            	//out of bound
	            	blockedDirections.add(direction);
	            	continue;
	            }
	            
	            if (currentTile.hasPiece()) {
	            	AbstractPiece currentPiece = currentTile.getPiece();
	            	if (!playerManager.isCurrentPlayerPiece(currentPiece)) {
	            		Hierarchy enemyDefenceLevel = currentPiece.getDefenceLevel();
	            		Hierarchy selectedPieceAttackLevel = selectedPiece.getAttackLevel();
	            		//Only able to kill an enemy with strictly lower defense level
	            		if (selectedPieceAttackLevel.compareTo(enemyDefenceLevel) > 0) {
	            			highlightTile(currentTile);  
	            		}
	            	}
	            	blockedDirections.add(direction);
	            } 
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	}

	public void highlightPossibleMoveTiles(Tile selectedTile) {
		AbstractPiece piece = selectedTile.getPiece();
	    int numMove = piece.getMoveRange();
	
	    highlightTile(selectedTile);
	
	    List<Direction> tempDirections = new ArrayList<>(Arrays.asList(piece.getMoveDirections()));
	    for (int count = 1; count <= numMove; count++) {
	        ArrayList<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	            Tile tile = getTile(selectedTile, direction, count);
	
	            if (tile != null) {
	                if (tile.hasPiece()) {
	                    if (!tempDirections.contains(Direction.JUMP_OVER)) {
	                        blockedDirections.add(direction);
	                    }
	                } else {
	                    highlightTile(tile);
	                }
	            }
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	}

	public void highlightPossibleSpecialEffectTiles(PlayerManager playerManager) {
		Tile selectedTile = getCurrentTile();
	    AbstractPiece selectedPiece = selectedTile.getPiece();
	    TargetType targetType = selectedPiece.getSpecialEffectTargetType();
	    
	    if (targetType == TargetType.SELF) {
	    	highlightTile(selectedTile);
	    	return;
	    } 
	    
	    int attackRange = selectedPiece.getAttackRange();
	    List<Direction> tempDirections = new ArrayList<>();
	    Direction attackDirections[] = selectedPiece.getAttackDirections();
	    tempDirections.addAll(Arrays.asList(attackDirections));
	    
	    boolean isComrades = true;
	    if (targetType == TargetType.ENEMIES) {
	    	isComrades = false;
	    }
	    
	    for (int count = 1; count <= attackRange; count++) {
	        ArrayList<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	            Tile currentTile = getTile(selectedTile, direction, count);
	            if (currentTile == null) {
	            	//out of bound
	            	blockedDirections.add(direction);
	            	continue;
	            }
	            
	            if (currentTile.hasPiece()) {
	            	AbstractPiece currentPiece = currentTile.getPiece();
	            	boolean isCurrentPlayerPiece = playerManager.isCurrentPlayerPiece(currentPiece);
	            	if (isComrades == isCurrentPlayerPiece) { 
	            		highlightTile(currentTile);  
	            	}
	            	blockedDirections.add(direction);
	            } 
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	}
}
