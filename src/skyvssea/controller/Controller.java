package skyvssea.controller;

import skyvssea.model.Board;
import skyvssea.model.Piece;
import skyvssea.model.PieceManager;
import skyvssea.model.Tile;
import skyvssea.view.BoardPane;

public class Controller {

    private Board board;
    private PieceManager pieceManager;
    private BoardPane boardPane;

    public void handleTileClicked(Tile tile) {
        // Nick - TODO: Check whose the piece belongs to
        if (tile.hasPiece()) {
            Piece piece = tile.getPiece();
            int numMove = piece.getNumMove();
            int pieceX = tile.getTilePane().getX();
            int pieceY = tile.getTilePane().getY();

            Tile[][] tiles = board.getTiles();

            // Highlight possible move tile on the same row
            for (int count = 0; count < numMove; count++) {
                if ((pieceX + count) < BoardPane.NUM_SIDE_CELL) {
                    Tile rightTile = tiles[pieceX + count][pieceY];
                    if (!rightTile.hasPiece()) {
                        rightTile.setHighlighted(true);
                    }
                }
                if ((pieceX - count) >= 0) {
                    Tile leftTile = tiles[pieceX - count][pieceY];
                    if (!leftTile.hasPiece()) {
                        leftTile.setHighlighted(true);
                    }
                }
            }

            for (int count = 0; count < numMove; count++) {
                if ((pieceY + count) < BoardPane.NUM_SIDE_CELL) {
                    Tile downnTile = tiles[pieceX][pieceY + count];
                    if (!downnTile.hasPiece()) {
                        downnTile.setHighlighted(true);
                    }
                }
                if ((pieceY - count) >= 0) {
                    Tile upTile = tiles[pieceX][pieceY - count];
                    if (!upTile.hasPiece()) {
                        upTile.setHighlighted(true);
                    }
                }
            }
        }

    }

    public void setViewsAndModels(Board board, PieceManager pieceManager, BoardPane boardPane) {
        this.board = board;
        this.pieceManager = pieceManager;
        this.boardPane = boardPane;
    }
}