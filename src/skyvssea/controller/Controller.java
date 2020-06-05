package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import skyvssea.database.LoadHandler;
import skyvssea.database.SaveHandler;
import skyvssea.model.*;
import skyvssea.model.command.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.List;

public class Controller {

    private Stage stage;
    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
    private HistoryManager historyManager;

    private MainView mainView;
    private ActionPane actionPane;
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
            		TogglePassiveEffectCommand togglePassiveEffectCommand = new TogglePassiveEffectCommand(registeredPiece, 
            				previousRegisteredTile, board, playerManager);		            				
            		historyManager.storeAndExecute(togglePassiveEffectCommand);
            	}
            	
            	// Change piece location to a new tile
            	Command moveCommand = new MoveCommand(registeredPiece, previousRegisteredTile, selectedTile, playerManager, board);
                historyManager.storeAndExecute(moveCommand);                     

                // If the player undoes and then makes a move, undo will no longer be available 
                if (!playerManager.getCurrentPlayer().validateUndoAvailability()) {
                	actionPane.setUndoBtnDisable(true);
                }
                
                switchToAttackMode();
            } else {
            	board.clearHighlightedTiles();
            	actionPane.disableAndDeactivatePassiveEffectBtn();
                if (selectedTile.hasPiece()) {
                    AbstractPiece newSelectedPiece = (AbstractPiece) selectedTile.getGameObject();
                    if (playerManager.isCurrentPlayerPiece(newSelectedPiece)) {
                        pieceManager.setRegisteredPiece(newSelectedPiece);
                        board.highlightPossibleMoveTiles();
                        
                        // Configure the passive btn after clicking a piece
                        // Enable and configure passive btn when the player clicks on its piece
                        if (newSelectedPiece.getPassiveEffect() != null) {
                        	boolean isPassiveEffectActivated = newSelectedPiece.isPassiveEffectActivated();
                        	actionPane.setPassiveEffectBtnActivated(isPassiveEffectActivated);                     	
                        	actionPane.setPassiveEffectBtnDisable(false);
                        }
                    }
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
                Command killCommand = new KillCommand(target, selectedTile, board, playerManager, pieceManager);
        	    historyManager.storeAndExecute(killCommand);
        	    
        	    // Check if there's a winner
        	    checkForWinner(target);
        	    
        		endTurn();
            }
        }   
    }

    @Requires("tileView != null")
    public void handleMouseEnteredTile(TileView tileView) {
        Tile hoveringTile = board.getTile(tileView.getX(), tileView.getY());
        if (hoveringTile.hasPiece()) {
            AbstractPiece hoveringPiece = (AbstractPiece) hoveringTile.getGameObject();
            infoPane.setPieceInfo(hoveringPiece);
        }

        if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
            GameObject currentObject = hoveringTile.getGameObject();
            if (currentObject instanceof AbstractPiece &&
            		playerManager.isCurrentPlayerPiece((AbstractPiece) currentObject)) {
                tileView.setCursor(Cursor.HAND);
            }
        }
    }

    private void switchToAttackMode() {
        game.setCurrentGameState(GameState.READY_TO_ATTACK);
        board.clearHighlightedTiles();
        infoPane.setCurrentGameState(game.getCurrentGameState());
        actionPane.setPassiveEffectBtnDisable(true);
        actionPane.setPassiveEffectBtnFocus(false);
        
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
        setupNewTurn();
    }

    public void handleSaveButton() {
        SaveHandler saveHandler = new SaveHandler();
        saveHandler.saveGame(board, pieceManager, playerManager, game);
    }

    public void handleLoadButton() {
        LoadHandler loadHandler = new LoadHandler();
        loadHandler.loadGame(stage);
    }

    private void declareWinner() {
        mainView.setDeclarationLabel(playerManager.getCurrentPlayer().getName());
    }
    
    private void checkForWinner(AbstractPiece killTarget) {
        if (killTarget.getLevel() == Hierarchy.BABY && pieceManager.countPiecesInHierarchy(killTarget) == 0) {
            declareWinner();
        }
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
        setupNewTurn();
    }

    public void updateUI() {
        try {
            actionPane.setPassiveEffectBtnActivated(pieceManager.getRegisteredPiece().isPassiveEffectActivated());
        } catch (NullPointerException ignored) {}
        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());

        switch (game.getCurrentGameState()) {
            case READY_TO_MOVE:
                setupNewTurn();
                break;
            case KILLING:
                switchToAttackMode();
                actionPane.setPassiveEffectBtnFocus(false);
                board.highlightPossibleKillTiles(playerManager);
                actionPane.fireKillBtn();
                break;
            case PERFORMING_ACTIVE_EFFECT:
                switchToAttackMode();
                actionPane.setPassiveEffectBtnFocus(false);
                board.highlightPossibleActiveEffectTiles(playerManager);
                actionPane.fireActiveEffectBtn();
                break;
            case READY_TO_ATTACK:
                switchToAttackMode();
                actionPane.setPassiveEffectBtnFocus(false);
                break;
        }
    }

    public void loadController(Stage stage, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane, Board board,
                               PieceManager pieceManager, PlayerManager playerManager, Game game) {
        this.stage = stage;
        this.actionPane = actionPane;
        this.infoPane = infoPane;

        this.board = board;
        this.pieceManager = pieceManager;
        this.playerManager = playerManager;
        this.historyManager = new HistoryManager();
        this.game = game;
    }

    public void setupNewTurn() {
    	game.setCurrentGameState(GameState.READY_TO_MOVE);
        actionPane.hideActionIndicator();
        actionPane.setPassiveEffectBtnFocus(true);
        actionPane.disableAndDeactivatePassiveEffectBtn();
        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());
        infoPane.setCurrentGameState(game.getCurrentGameState());
        clearCache();
        
		boolean isUndoEmpty = !getPlayerManager().getCurrentPlayer().isUndoAvailabile();
		boolean hasHistory = getHistoryManager().isUndoAvailable();
		boolean isUndoAvailable = !isUndoEmpty && hasHistory;
		actionPane.setUndoBtnDisable(!isUndoAvailable);
    }
    
    @Requires("mainView != null && boardSetup != null && boardPane != null && actionPane != null && infoPane != null")
    public void setController(Stage stage, MainView mainView, BoardSetupView boardSetup, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
        int boardCol = boardSetup.getBoardSize()[0];
        int boardRow = boardSetup.getBoardSize()[1];

        this.stage = stage;
        this.mainView = mainView;
		this.actionPane = actionPane;
		this.infoPane = infoPane;

        this.board = new Board(boardCol, boardRow);
        this.pieceManager = new PieceManager(boardSetup.getPieceLineup());
        this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        this.historyManager = new HistoryManager();
        this.game = new Game();

        setTiles(boardPane, board);
        setPieces(boardPane, board, null, pieceManager, playerManager);
        setObstacles(boardPane, board,null);

        setupNewTurn();
    }

    @Requires("boardPane != null")
    public void setTiles(BoardPane boardPane, Board board) {
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

    // Nick - Should consider putting pieceManager and board in the argument as well
    @Requires("boardPane != null")
    public void setPieces(BoardPane boardPane, Board board, List<Tile> pieceTiles, PieceManager pieceManager,
                          PlayerManager playerManager) {
        if (pieceTiles == null) {
            pieceTiles = pieceManager.setPiecesOnBoard(board);
        }

        for (Tile tile : pieceTiles) {
            AbstractPiece piece = (AbstractPiece) tile.getGameObject();
            Player player = playerManager.getPlayer(piece);
            if (boardPane == null) System.out.println("boardPane null");
            if (tile == null) System.out.println("tile null");
            if (piece == null) System.out.println("piece null");
            if (player == null) System.out.println("player null");
            Avatar pieceView = boardPane.instantiatePieceView(tile.getX(), tile.getY(), piece.getName(), player.getColor());
            piece.addAvatar(pieceView);
        }
    }

    // Nick - Should consider putting pieceManager and board in the argument as well
    @Requires("boardPane != null")
    public void setObstacles(BoardPane boardPane, Board board, List<Tile> obstacleTiles) {
        if (obstacleTiles == null) {
            ObstacleManager obstacleManager = new ObstacleManager();
            obstacleTiles = obstacleManager.setObstacleOnBoard(board);
        }

        for (Tile tile : obstacleTiles) {
            Obstacle obstacle = (Obstacle) tile.getGameObject();
            ObstacleView obstacleView = boardPane.instantiateObstacleView(tile.getX(), tile.getY());
            obstacle.addAvatar(obstacleView);
        }
    }

	public PlayerManager getPlayerManager() { return playerManager; }
	public HistoryManager getHistoryManager() { return historyManager; }
}