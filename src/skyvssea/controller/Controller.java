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
    private ActionPane actionPane;
    private BoardPane boardPane;
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
        AbstractPiece registeredPiece = pieceManager.getRegisteredPiece();
        actionPane.setSpecialEffectBtnDisable(!registeredPiece.isSpecialEffectAvailable());
        actionPane.setRegularActionPaneDisable(false);
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
        playerManager.getCurrentPlayer().reduceNumUndos();
        historyManager.undoToMyTurn();
        startNewTurn();
    }

    public void handleSaveButton() {
        SaveHandler.saveGame(board, pieceManager, playerManager, game);
    }

    public void handleLoadButton() {
        LoadHandler loadHandler = new LoadHandler();
        loadHandler.loadGame(stage);
    }

    private void changeTurn() {
        Player player = playerManager.changeTurn();
        infoPane.setPlayerInfo(player);
    }

    public void clearCache() {
        board.clearHighlightedTiles();
        board.clearRegisteredTile();
        pieceManager.clearRegisteredPiece();
    }

    private void endTurn() {
        playerManager.getCurrentPlayer().validateUndoAvailability();
        pieceManager.updatePieceStatus(historyManager);
        changeTurn();
        historyManager.startNewTurnCommand();
        startNewTurn();
    }

    public void startNewTurn() {
        game.setCurrentGameState(GameState.READY_TO_MOVE);
        actionPane.setRegularActionPaneDisable(true);
        actionPane.hideActionIndicator();
        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());
        clearCache();

        boolean isUndoEmpty = !getPlayerManager().getCurrentPlayer().isUndoAvailabile();
        boolean hasHistory = getHistoryManager().isUndoAvailable();
        boolean isUndoAvailable = !isUndoEmpty && hasHistory;
        actionPane.setUndoBtnDisable(!isUndoAvailable);
    }

    public void loadController(Stage stage, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane, Board board,
                               PieceManager pieceManager, PlayerManager playerManager, Game game) {
        this.stage = stage;
        this.boardPane = boardPane;
        this.boardPane = boardPane;
        this.actionPane = actionPane;
        this.infoPane = infoPane;

        this.board = board;
        this.pieceManager = pieceManager;
        this.playerManager = playerManager;
        this.historyManager = new HistoryManager();
        this.game = game;

        startNewTurn();
    }

    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setController(Stage stage, BoardSetupView boardSetup, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
        int boardCol = boardSetup.getBoardSize()[0];
        int boardRow = boardSetup.getBoardSize()[1];

        this.stage = stage;
		this.boardPane = boardPane;
		this.actionPane = actionPane;
		this.infoPane = infoPane;

        this.board = new Board(boardCol, boardRow);
        this.pieceManager = new PieceManager(boardSetup.getPieceLineup());
        this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        this.historyManager = new HistoryManager();
        this.game = new Game(this, actionPane);

        setTiles(boardPane, board);
        setPieces(boardPane, board, null, pieceManager, playerManager);
        setObstacles(boardPane, board,null);

        startNewTurn();
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