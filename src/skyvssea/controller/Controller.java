package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import skyvssea.model.*;
import skyvssea.model.command.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.*;

public class Controller {

    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
    private HistoryManager historyManager;
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
            	// Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
                Command moveCommand = new MoveCommand(registeredPiece, previousRegisteredTile, selectedTile);
                historyManager.storeAndExecute(moveCommand);

                switchToAttackMode();

            } else {
            	board.clearHighlightedTiles();
                if (selectedTile.hasPiece()) {
                    AbstractPiece newSelectedPiece = (AbstractPiece) selectedTile.getGameObject();
                    if (playerManager.isCurrentPlayerPiece(newSelectedPiece)) {
                        pieceManager.setRegisteredPiece(newSelectedPiece);
                        board.highlightPossibleMoveTiles();
                    }
                }
            }
            
        } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
            if (selectedTile.isHighlighted()) {
                AbstractPiece target = (AbstractPiece) selectedTile.getGameObject();
                Command performSpecialEffectCommand = new PerformSpecialEffectCommand(registeredPiece, target, historyManager);
                historyManager.storeAndExecute(performSpecialEffectCommand);
                endTurn();
            }
            
        } else if (game.getCurrentGameState() == GameState.KILLING) {
        	if (selectedTile.isHighlighted()) {
                AbstractPiece target = (AbstractPiece) selectedTile.getGameObject();
        	    Command killCommand = new KillCommand(target, selectedTile);
        	    historyManager.storeAndExecute(killCommand);
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
        board.clearHighlightedTiles();
        AbstractPiece currentPiece = pieceManager.getRegisteredPiece();
        actionPane.setSpecialEffectBtnDisable(!currentPiece.isSpecialEffectAvailable());
    }

    public void handleKillButton() { 
    	game.setCurrentGameState(GameState.KILLING); 
	}
   
    public void handleMouseEnteredKillBtn() { 
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
    		board.highlightPossibleKillTiles(playerManager); 
    	} else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleKillTiles(playerManager);
    	}
	}
    
    public void handleMouseExitedKillBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
        	board.clearHighlightedTiles();
        	board.highlightPossibleSpecialEffectTiles(playerManager);
        }
    }

    public void handleSpecialEffectButton() { 
    	game.setCurrentGameState(GameState.PERFORMING_SPECIAL_EFFECT); 
	}

	public void handleMouseEnteredSpecialEffectBtn() {
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
    		board.highlightPossibleSpecialEffectTiles(playerManager);     		
    	} else if (game.getCurrentGameState() == GameState.KILLING) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleSpecialEffectTiles(playerManager);
    	}
	}

    public void handleMouseExitedSpecialEffectBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.KILLING) {
    		board.clearHighlightedTiles();
    		board.highlightPossibleKillTiles(playerManager);
    	}
    }

    public void handleEndButton() { endTurn(); }

    public void handleUndoButton() {
        historyManager.undoMyTurn();
        game.setCurrentGameState(GameState.READY_TO_MOVE);
    }

    private void changeTurn() {
        Player player = playerManager.changeTurn();
        infoPane.setPlayerInfo(player);
    }

    public void clearCache() {
        try {
            board.clearHighlightedTiles();
            board.clearCurrentTile();
            pieceManager.clearCurrentPiece();
        } catch (NullPointerException ignore) {}
    }

    private void endTurn() {
        pieceManager.updatePieceStatus(historyManager);
        changeTurn();
        historyManager.startNewTurnCommand();
        game.setCurrentGameState(GameState.READY_TO_MOVE);
    }

    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setViewsAndModels(BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	this.actionPane = actionPane;
    	this.infoPane = infoPane;

    	this.game = new Game(this, actionPane);
    	this.board = new Board();
		this.pieceManager = new PieceManager(createInitialLineUp());
        this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        this.historyManager = new HistoryManager();

        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());

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
}