package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int col;
    private int row;
	private Tile[][] tiles;
	private List<Tile> highlightedTiles = new ArrayList<>();
	private Tile registeredTile;

	public Board(int col, int row) {
		this.col = col;
		this.row = row;
		tiles = new Tile[col][row];

		for (int x = 0; x < col; x++) {
			for (int y = 0; y < row; y++) {
				tiles[x][y] = new Tile(x, y);
			}
		}
	}

    public Tile[][] getTiles() {
        return tiles;
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

	@Requires("tile != null")
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
	
	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public Tile getRegisteredTile() { return registeredTile; }

	@Requires("tile != null")
	public void setRegisteredTile(Tile tile) {
		this.registeredTile = tile;
	}

	public void clearRegisteredTile() {
		this.registeredTile = null;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && y >= 0 && x < col && y < row) {
			return tiles[x][y];
		}
		return null;
	}

	public void setBaseColours() {
		for (int x = 0; x < col; x++) {
			for (int y = 0; y < row; y++) {
				tiles[x][y].setHighlighted(false);
			}
		}
	}

	@Requires("registeredTile != null")
	public void highlightPossibleMoveTiles() {
		AbstractPiece selectedPiece = (AbstractPiece) registeredTile.getGameObject();
		int moveRange = selectedPiece.getMoveRange();	
		List<Direction> tempDirections = new ArrayList<>(Arrays.asList(selectedPiece.getMoveDirections()));
	
	    for (int count = 1; count <= moveRange; count++) {
	        List<Direction> blockedDirections = new ArrayList<>();
	        for (Direction direction : tempDirections) {
	        	if (direction == Direction.JUMP_OVER) {
	        		continue;
	        	}
	        	
	            Tile currentTile = getTile(registeredTile, direction, count);
	            if (currentTile == null) {
	            	// Out of bound, so ignore other tiles in this direction
	            	blockedDirections.add(direction);
	            	continue;
	            }
	            
	            if (currentTile.hasGameObject()) {
	            	if (!tempDirections.contains(Direction.JUMP_OVER)) {
	            		blockedDirections.add(direction);
	            	}
	            } else {
	            	highlightTile(currentTile);
	            }
	        }
	        tempDirections.removeAll(blockedDirections);
	    }
	    
	    highlightTile(registeredTile);
	}

	@Requires("playerManager != null && registeredTile != null")
	public void highlightPossibleKillTiles(PlayerManager playerManager) {
		AbstractPiece selectedPiece = (AbstractPiece) registeredTile.getGameObject();

		for (Tile currentTile : getDetectablePieceLocation(registeredTile)) {
			AbstractPiece currentPiece = (AbstractPiece) currentTile.getGameObject();
			if (!playerManager.isCurrentPlayerPiece(currentPiece)) {
				Hierarchy enemyDefenceLevel = currentPiece.getDefenceLevel();
				Hierarchy selectedPieceAttackLevel = selectedPiece.getAttackLevel();
				// Only able to kill an enemy with strictly lower defense level
				if (selectedPieceAttackLevel.compareTo(enemyDefenceLevel) > 0) {
					highlightTile(currentTile);
				}
			}
		}
	}

	@Requires("playerManager != null && registeredTile != null")
	public void highlightPossibleActiveEffectTiles(PlayerManager playerManager) {
		AbstractPiece selectedPiece = (AbstractPiece) registeredTile.getGameObject();
		SpecialEffectObject activeEffect = selectedPiece.getActiveEffect();
		
	    for (Tile currentTile : getDetectablePieceLocation(registeredTile)) {
			AbstractPiece currentPiece = (AbstractPiece) currentTile.getGameObject();
			if (activeEffect.usableOnPiece(currentPiece, playerManager)) {
				highlightTile(currentTile);
			}
		}
	}

	@Requires("tile.getGameObject() != null && tile.getGameObject() instanceof AbstractPiece")
	private List<Tile> getDetectablePieceLocation(Tile tile) {
		AbstractPiece selectedPiece = (AbstractPiece) tile.getGameObject();
		List<Tile> detectableTilesWithPiece = new ArrayList<>();
		detectableTilesWithPiece.add(tile);

		int attackRange = selectedPiece.getAttackRange();
		List<Direction> tempDirections = new ArrayList<>(Arrays.asList(selectedPiece.getAttackDirections()));

		for (int count = 1; count <= attackRange; count++) {
			List<Direction> blockedDirections = new ArrayList<>();
			for (Direction direction : tempDirections) {
				Tile currentTile = getTile(tile, direction, count);
				if (currentTile == null) {
					//out of bound
					blockedDirections.add(direction);
					continue;
				}

				if (currentTile.hasGameObject()) {
					if (currentTile.hasPiece()) {
						detectableTilesWithPiece.add(currentTile);
					}
					blockedDirections.add(direction);
				}
			}
			tempDirections.removeAll(blockedDirections);
		}
		return detectableTilesWithPiece;
	}

	public List<Tile> getSurroundingTiles(Tile tile) {
		List<Tile> surroundingTiles = new ArrayList<>();
		List<Direction> directions = Direction.getEightDirections();
		int count = 1;
		for (Direction direction : directions) {
			Tile currentTile = getTile(tile, direction, count);
			if (currentTile != null) {
				surroundingTiles.add(currentTile);				
			}
		}
		return surroundingTiles;
	}
	
	public void setUnhighlightedTilesDisable(boolean disable) {
		for (Tile[] row : getTiles()) {
			for (Tile tile : row) {
				if (!tile.isHighlighted()) {
					tile.notifyObservers(EventType.DISABLE, disable);
				}
			}
		}
	}
}
