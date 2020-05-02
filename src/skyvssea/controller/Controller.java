package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.model.piece.Piece;
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
    public void handleTileClicked(TilePane tileView) {
        System.out.println("GameState at tileClicked: " + game.getCurrentGameState());
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
                        Piece currentPiece = pieceManager.getCurrentPiece();
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
                        Piece piece = selectedTile.getPiece();
                        if (playerManager.checkSide(piece).equals(playerManager.getCurrentPlayer())) {
                            pieceManager.setCurrentPiece(piece);
                            highlightPossibleMoveTiles(piece, selectedTile);
                        }
                    }
                }
            } else if (game.getCurrentGameState() == GameState.PERFORMING_SPECIAL_EFFECT) {
                if (selectedTile.isHighlighted()) {
                    Piece currentPiece = pieceManager.getCurrentPiece();
                    currentPiece.performSpecialEffect(selectedTile.getPiece());
                    endTurn();
                }
            }
        }
    }

    private void highlightPossibleMoveTiles(Piece piece, Tile selectedTile) {
        int numMove = piece.getNumMove();

        board.highlightTile(selectedTile);

        List<Direction> tempDirections = new ArrayList<>(Arrays.asList(piece.getMoveDirection()));
        for (int count = 1; count <= numMove; count++) {
            ArrayList<Direction> blockedDirections = new ArrayList<>();
            for (Direction direction : tempDirections) {
                Tile tile = board.getTile(selectedTile, direction, count);

                if (tile != null) {
                    if (tile.hasPiece()) {
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
        highlightPossibleAttackTiles();
        game.setCurrentGameState(GameState.READY_TO_ATTACK);
        System.out.println("Update GameState to " + game.getCurrentGameState());

        Piece currentPiece = pieceManager.getCurrentPiece();
        actionPane.setSpecialEffectBtnDisable(!currentPiece.isSpecialEffectAvailable());
    }

    public void handleKillButton() {

    }

    public void handleSpecialEffectButton() {
        game.setCurrentGameState(GameState.PERFORMING_SPECIAL_EFFECT);
        System.out.println("Update GameState to " + game.getCurrentGameState());
    }

    public void highlightPossibleAttackTiles() {
        Tile currentTile = board.getCurrentTile();
        Piece currentPiece = pieceManager.getCurrentPiece();
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
                System.out.println("Current checked tile | x: " + x + " | y: " + y);
                if (checkTile.hasPiece()) {
                    board.highlightTile(checkTile);
                }
            }
        }
    }

    public void handleEndButton() {
        endTurn();
    }

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
        System.out.println("Update GameState to " + game.getCurrentGameState());

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
    		int x = ((TilePane) tileView).getX();
    		int y = ((TilePane) tileView).getY();
    		tiles[x][y].addObserver((Observer) tileView);
    	});
    	board.setBaseColours();

        //Initialize PieceView objects and assign to the corresponding TilePane objects
        ArrayList<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);
        startingPositions.forEach(tile -> {
            Piece piece = tile.getPiece();
            Player player = playerManager.checkSide(piece);
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