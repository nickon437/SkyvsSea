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
    private ObstacleManager obstacleManager;
    private BoardPane boardPane;
    private ActionPane actionPane;
    private InfoPane infoPane;

    @Requires("tileView != null")
    public void handleTileClicked(TileView tileView) {
        if (game.getCurrentGameState() != GameState.READY_TO_ATTACK) {
            final Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
            Tile previousSelectedTile = board.getCurrentTile();
            board.setCurrentTile(selectedTile);

            if (game.getCurrentGameState() == GameState.READY_TO_MOVE) {
                if (selectedTile.isHighlighted()) {
                    board.clearHighlightedTiles();

                    // Change piece location to a new tile. If selected tile is in the same position, remain everything the same.
                    if (!selectedTile.equals(previousSelectedTile)) {
                        // Configure model objects
                        AbstractPiece currentPiece = pieceManager.getCurrentPiece();
                        selectedTile.setGameObject(currentPiece);

//                        // Configure view objects
//                        PieceView pieceView = boardPane.getTileView(previousSelectedTile.getX(), previousSelectedTile.getY()).getPieceView();
//                        tileView.setPieceView(pieceView);

                        previousSelectedTile.removeGameObject();
                    }

                    switchToAttackMode();
                } else {
                    board.clearHighlightedTiles();
                    if (selectedTile.hasPiece()) {
                        AbstractPiece piece = (AbstractPiece) selectedTile.getGameObject();
                        if (playerManager.checkSide(piece).equals(playerManager.getCurrentPlayer())) {
                            pieceManager.setCurrentPiece(piece);
                            highlightPossibleMoveTiles(piece, selectedTile);
                        }
                    }
                }
            } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
                if (selectedTile.isHighlighted()) {
                    AbstractPiece currentPiece = pieceManager.getCurrentPiece();
                    currentPiece.performSpecialEffect((AbstractPiece) selectedTile.getGameObject());
                    endTurn();
                }
            }
        }
    }

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
            Player currentPlayer = playerManager.getCurrentPlayer();
            if (currentObject instanceof AbstractPiece &&
                    currentPlayer.equals(playerManager.checkSide((AbstractPiece) currentObject))) {
                tileView.setCursor(Cursor.HAND);
            }
        }
    }
    public void handleMouseExitedTile(TileView tileView) {
        tileView.updateBaseColorAsHovered(false);
    }

    private void highlightPossibleMoveTiles(AbstractPiece piece, Tile selectedTile) {
        int numMove = piece.getMoveRange();

        board.highlightTile(selectedTile);

        List<Direction> tempDirections = new ArrayList<>(Arrays.asList(piece.getMoveDirection()));
        for (int count = 1; count <= numMove; count++) {
            ArrayList<Direction> blockedDirections = new ArrayList<>();
            for (Direction direction : tempDirections) {
                Tile tile = board.getTile(selectedTile, direction, count);

                if (tile != null) {
                    if (tile.hasGameObject()) {
                        if (!tempDirections.contains(Direction.JUMP_OVER)) {
                            blockedDirections.add(direction);
                        }
                    } else {
                        board.highlightTile(tile);
                    }
                }
            }
            tempDirections.removeAll(blockedDirections);
        }
    }

    private void switchToAttackMode() {
        game.setCurrentGameState(GameState.READY_TO_ATTACK);
        AbstractPiece currentPiece = pieceManager.getCurrentPiece();
        actionPane.setSpecialEffectBtnDisable(!currentPiece.isSpecialEffectAvailable());
    }

    public void handleKillButton() { game.setCurrentGameState(GameState.KILLING); }
    public void handleMouseEnteredKillBtn() { highlightPossibleAttackTiles(true); }
    public void handleMouseExitedKillBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.KILLING) {
            highlightPossibleAttackTiles(true);
        }
    }

    public void handleSpecialEffectButton() { game.setCurrentGameState(GameState.PERFORMING_SPECIAL_EFFECT); }
    public void handleMouseEnteredSpecialEffectBtn() { highlightPossibleAttackTiles(false); }
    public void handleMouseExitedSpecialEffectBtn() {
        if (game.getCurrentGameState() == GameState.READY_TO_ATTACK) {
            board.clearHighlightedTiles();
        } else if (game.getCurrentGameState() == GameState.KILLING) {
            highlightPossibleAttackTiles(false);
        }
    }

    // TODO: Add highlight tiles for killable targets logic here
    public void highlightPossibleAttackTiles(boolean isKillOption) {
        Tile currentTile = board.getCurrentTile();
        AbstractPiece currentPiece = pieceManager.getCurrentPiece();
        int attackRange = currentPiece.getAttackRange();

        int leftX = currentTile.getX() - attackRange;
        leftX = leftX >= 0 ? leftX : 0;

        int topY = currentTile.getY() - attackRange;
        topY = topY >= 0 ? topY : 0;

        int rightX = currentTile.getX() + attackRange;
        rightX = rightX < Board.NUM_SIDE_CELL ? rightX : Board.NUM_SIDE_CELL - 1;

        int bottomY = currentTile.getY() + attackRange;
        bottomY = bottomY < Board.NUM_SIDE_CELL ? bottomY : Board.NUM_SIDE_CELL - 1;

        // Nick - TODO: Block attack if there is piece in sight
        for (int x = leftX; x <= rightX; x++) {
            for (int y = topY; y <= bottomY; y++) {
                Tile checkTile = board.getTile(x, y);
                if (checkTile.hasPiece()) {
                    if (!isKillOption) {
                        board.highlightTile(checkTile);
                    }
                }
            }
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
    	this.board = new Board(this);
		this.pieceManager = new PieceManager(createInitialLineUp());
        this.playerManager = new PlayerManager(pieceManager.getEaglePieces(), pieceManager.getSharkPieces());
        this.obstacleManager = new ObstacleManager();

        infoPane.setPlayerInfo(playerManager.getCurrentPlayer());

        // Set up tiles on board
    	Tile[][] tiles = board.getTiles();
    	Group tileViews = this.boardPane.getTileGroup();
    	tileViews.getChildren().forEach((tileView) -> {
    		int x = ((TileView) tileView).getX();
    		int y = ((TileView) tileView).getY();
    		tiles[x][y].addObserver((Observer) tileView);
    		tiles[x][y].addAvatar((Avatar) tileView);
    	});
    	board.setBaseColours();

        //Initialize PieceView objects and assign to the corresponding TileView objects
        ArrayList<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);
        startingPositions.forEach(tile -> {
            AbstractPiece piece = (AbstractPiece) tile.getGameObject();
            Player player = playerManager.checkSide(piece);
            PieceView pieceView = this.boardPane.instantiatePieceView(tile.getX(), tile.getY(), piece.getName(), player.getColor());
            piece.addAvatar(pieceView);
        });

        ArrayList<Tile> obstacleTiles = obstacleManager.setObstacleOnBoard(board);
        for (Tile tile : obstacleTiles) {
            Obstacle obstacle = (Obstacle) tile.getGameObject();
            ObstacleView obstacleView = this.boardPane.instantiateObstacleView(tile.getX(), tile.getY());
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