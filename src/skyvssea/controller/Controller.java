package skyvssea.controller;

import com.google.java.contract.Requires;
import javafx.scene.Group;
import skyvssea.model.*;
import skyvssea.view.ActionPane;
import skyvssea.view.BoardPane;
import skyvssea.view.PieceView;
import skyvssea.view.TilePane;

import java.util.ArrayList;
import java.util.Observer;

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
                // Nick - TODO: Check whose the piece belongs to
                Piece piece = selectedTile.getPiece();
                pieceManager.setCurrentPiece(piece);

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
        }
        board.setCurrentTile(selectedTile);
    }

    @Requires("boardPane != null")
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
        ArrayList<Tile> startingPositions = pieceManager.setPiecesOnBoard(board);

        //Initialize PieceView objects and assign to the corresponding TilePane objects
        startingPositions.forEach(tile -> {
            Piece piece = tile.getPiece();
            PieceView pieceView = new PieceView(piece.getName());
            this.boardPane.getTileView(tile.getX(), tile.getY()).setPieceView(pieceView);
            this.boardPane.addPieceView(pieceView);
        });
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