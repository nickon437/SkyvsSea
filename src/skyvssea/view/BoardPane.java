package skyvssea.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import skyvssea.controller.Controller;
import skyvssea.model.Tile;

import java.util.ArrayList;

public class BoardPane extends Pane {

    public static final int NUM_SIDE_CELL = 10;
    private Group tileGroup = new Group();
    private ArrayList<PieceView> pieceViewGroup;
    private double tileSize;
    private Tile[][] tiles;

    public BoardPane(Controller controller) {
        tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];

        for (int y = 0; y < NUM_SIDE_CELL; y++) {
            for (int x = 0; x < NUM_SIDE_CELL; x++) {
                TilePane tileView = new TilePane(x, y, tileSize);
                Tile tile = setTile(tileView, x, y);

                tileView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    controller.handleTileClicked(tile);
                });

                tileGroup.getChildren().add(tileView);
            }
        }

        this.getChildren().add(tileGroup);

        setDynamicTileSize();
    }

    private void setDynamicTileSize() {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = getWidth();
            double paneHeight = getHeight();
            double boardSideSize = paneWidth < paneHeight ? paneWidth : paneHeight;
            tileSize = boardSideSize / NUM_SIDE_CELL;
            updateTilesSize(tileSize, paneWidth, paneHeight);
            updatePieceSize(tileSize);
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * NUM_SIDE_CELL)) / 2;

        for (Node node : tileGroup.getChildren()) {
            TilePane tilePane = (TilePane) node;
            tilePane.updateTileSize(tileSize, mostLeftX, mostTopY);
        }
    }

    private void updatePieceSize(double tileSize) {
        for (PieceView pieceView : pieceViewGroup) {
            pieceView.updatePieceViewSize(tileSize);
        }
    }

    public Tile setTile(TilePane tileView, int x, int y) {
        Tile tile = new Tile(tileView, (x + y) % 2 == 0);
        tiles[x][y] = tile;
        return tile;
    }

    // Nick - TODO: Need to modify this as this getTiles() method is only used by Board once when setting up and shouldn't be accessible in any other circumstances
    public Tile[][] getTiles() {
        return tiles;
    }

    public void setPieceGroup(ArrayList<PieceView> pieceViews) {
        this.pieceViewGroup = pieceViews;
    }

}