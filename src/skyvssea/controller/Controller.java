package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.stage.Stage;
import skyvssea.BoardGame;
import skyvssea.model.*;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.view.*;

import java.util.*;

public class Controller {

//<<<<<<< HEAD
//	private Game game;
//    private Board board;
//    private PieceManager pieceManager;
//    private PlayerManager playerManager;
//    private ActionPane actionPane;
//    private InfoPane infoPane;
//
//	@Requires("tileView != null")
//    public void handleTileClicked(TileView tileView) {
//    	if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
//        	return;
//        }
//    	
//        final Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
//        AbstractPiece registeredPiece = pieceManager.getRegisteredPiece();
//
//        if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
//            Tile previousRegisteredTile = board.getRegisteredTile();
//            board.setRegisteredTile(selectedTile);
//
//            if (selectedTile.isHighlighted()) {
//            	board.clearHighlightedTiles();
//            	// Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
//                if (!selectedTile.equals(previousRegisteredTile)) {
//                    selectedTile.setGameObject(registeredPiece);
//                    previousRegisteredTile.removeGameObject();
//                }
//
//                switchToAttackMode();
//
//            } else {
//            	board.clearHighlightedTiles();
//                if (selectedTile.hasPiece()) {
//                    AbstractPiece newSelectedPiece = (AbstractPiece) selectedTile.getGameObject();
//                    if (playerManager.isCurrentPlayerPiece(newSelectedPiece)) {
//                        pieceManager.setRegisteredPiece(newSelectedPiece);
//                        board.highlightPossibleMoveTiles();
//                    }
//                }
//            }
//            
//        } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
//            if (selectedTile.isHighlighted()) {
//                registeredPiece.performSpecialEffect((AbstractPiece) selectedTile.getGameObject());
//                endTurn();
//            }
//            
//        } else if (game.getCurrentGameState() == GameState.KILLING) {
//        	if (selectedTile.isHighlighted()) {
//        		selectedTile.removeGameObject();
//        		endTurn();
//            }
//        }   
//    }
//
//	private void highlightPossibleMoveTiles(AbstractPiece piece, Tile selectedTile) {
//		int numMove = piece.getMoveRange();
//
//		board.highlightTile(selectedTile);
//
//		List<Direction> tempDirections = new ArrayList<>(Arrays.asList(piece.getMoveDirection()));
//		for (int count = 1; count <= numMove; count++) {
//			ArrayList<Direction> blockedDirections = new ArrayList<>();
//			for (Direction direction : tempDirections) {
//				Tile tile = board.getTile(selectedTile, direction, count);
//
//				if (tile != null) {
//					if (tile.hasPiece()) {
//						if (!tempDirections.contains(Direction.JUMP_OVER)) {
//							blockedDirections.add(direction);
//						}
//					} else {
//						board.highlightTile(tile);
//					}
//				}
//			}
//			tempDirections.removeAll(blockedDirections);
//		}
//	}
//
//	private void switchToAttackMode() {
//		highlightPossibleAttackTiles();
//		game.setCurrentGameState(GameState.READY_TO_ATTACK);
//
//		AbstractPiece currentPiece = pieceManager.getCurrentPiece();
//		actionPane.setSpecialEffectBtnDisable(!currentPiece.isSpecialEffectAvailable());
//	}
//
//	public void handleKillButton() {
//
//	}
//
//	public void handleSpecialEffectButton() {
//		game.setCurrentGameState(GameState.PERFORMING_SPECIAL_EFFECT);
//	}
//
//	public void highlightPossibleAttackTiles() {
//		Tile currentTile = board.getCurrentTile();
//		AbstractPiece currentPiece = pieceManager.getCurrentPiece();
//		int attackRange = currentPiece.getAttackRange();
//
//		int leftX = currentTile.getX() - attackRange;
//		leftX = leftX >= 0 ? leftX : 0;
//
//		int topY = currentTile.getY() - attackRange;
//		topY = topY >= 0 ? topY : 0;
//
//		int rightX = currentTile.getX() + attackRange;
//		rightX = rightX < board.getCol() ? rightX : board.getCol() - 1;
//
//		int bottomY = currentTile.getY() + attackRange;
//		bottomY = bottomY < board.getRow() ? bottomY : board.getRow() - 1;
//
//		// Nick - TODO: Block attack if there is piece in sight
//		for (int x = leftX; x <= rightX; x++) {
//			for (int y = topY; y <= bottomY; y++) {
//				Tile checkTile = board.getTile(x, y);
//				if (checkTile.hasPiece()) {
//					board.highlightTile(checkTile);
//				}
//			}
//		}
//	}
//
//	public void handleEndButton() {
//		endTurn();
//	}
//
//	private void changeTurn() {
//		Player player = playerManager.changeTurn();
//		infoPane.setPlayerInfo(player);
//
//		board.clearHighlightedTiles();
//		board.clearCurrentTile();
//		pieceManager.clearCurrentPiece();
//	}
//
//	private void endTurn() {
//		pieceManager.updatePieceStatus();
//		changeTurn();
//		game.setCurrentGameState(GameState.READY_TO_MOVE);
//
//		// TODO: Add save for undo here
//	}
//
//	public void handleMouseEnteredTile(TileView tileView) {
//		Tile hoveringTile = board.getTile(tileView.getX(), tileView.getY());
//		tileView.updateBaseColorAsHovered(true);
//		if (hoveringTile.hasPiece()) {
//			AbstractPiece hoveringPiece = hoveringTile.getPiece();
//			infoPane.setPieceInfo(hoveringPiece);
//		}
//
//		if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
//			AbstractPiece currentPiece = hoveringTile.getPiece();
//			if (currentPiece != null
//					&& playerManager.getCurrentPlayer().equals(playerManager.checkSide(currentPiece))) {
//				tileView.setCursor(Cursor.HAND);
//			}
//		} else if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
//			tileView.setCursor(Cursor.DEFAULT);
//		} else {
//			if (hoveringTile.isHighlighted()) {
//				tileView.setCursor(Cursor.HAND);
//			}
//		}
//	}
//
//	public void handleMouseExitedTile(TileView tileView) {
//		tileView.updateBaseColorAsHovered(false);
//	}
//
//	@Requires("boardPane != null && actionPane != null && infoPane != null")
//	public void setViewsAndModels(ChangeBoardSizePane changeBoardSizePane,BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
//		int boardCol = Integer.valueOf(changeBoardSizePane.getBoardColTextField().getText());
//		int boardRow = Integer.valueOf(changeBoardSizePane.getBoardRowTextField().getText());
//		
//		int babyNumber = Integer.valueOf(changeBoardSizePane.getBabyPieceTextField().getText());
//		int smallNumber =Integer.valueOf(changeBoardSizePane.getSmallPieceTextField().getText());
//		int midNumber =Integer.valueOf(changeBoardSizePane.getMidPieceTextField().getText());
//		int bigNumber =Integer.valueOf(changeBoardSizePane.getBigPieceTextField().getText());
//		
//		this.boardPane = boardPane;
//		this.actionPane = actionPane;
//		this.infoPane = infoPane;
//
//		this.game = new Game(actionPane);
//		this.board = new Board(boardCol,boardRow);
//		this.pieceManager = new PieceManager(createInitialLineUp(),babyNumber,smallNumber,midNumber,bigNumber);
//		this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
//
//		infoPane.setPlayerInfo(playerManager.getCurrentPlayer());
//
//		// Set up tiles on board
//		Tile[][] tiles = board.getTiles();
//		Group tileViews = this.boardPane.getTileGroup();
//		tileViews.getChildren().forEach((tileView) -> {
//			int x = ((TileView) tileView).getX();
//			int y = ((TileView) tileView).getY();
//			tiles[x][y].addObserver((Observer) tileView);
//		});
//		board.setBaseColours();
//
//		// Initialize PieceView objects and assign to the corresponding TileView objects
//		ArrayList<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);
//		startingPositions.forEach(tile -> {
//			AbstractPiece piece = tile.getPiece();
//			Player player = playerManager.checkSide(piece);
//			this.boardPane.initializePieceView(tile.getX(), tile.getY(), piece.getName(), player.getColor());
//		});
//	}
//
//=======
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
    public void setViewsAndModels(ChangeBoardSizePane changeBoardSizePane,BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	int boardCol = Integer.valueOf(changeBoardSizePane.getBoardColTextField().getText());
		int boardRow = Integer.valueOf(changeBoardSizePane.getBoardRowTextField().getText());
		
		int babyNumber = Integer.valueOf(changeBoardSizePane.getBabyPieceTextField().getText());
		int smallNumber =Integer.valueOf(changeBoardSizePane.getSmallPieceTextField().getText());
		int midNumber =Integer.valueOf(changeBoardSizePane.getMidPieceTextField().getText());
		int bigNumber =Integer.valueOf(changeBoardSizePane.getBigPieceTextField().getText());
		
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

	public void handleConfirmBtn(ChangeBoardSizePane changeBoardSizePane,Stage stage) {
		String row = changeBoardSizePane.getBoardRowTextField().getText();
		String col = changeBoardSizePane.getBabyPieceTextField().getText();
		String big = changeBoardSizePane.getBigPieceTextField().getText();
		String mid = changeBoardSizePane.getMidPieceTextField().getText();
		String small = changeBoardSizePane.getSmallPieceTextField().getText();
		String baby = changeBoardSizePane.getBabyPieceTextField().getText();
		
		int rowNumber = Integer.valueOf(row);
		int colNumber = Integer.valueOf(row);
		int bigNumber = Integer.valueOf(big);
		int midNumber = Integer.valueOf(mid);
		int smallNumber = Integer.valueOf(small);
		int babyNumber = Integer.valueOf(baby);
		

		if (row.isEmpty() && col.isEmpty() && big.isEmpty() 
				&& mid.isEmpty() && small.isEmpty() && baby.isEmpty()) {
			changeBoardSizePane.getTips().setVisible(true);
			return;
		}
		else if ((colNumber < 4) || (rowNumber < 4)|| (bigNumber < 1)
				|| (midNumber < 1) || (smallNumber < 1) || (babyNumber < 1)) {
			changeBoardSizePane.getTips().setVisible(true);
			return;
		}
		else if(rowNumber < (bigNumber + midNumber + smallNumber + babyNumber)) {
			changeBoardSizePane.getTips().setVisible(true);
			return;
		}
		else {
			changeBoardSizePane.getTips().setVisible(false);
			BoardGame boardGame = new BoardGame(changeBoardSizePane);
			boardGame.stage.show();
			stage.close();
		}
		
	}

	public void handleClearBtn(ChangeBoardSizePane changeBoardSizePane) {
		changeBoardSizePane.getTips().setVisible(false);
		changeBoardSizePane.getBoardColTextField().setText("");
		changeBoardSizePane.getBoardRowTextField().setText("");
		changeBoardSizePane.getBigPieceTextField().setText("");
		changeBoardSizePane.getMidPieceTextField().setText("");
		changeBoardSizePane.getSmallPieceTextField().setText("");
		changeBoardSizePane.getBabyPieceTextField().setText("");
	}
}