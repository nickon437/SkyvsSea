package skyvssea.controller;

import java.util.Observer;

import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.view.ActionPane;
import skyvssea.view.BoardPane;
import skyvssea.view.TilePane;

public class Controller {

    private Board board;
    private PieceManager pieceManager;
    private BoardPane boardPane;

    // Phil: construct the controller
    private ActionPane actionPane;
    TurnManager turnManager = new TurnManager();
    Player[] players;
    Player currentPlayer;

    public Controller() {
        turnManager.setUpPlayers();
        players = turnManager.players;
        currentPlayer = players[0];

    }

    public void handleTileClicked(int x, int y) {
    	
    	// TODO: use the tileView position to find the Tile object instead
    	
    	Tile tile = board.getTile(x, y);
        if (tile.isHighlighted()) {
            board.clearHighlightedTiles();
            Piece currentPiece = pieceManager.getCurrentPiece();
            tile.setPiece(currentPiece, pieceManager.getPieceView(currentPiece));

            board.getCurrentTile().removePiece();
        } else {
            board.clearHighlightedTiles();
            if (tile.hasPiece()) {
                // Nick - TODO: Check whose the piece belongs to
                Piece piece = tile.getPiece();
                pieceManager.setCurrentPiece(piece);

                int numMove = piece.getNumMove();
                int pieceX = tile.getX();
                int pieceY = tile.getY();
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
        }
        board.setCurrentTile(tile);
    }

    public void setViewsAndModels(BoardPane boardPane) {
    	this.boardPane = boardPane;
    	this.board = new Board();
    	
    	Tile[][] tiles = board.getTiles();
    	Group tileViews = this.boardPane.getTileGroup();
    	tileViews.getChildren().forEach((tileView) -> {
    		int x = ((TilePane) tileView).getX();
    		int y = ((TilePane) tileView).getY();
    		tiles[x][y].addObserver((Observer) tileView);
    	});
    	
    	board.setBaseColours();
    	
    	pieceManager = new PieceManager();
    	pieceManager.setPiecesOnBoard(board);
    	boardPane.setPieceGroup(pieceManager.getAllPieceViews());
    }


    //  Phil TODO: click skip button, the player turn will be changed
    public void setAction(ActionPane actionPane) {
        this.actionPane = actionPane;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
        this.players = turnManager.players;
    }


    public void addActionHandler() {
        currentPlayer.setStatus(true);
        showCurrentPlayerName();

        actionPane.getSkipBtn().setOnAction(actionEvent -> {
            if(currentPlayer.getName() == players[0].getName()){
                currentPlayer = turnManager.nextTurn();
                showCurrentPlayerName();
            }

            else if (currentPlayer.getName() == players[1].getName()){
                currentPlayer = turnManager.nextTurn();
                showCurrentPlayerName();
            }
        });

    }

    public void showCurrentPlayerName(){
        actionPane.getPlayerText().setText(currentPlayer.getName());
        actionPane.getPlayerText().setFill(currentPlayer.getColour());

    }
}