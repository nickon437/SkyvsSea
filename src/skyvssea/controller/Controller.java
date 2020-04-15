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
        if (tile.isHighlighted()) {
            board.clearHighlightedTiles();
            tile.setPiece(pieceManager.getCurrentPiece());
            board.getCurrentTile().removePiece();
        } else {
            board.clearHighlightedTiles();
            if (tile.hasPiece()) {
                // Nick - TODO: Check whose the piece belongs to
                Piece piece = tile.getPiece();
                pieceManager.setCurrentPiece(piece);

                int numMove = piece.getNumMove();
                int pieceX = tile.getTilePane().getX();
                int pieceY = tile.getTilePane().getY();
                Tile[][] tiles = board.getTiles();

                // Nick - TODO: Find a way to modularize the code
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
        }
        board.setCurrentTile(tile);
    }

    public void setViewsAndModels(Board board, PieceManager pieceManager, BoardPane boardPane) {
        this.board = board;
        this.pieceManager = pieceManager;
        this.boardPane = boardPane;
    }
}