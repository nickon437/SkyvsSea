package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.*;

public class Controller {

    private Game game;
    private Board board;
    private PieceManager pieceManager;
    private PlayerManager playerManager;
    private BoardPane boardPane;
    private ActionPane actionPane;
    private InfoPane infoPane;

    @Requires("tileView != null")
    public void handleTileClicked(TileView tileView) {
    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
        	return;
        }
    	
        final Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
        Tile previousSelectedTile = board.getCurrentTile();
        board.setCurrentTile(selectedTile);

        AbstractPiece currentPiece = pieceManager.getCurrentPiece();
        if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
            if (selectedTile.isHighlighted()) {
            	board.clearHighlightedTiles();
                // Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
                if (!selectedTile.equals(previousSelectedTile)) {
                    // Configure model objects
                    selectedTile.setPiece(currentPiece);

                    // Configure view objects
                    PieceView pieceView = boardPane.getTileView(previousSelectedTile.getX(), previousSelectedTile.getY()).getPieceView();
                    tileView.setPieceView(pieceView);

                    previousSelectedTile.removePiece();
                }

                switchToAttackMode();
            } else {
            	board.clearHighlightedTiles();
                if (selectedTile.hasPiece()) {
                    AbstractPiece newSelectedPiece = selectedTile.getPiece();
                    if (playerManager.returnSide(newSelectedPiece).equals(playerManager.getCurrentPlayer())) {
                        pieceManager.setCurrentPiece(newSelectedPiece);
                        board.highlightPossibleMoveTiles(selectedTile);
                    }
                }
            }
        } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
            if (selectedTile.isHighlighted()) {
                currentPiece.performSpecialEffect(selectedTile.getPiece());
                endTurn();
            }
        } else if (game.getCurrentGameState() == GameState.KILLING) {
        	if (selectedTile.isHighlighted()) {
        		//TODO: Jiang: currently does not alter pieceManager to kill, if necessary i will implement it later 
        		selectedTile.removePiece();
                tileView.removePieceView();
        		endTurn();
            }
        }   
    }

    public void handleMouseEnteredTile(TileView tileView) {
        Tile hoveringTile = board.getTile(tileView.getX(), tileView.getY());
        tileView.updateBaseColorAsHovered(true);
        if (hoveringTile.hasPiece()) {
            AbstractPiece hoveringPiece = hoveringTile.getPiece();
            infoPane.setPieceInfo(hoveringPiece);
        }

        if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
            AbstractPiece currentPiece = hoveringTile.getPiece();
            if (currentPiece != null && playerManager.isCurrentPlayerPiece(currentPiece)) {
                tileView.setCursor(Cursor.HAND);
            }
        } else if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            tileView.setCursor(Cursor.DEFAULT);
        } else {
            if (hoveringTile.isHighlighted()) { tileView.setCursor(Cursor.HAND); }
        }
    }
    public void handleMouseExitedTile(TileView tileView) {
        tileView.updateBaseColorAsHovered(false);
    }

    private void switchToAttackMode() {
        game.setCurrentGameState(GameState.READY_TO_ATTACK);
        AbstractPiece currentPiece = pieceManager.getCurrentPiece();
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
        actionPane.hideActionIndicator();
        pieceManager.updatePieceStatus();
        changeTurn();
        game.setCurrentGameState(GameState.READY_TO_MOVE);

        // TODO: Add save for undo here
    }

    @Requires("boardPane != null && actionPane != null && infoPane != null")
    public void setViewsAndModels(BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	this.boardPane = boardPane;
    	this.actionPane = actionPane;
    	this.infoPane = infoPane;

    	this.game = new Game(actionPane);
    	this.board = new Board();
		this.pieceManager = new PieceManager(createInitialLineUp());
        this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());

        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());

        // Set up tiles on board
    	Tile[][] tiles = board.getTiles();
    	Group tileViews = this.boardPane.getTileGroup();
    	tileViews.getChildren().forEach((tileView) -> {
    		int x = ((TileView) tileView).getX();
    		int y = ((TileView) tileView).getY();
    		tiles[x][y].addObserver((Observer) tileView);
    	});
    	board.setBaseColours();

        //Initialize PieceView objects and assign to the corresponding TileView objects
        ArrayList<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);
        startingPositions.forEach(tile -> {
            AbstractPiece piece = tile.getPiece();
            Player player = playerManager.returnSide(piece);
            this.boardPane.initializePieceView(tile.getX(), tile.getY(), piece.getName(), player.getColor());
        });
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