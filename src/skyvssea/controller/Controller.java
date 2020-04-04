package skyvssea.controller;

import skyvssea.model.Tile;
import skyvssea.view.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Controller {

    public static final int NUM_SIDE_CELL = 10;
    private Tile[][] board;

    private double tileSize;

    public void buildView(Stage stage) {
        stage.setTitle("Sky vs. Sea");


        BoardPane boardPane = buildBoard(tileSize);
        ActionPane actionPane = new ActionPane();
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);
        //boardPane.setStyle("-fx-background-color: #7d5468;"); // TODO: Remove this later

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        setDynamicTileSize(boardPane);
        stage.show();
    }

    private void setDynamicTileSize(BoardPane boardPane) {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = boardPane.getWidth();
            double paneHeight = boardPane.getHeight();
            double boardSideSize = paneWidth < paneHeight ? paneWidth : paneHeight;
            tileSize = boardSideSize / NUM_SIDE_CELL;
            boardPane.updateTilesSize(tileSize, paneWidth, paneHeight); // TODO: Maybe let the boardPane handle tileSize calculation as well
        };

        boardPane.widthProperty().addListener(paneSizeListener);
        boardPane.heightProperty().addListener(paneSizeListener);
    }

    private BoardPane buildBoard(double tileSize) {
        board = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];
        BoardPane boardPane = new BoardPane(tileSize, this);
        return boardPane;
    }

    public void setTile(Rectangle tileView, int x, int y) {
        Tile tile = new Tile(tileView, (x + y) % 2 == 0);
        board[x][y] = tile;
    }
}
