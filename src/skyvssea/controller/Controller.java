package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import skyvssea.model.*;
import skyvssea.model.command.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.List;

public class Controller {

    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
    private HistoryManager historyManager;
    private ActionPane actionPane;
    private BoardPane boardPane;
    private InfoPane infoPane;
    private boolean isPassiveEffectBtnClicked = false;
    
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
            	// Switch on/off registeredPiece's passiveEffect if passiveEffectBtn is clicked
            	if (actionPane.getPassiveEffectBtn().isSelected() != registeredPiece.isPassiveEffectActivated()) {
            		TogglePassiveEffectCommand togglePassiveEffectCommand;
    				togglePassiveEffectCommand = new TogglePassiveEffectCommand(registeredPiece, previousRegisteredTile, board, playerManager);		            				
            		historyManager.storeAndExecute(togglePassiveEffectCommand);
            	}
            	
            	// Change piece location to a new tile
            	Command moveCommand;
            	moveCommand = new MoveCommand(registeredPiece, previousRegisteredTile, selectedTile, playerManager, board);
                historyManager.storeAndExecute(moveCommand);                     

                // If the player undoes and then makes a move, undo will no longer be available 
                if (!playerManager.getCurrentPlayer().validateUndoAvailability()) {
                	actionPane.setUndoBtnDisable(true);
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
                        	actionPane.setPassiveEffectBtnActivated(isPassiveEffectActivated);                     	
                        	actionPane.setPassiveEffectBtnDisable(false);
                        } else {
                        	// For baby pieces with no passive effect, must ensure the passive btn is default color and disabled
                        	actionPane.disableAndDeactivatePassiveEffectBtn();
                        }
                    } else {
                    	// Click on an enemy piece
                    	actionPane.disableAndDeactivatePassiveEffectBtn();
                    }
                } else {
                	// Click on a unhighlighted tile with no piece
                	actionPane.disableAndDeactivatePassiveEffectBtn();
                }
            }
            
        } else if (game.getCurrentGameState() == GameState.PERFORMING_ACTIVE_EFFECT) {
            if (selectedTile.isHighlighted()) {
                AbstractPiece target = (AbstractPiece) selectedTile.getGameObject();
                Command performSpecialEffectCommand = new PerformActiveEffectCommand(registeredPiece, target);
                historyManager.storeAndExecute(performSpecialEffectCommand);
                endTurn();
            }
            
        } else if (game.getCurrentGameState() == GameState.KILLING) {
        	if (selectedTile.isHighlighted()) {
                AbstractPiece target = (AbstractPiece) selectedTile.getGameObject();
                Command killCommand = new KillCommand(target, selectedTile, board, playerManager);  
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
        actionPane.setPassiveEffectBtnDisable(true);
        actionPane.setKillBtnDisable(false);
        actionPane.setEndBtnDisable(false);
        
        boolean isActiveEffectAvailable = pieceManager.getRegisteredPiece().isActiveEffectAvailable();
        actionPane.setActiveEffectBtnDisable(!isActiveEffectAvailable);
        
        if (isPassiveEffectBtnClicked) {
        	isPassiveEffectBtnClicked = false;
        	board.setUnhighlightedTilesDisable(false);        	
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
		isPassiveEffectBtnClicked = !isPassiveEffectBtnClicked;
		board.setUnhighlightedTilesDisable(isPassiveEffectBtnClicked); // Prevent clicking passiveEffect for more than 1 pieces
	}

    public void handleEndButton() { endTurn(); }

    public void handleUndoButton() {
        playerManager.getCurrentPlayer().reduceNumUndos();
        historyManager.undoToMyTurn();
        startNewTurn();
    }
    
    public void clearCache() {
        board.clearHighlightedTiles();
        board.clearRegisteredTile();
        pieceManager.clearRegisteredPiece();
    }

    private void endTurn() {
    	pieceManager.updatePieceStatus(historyManager);
        playerManager.changeTurn();
        historyManager.startNewTurnCommand();
        
        startNewTurn();
    }

    public void startNewTurn() {
    	game.setCurrentGameState(GameState.READY_TO_MOVE);
        actionPane.disableRegularActionPane();
        actionPane.hideActionIndicator();
        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());
        clearCache();
        
		boolean isUndoEmpty = !getPlayerManager().getCurrentPlayer().isUndoAvailabile();
		boolean hasHistory = getHistoryManager().isUndoAvailable();
		boolean isUndoAvailable = !isUndoEmpty && hasHistory;
		actionPane.setUndoBtnDisable(!isUndoAvailable);
    }
    
    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setController(BoardSetupView boardSetup, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
        int boardCol = boardSetup.getBoardSize()[0];
        int boardRow = boardSetup.getBoardSize()[1];

		this.actionPane = actionPane;
		this.boardPane = boardPane;
		this.infoPane = infoPane;

        board = new Board(boardCol, boardRow);
        pieceManager = new PieceManager(boardSetup.getPieceLineup());
        playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        historyManager = new HistoryManager();
        game = new Game();

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
            tiles[x][y].attach(tileView);
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

	public PlayerManager getPlayerManager() { return playerManager; }
	public HistoryManager getHistoryManager() { return historyManager; }
}