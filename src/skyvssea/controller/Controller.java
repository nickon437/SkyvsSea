package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.*;

public class Controller {

    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
    private ActionPane actionPane;
    private InfoPane infoPane;

    @Requires("tileView != null")
    public void handleTileClicked(TileView tileView) {
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
        	return;
        }
    	
        final Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
        AbstractPiece registeredPiece = pieceManager.getRegisteredPiece();

        if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
            Tile previousRegisteredTile = board.getRegisteredTile();
            board.setRegisteredTile(selectedTile);

            if (selectedTile.isHighlighted()) {
            	board.clearHighlightedTiles();
            	// Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
                if (!selectedTile.equals(previousRegisteredTile)) {
                	// Handle removing passive effect from a moving piece if the moving piece moves away from a passive effect caster
                	previousRegisteredTile.removeSpecialEffects();  
                	previousRegisteredTile.removeGameObject();
                	
                    selectedTile.setGameObject(registeredPiece);
                    // Handle applying passive effect to a moving piece if the moving piece moves towards a passive effect caster
                    selectedTile.applySpecialEffects(playerManager);                         
                }

                // Handle setting up transmittable passive effect on nearby tiles so that it can be applied to a piece moving to any of the nearby tiles
                if (registeredPiece.isPassiveEffectActivated() && registeredPiece.isPassiveEffectTransmittable()) {
                	List<Tile> surroundingTiles;
                	if (!selectedTile.equals(previousRegisteredTile)) {
                		surroundingTiles = board.getSurroundingTiles(previousRegisteredTile);  
                		surroundingTiles.forEach(tile -> tile.removeSpecialEffect(registeredPiece.getPassiveEffect()));
                	}
                	surroundingTiles = board.getSurroundingTiles(selectedTile);
                	surroundingTiles.forEach(tile -> tile.addSpecialEffect(registeredPiece.getPassiveEffect(), playerManager));
                }
                
                switchToAttackMode();

            } else {
            	board.clearHighlightedTiles();
                if (selectedTile.hasPiece()) {
                    AbstractPiece newSelectedPiece = (AbstractPiece) selectedTile.getGameObject();
                    if (playerManager.isCurrentPlayerPiece(newSelectedPiece)) {
                        pieceManager.setRegisteredPiece(newSelectedPiece);
                        board.highlightPossibleMoveTiles();
                        
                        // Configure the passive btn after clicking a piece
                        if (newSelectedPiece.getPassiveEffect() != null) {
                        	boolean isPassiveEffectActivated = newSelectedPiece.isPassiveEffectActivated();
                        	actionPane.enablePassiveEffectBtn(isPassiveEffectActivated);                        	
                        } else {
                        	// For baby pieces with no passive effect, must ensure the passive btn is default color and disabled
                        	actionPane.deactivatePassiveEffectBtn();
                        	actionPane.disablePassiveEffectBtn();
                        }
                    } else {
                    	// Click on an enemy piece
                    	actionPane.disableAllButtons();
                    }
                } else {
                	// Click on a unhighlighted tile with no piece
                	actionPane.disableAllButtons();
                }
            }
            
        } else if (game.getCurrentGameState() == GameState.PERFORMING_ACTIVE_EFFECT) {
            if (selectedTile.isHighlighted()) {
                registeredPiece.performActiveEffect((AbstractPiece) selectedTile.getGameObject());
                endTurn();
            }
            
        } else if (game.getCurrentGameState() == GameState.KILLING) {
        	if (selectedTile.isHighlighted()) {
        		AbstractPiece target = (AbstractPiece) selectedTile.getGameObject();
        		if (target.isPassiveEffectActivated() && target.isPassiveEffectTransmittable()) {
                	List<Tile> surroundingTiles = board.getSurroundingTiles(selectedTile);       
                	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(target.getPassiveEffect()));
        		}
        		selectedTile.removeGameObject();
        		endTurn();
            }
        }   
    }

    @Requires("tileView != null")
    public void handleMouseEnteredTile(TileView tileView) {
        Tile hoveringTile = board.getTile(tileView.getX(), tileView.getY());
        tileView.updateBaseColorAsHovered(true);
        if (hoveringTile.hasPiece()) {
            AbstractPiece hoveringPiece = (AbstractPiece) hoveringTile.getGameObject();
            infoPane.setPieceInfo(hoveringPiece);
        }

        if (hoveringTile.isHighlighted()) {
            tileView.setCursor(Cursor.HAND);
        } else if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
            GameObject currentObject = hoveringTile.getGameObject();
            if (currentObject instanceof AbstractPiece &&
            		playerManager.isCurrentPlayerPiece((AbstractPiece) currentObject)) {
                tileView.setCursor(Cursor.HAND);
            }
        }
    }

    @Requires("tileView != null")
    public void handleMouseExitedTile(TileView tileView) {
        tileView.updateBaseColorAsHovered(false);
    }

    private void switchToAttackMode() {
        game.setCurrentGameState(GameState.READY_TO_ATTACK);
        actionPane.enableKillBtn();
        actionPane.disablePassiveEffectBtn();
        actionPane.enableEndBtn();
        AbstractPiece currentPiece = pieceManager.getRegisteredPiece();
        if (currentPiece.isActiveEffectAvailable()) {
        	actionPane.enableActiveEffectBtn();
        } else {
        	actionPane.disableActiveEffectBtn();
        }
    }

    public void handleKillButton() { 
    	game.setCurrentGameState(GameState.KILLING); 
	}
   
    public void handleMouseEnteredKillBtn() { 
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
    		board.highlightPossibleKillTiles(playerManager); 
    	} else if (game.getCurrentGameState() == GameState.PERFORMING_ACTIVE_EFFECT) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleKillTiles(playerManager);
    	}
	}
    
    public void handleMouseExitedKillBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.PERFORMING_ACTIVE_EFFECT) {
        	board.clearHighlightedTiles();
        	board.highlightPossibleActiveEffectTiles(playerManager);
        }
    }

    public void handleActiveEffectButton() { 
    	game.setCurrentGameState(GameState.PERFORMING_ACTIVE_EFFECT); 
	}
    public void handleMouseEnteredActiveEffectBtn() { 
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
    		board.highlightPossibleActiveEffectTiles(playerManager);     		
    	} else if (game.getCurrentGameState() == GameState.KILLING) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleActiveEffectTiles(playerManager);
    	}
	}
    public void handleMouseExitedActiveEffectBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.KILLING) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleKillTiles(playerManager);
    	}
    }
    
	public void handlePassiveEffectButton() {
		AbstractPiece registeredPiece = pieceManager.getRegisteredPiece();
		registeredPiece.togglePassiveEffectSwitch();
		
		// Remove transmittable passiveEffect from surrounding tiles and pieces if passiveEffect is turned off
		if (!registeredPiece.isPassiveEffectActivated() && registeredPiece.isPassiveEffectTransmittable()) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(board.getRegisteredTile());      
        	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(registeredPiece.getPassiveEffect()));
        }
	}

    public void handleEndButton() { endTurn(); }

    private void changeTurn() {
        Player player = playerManager.changeTurn();
        infoPane.setPlayerInfo(player);

        board.clearHighlightedTiles();
        board.clearCurrentTile();
        pieceManager.clearCurrentPiece();
    }

    private void endTurn() {
        pieceManager.updatePieceStatus();
        changeTurn();
        game.setCurrentGameState(GameState.READY_TO_MOVE);
        actionPane.disableAllButtons();
        
        // TODO: Add save for undo here
    }

    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setViewsAndModels(BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	this.actionPane = actionPane;
    	this.infoPane = infoPane;

    	this.board = new Board();
		this.pieceManager = new PieceManager(createInitialLineUp());
		this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());

        setTiles(boardPane);
        setPieces(boardPane);
        setObstacles(boardPane);
    }

    @Requires("boardPane != null")
    private void setTiles(BoardPane boardPane) {
        Tile[][] tiles = board.getTiles();
        List<TileView> tileViews = boardPane.getTileViewGroup();
        for (TileView tileView : tileViews) {
            int x = tileView.getX();
            int y = tileView.getY();
            tiles[x][y].addObserver(tileView);
            tiles[x][y].addAvatar(tileView);
        }
        board.setBaseColours();
    }

    @Requires("boardPane != null")
    private void setPieces(BoardPane boardPane) {
        List<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);
        for (Tile tile : startingPositions) {
            AbstractPiece piece = (AbstractPiece) tile.getGameObject();
            Player player = playerManager.getPlayer(piece);
            PieceView pieceView = boardPane.instantiatePieceView(tile.getX(), tile.getY(), piece.getName(), player.getColor());
            piece.addAvatar(pieceView);
        }
    }

    @Requires("boardPane != null")
    private void setObstacles(BoardPane boardPane) {
        ObstacleManager obstacleManager = new ObstacleManager();
        List<Tile> obstacleTiles = obstacleManager.setObstacleOnBoard(board);
        for (Tile tile : obstacleTiles) {
            Obstacle obstacle = (Obstacle) tile.getGameObject();
            ObstacleView obstacleView = boardPane.instantiateObstacleView(tile.getX(), tile.getY());
            obstacle.addAvatar(obstacleView);
        }
    }
    
	private Map<Hierarchy, Integer> createInitialLineUp() {
		Map<Hierarchy, Integer> lineup = new HashMap<>();
		lineup.put(Hierarchy.BIG, 1);
		lineup.put(Hierarchy.MEDIUM, 1);
		lineup.put(Hierarchy.SMALL, 1);
		lineup.put(Hierarchy.BABY, 1);
		return lineup;
	}

	public void startGame() {
		game = new Game();
		game.setCurrentGameState(GameState.READY_TO_MOVE);
        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());
        actionPane.disableAllButtons();
	}
}