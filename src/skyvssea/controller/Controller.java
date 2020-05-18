package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import skyvssea.BoardGame;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
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
            	board.clearHighlightedTiles();
            	// Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
                if (!selectedTile.equals(previousRegisteredTile)) {
                    selectedTile.setGameObject(registeredPiece);
                    previousRegisteredTile.removeGameObject();
                }

                switchToAttackMode();

            } else {
            	board.clearHighlightedTiles();
                if (selectedTile.hasPiece()) {
                    AbstractPiece newSelectedPiece = (AbstractPiece) selectedTile.getGameObject();
                    if (playerManager.isCurrentPlayerPiece(newSelectedPiece)) {
                    	pieceManager.setCurrentPiece(newSelectedPiece);
                        board.highlightPossibleMoveTiles();
                    }
                }
            }
            
        } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
            if (selectedTile.isHighlighted()) {
                registeredPiece.performSpecialEffect((AbstractPiece) selectedTile.getGameObject());
                endTurn();
            }
            
        } else if (game.getCurrentGameState() == GameState.KILLING) {
        	if (selectedTile.isHighlighted()) {
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

        // TODO: Add save for undo here
    }

    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setViewsAndModels(BoardSetupView boardSetup, BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	int boardCol = Integer.valueOf(boardSetup.getBoardColTextField().getText());
		int boardRow = Integer.valueOf(boardSetup.getBoardRowTextField().getText());
		
		int babyNumber = Integer.valueOf(boardSetup.getBabyPieceTextField().getText());
		int smallNumber =Integer.valueOf(boardSetup.getSmallPieceTextField().getText());
		int midNumber =Integer.valueOf(boardSetup.getMidPieceTextField().getText());
		int bigNumber =Integer.valueOf(boardSetup.getBigPieceTextField().getText());
		
		this.boardPane = boardPane;
		this.actionPane = actionPane;
		this.infoPane = infoPane;

		this.game = new Game(actionPane);
		this.board = new Board(boardCol,boardRow);
		this.pieceManager = new PieceManager(createInitialLineUp(),babyNumber,smallNumber,midNumber,bigNumber);
		 this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
		 
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

	public void handleConfirmBtn(BoardSetupView boardSetup, Stage stage) {
		String row = boardSetup.getBoardRowTextField().getText();
		String col = boardSetup.getBabyPieceTextField().getText();
		String big = boardSetup.getBigPieceTextField().getText();
		String mid = boardSetup.getMidPieceTextField().getText();
		String small = boardSetup.getSmallPieceTextField().getText();
		String baby = boardSetup.getBabyPieceTextField().getText();

		int rowNumber = Integer.valueOf(row);
		int colNumber = Integer.valueOf(row);
		int bigNumber = Integer.valueOf(big);
		int midNumber = Integer.valueOf(mid);
		int smallNumber = Integer.valueOf(small);
		int babyNumber = Integer.valueOf(baby);


		if (row.isEmpty() && col.isEmpty() && big.isEmpty()
				&& mid.isEmpty() && small.isEmpty() && baby.isEmpty()) {
			boardSetup.getTips().setVisible(true);
			return;
		}
		else if ((colNumber < 4) || (rowNumber < 4)|| (bigNumber < 1)
				|| (midNumber < 1) || (smallNumber < 1) || (babyNumber < 1)) {
			boardSetup.getTips().setVisible(true);
			return;
		}
		else if(rowNumber < (bigNumber + midNumber + smallNumber + babyNumber)) {
			boardSetup.getTips().setVisible(true);
			return;
		}
		else {
			boardSetup.getTips().setVisible(false);
			BoardGame boardGame = new BoardGame(boardSetup);
			boardGame.stage.show();
			stage.close();
		}

	}

	public void handleClearBtn(BoardSetupView boardSetup) {
		boardSetup.getTips().setVisible(false);
		boardSetup.getBoardColTextField().setText("");
		boardSetup.getBoardRowTextField().setText("");
		boardSetup.getBigPieceTextField().setText("");
		boardSetup.getMidPieceTextField().setText("");
		boardSetup.getSmallPieceTextField().setText("");
		boardSetup.getBabyPieceTextField().setText("");
	}
}