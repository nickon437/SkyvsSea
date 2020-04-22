package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.view.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;

public class Controller {

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
        int pieceX = selectedTile.getX();
        int pieceY = selectedTile.getY();
        Tile[][] tiles = board.getTiles();

        // Nick - TODO: Find a way to modularize the code
        // Jiang - TODO: and customize available moves based on Piece type
        // Highlight possible move tiles
        for (int count = 1; count <= numMove; count++) {
            if ((pieceX + count) < BoardPane.NUM_SIDE_CELL) {
                Tile rightTile = tiles[pieceX + count][pieceY];
                board.highlightUnoccupiedTiles(rightTile);
            }

            if ((pieceX - count) >= 0) {
                Tile leftTile = tiles[pieceX - count][pieceY];
                board.highlightUnoccupiedTiles(leftTile);
            }

            if ((pieceY + count) < BoardPane.NUM_SIDE_CELL) {
                Tile downTile = tiles[pieceX][pieceY + count];
                board.highlightUnoccupiedTiles(downTile);
            }

            if ((pieceY - count) >= 0) {
                Tile upTile = tiles[pieceX][pieceY - count];
                board.highlightUnoccupiedTiles(upTile);
            }
        }
    }

    public void handleSkipButton() {
        changeTurn();
    }

    private void changeTurn() {
        Player player = playerManager.changeTurn();
        infoPane.setPlayerName(player.getName());
    }

    @Requires("boardPane != null")
    public void setViewsAndModels(BoardPane boardPane, ActionPane actionPane, InfoPane infoPane, Map<Hierarchy, Integer> lineup) {
    	this.boardPane = boardPane;
    	this.actionPane = actionPane;
    	this.infoPane = infoPane;

    	this.board = new Board();
        this.pieceManager = new PieceManager(lineup);
        this.playerManager = new PlayerManager();

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
            
//            PieceView pieceView = new PieceView(piece.getName());
//            this.boardPane.getTileView(tile.getX(), tile.getY()).setPieceView(pieceView);
//            this.boardPane.addPieceView(pieceView);
//
//            Player player = playerManager.checkSide(piece);
//            pieceView.paint(player.getColor());
        });
    }
}