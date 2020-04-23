package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.view.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

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

    	Tile selectedTile = board.getTile(tileView.getX(), tileView.getY());
        if (selectedTile.isHighlighted()) {
            board.clearHighlightedTiles();

            // Model
            Piece currentPiece = pieceManager.getCurrentPiece();
            selectedTile.setPiece(currentPiece);

            // View
            Tile prevTile = board.getCurrentTile();
            PieceView pieceView = boardPane.getTileView(prevTile.getX(), prevTile.getY()).getPieceView();
            boardPane.getTileView(tileView.getX(), tileView.getY()).setPieceView(pieceView);

            board.getCurrentTile().removePiece();

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
        board.setCurrentTile(selectedTile);
    }

    private void highlightPossibleMoveTiles(Piece piece, Tile selectedTile) {
        int numMove = piece.getNumMove();

        List<Direction> tempDirections = new ArrayList<>(Arrays.asList(piece.getMoveDirection()));
        for (int count = 1; count <= numMove; count++) {
            ArrayList<Direction> blockedDirections = new ArrayList<>();
            for (Direction direction : tempDirections) {
                Tile tile = board.getTile(selectedTile, direction, count);

                if (tile != null) {
                    if (tile.hasPiece()) {
                        if (!tempDirections.contains(Direction.JUMPOVER)) {
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
        infoPane.setPlayerName(player.getName());

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

        infoPane.setPlayerName(playerManager.getCurrentPlayer().getName());

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