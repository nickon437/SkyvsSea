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
	public List<Tile> getTileList() {
		List<Tile> tileList = new ArrayList<>();
		for (Tile[] tiles : tiles) {
			tileList.addAll(Arrays.asList(tiles));
		}
		return tileList;
	}

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
	
	public void highlightPossibleMoveTiles(Tile selectedTile) {
		AbstractPiece selectedPiece = (AbstractPiece) selectedTile.getGameObject();
		int moveRange = selectedPiece.getMoveRange();	
		List<Direction> tempDirections = new ArrayList<>(Arrays.asList(selectedPiece.getMoveDirections()));
	
	    for (int count = 1; count <= moveRange; count++) {
	        ArrayList<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	        	if (direction == Direction.JUMP_OVER) {
	        		continue;
	        	}
	        	
	            Tile currentTile = getTile(selectedTile, direction, count);
	            if (currentTile == null) {
	            	//out of bound, so ignore other tiles in this direction
	            	blockedDirections.add(direction);
	            	System.out.println("out of bound");
	            	continue;
	            }
	            
	            if (currentTile.hasGameObject()) {
	            	if (!tempDirections.contains(Direction.JUMP_OVER)) {
	            		blockedDirections.add(direction);
		            	System.out.println("can't jump over");
	            	}
	            } else {
	            	highlightTile(currentTile);
	            }
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	    
	    highlightTile(selectedTile);
	}

	public void highlightPossibleKillTiles(PlayerManager playerManager) {
	    Tile selectedTile = getCurrentTile();
		AbstractPiece selectedPiece = (AbstractPiece) selectedTile.getGameObject();
	    int attackRange = selectedPiece.getAttackRange();
	    List<Direction> tempDirections = new ArrayList<>(Arrays.asList(selectedPiece.getAttackDirections()));
	    
	    for (int count = 1; count <= attackRange; count++) {
	        ArrayList<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	            Tile currentTile = getTile(selectedTile, direction, count);
	            if (currentTile == null) {
	            	//out of bound
	            	blockedDirections.add(direction);
	            	continue;
	            }
	            
	            if (currentTile.hasGameObject()) {
	            	if (currentTile.hasPiece()) {
	            		AbstractPiece currentPiece = (AbstractPiece) currentTile.getGameObject();
	            		if (!playerManager.isCurrentPlayerPiece(currentPiece)) {
	            			Hierarchy enemyDefenceLevel = currentPiece.getDefenceLevel();
	            			Hierarchy selectedPieceAttackLevel = selectedPiece.getAttackLevel();
	            			//Only able to kill an enemy with strictly lower defense level
	            			if (selectedPieceAttackLevel.compareTo(enemyDefenceLevel) > 0) {
	            				highlightTile(currentTile);  
	            			}
	            		}
	            	} 
	            	blockedDirections.add(direction);	
	            }
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	}
	
	public void highlightPossibleSpecialEffectTiles(PlayerManager playerManager) {
		Tile selectedTile = getCurrentTile();
		AbstractPiece selectedPiece = (AbstractPiece) selectedTile.getGameObject();
	    TargetType targetType = selectedPiece.getSpecialEffectTargetType();
	    
	    if (targetType == TargetType.SELF) {
	    	highlightTile(selectedTile);
	    	return;
	    } 
	    
	    int attackRange = selectedPiece.getAttackRange();
	    List<Direction> tempDirections = new ArrayList<>(Arrays.asList(selectedPiece.getAttackDirections()));
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
	            
	            if (currentTile.hasGameObject()) {
	            	if (currentTile.hasPiece()) {
	            		AbstractPiece currentPiece = (AbstractPiece) currentTile.getGameObject();
	            		boolean currentPlayerHasPiece = playerManager.isCurrentPlayerPiece(currentPiece);
	            		if (isComrades == currentPlayerHasPiece) { 
	            			highlightTile(currentTile);  
	            		}
	            	} 
	            	blockedDirections.add(direction);	            	
	            }
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	}
}
