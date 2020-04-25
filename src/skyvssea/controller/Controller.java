package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Group;
import skyvssea.model.*;
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
    	final Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
    	Tile previousSelectedTile = board.getCurrentTile();
    	board.setCurrentTile(selectedTile);

        if (selectedTile.isHighlighted()) {
            board.clearHighlightedTiles();

            // selecting self-occupied tile is a valid move 
            if (!selectedTile.equals(previousSelectedTile)) {
                // Configure model objects
                Piece currentPiece = pieceManager.getCurrentPiece();
                selectedTile.setPiece(currentPiece);

                // Configure view objects
                PieceView pieceView = boardPane.getTileView(previousSelectedTile.getX(), previousSelectedTile.getY()).getPieceView();
                boardPane.getTileView(tileView.getX(), tileView.getY()).setPieceView(pieceView);

                previousSelectedTile.removePiece();
            }

            // TODO: Remove this later when implementing attacking
            changeTurn();
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
    }

    private void highlightPossibleMoveTiles(Piece piece, Tile selectedTile) {
        int numMove = piece.getNumMove();

        board.highlightCurrentTile();

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
                        board.highlightUnoccupiedTile(tile);
                    }
                }
            }
            tempDirections.removeAll(blockedDirections);
        }
    }

    public void handleSkipButton() {
        changeTurn();
    }

    private void changeTurn() {
        Player player = playerManager.changeTurn();
        infoPane.setPlayerInfo(player);

        board.clearHighlightedTiles();
        board.clearCurrentTile();
        pieceManager.clearCurrentPiece();
    }

    @Requires("boardPane != null")
    public void setViewsAndModels(BoardPane boardPane, ActionPane actionPane, InfoPane infoPane) {
    	this.boardPane = boardPane;
    	this.actionPane = actionPane;
    	this.infoPane = infoPane;

    	this.game = new Game();
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