package skyvssea.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import skyvssea.model.Tile;

public class BoardPane extends Pane {

    private static final int NUM_SIDE_CELL = 10;
    private Group tileGroup = new Group();
    private double tileSize;
    private Tile[][] tiles;

    public BoardPane() {
        tiles = new Tile[NUM_SIDE_CELL][NUM_SIDE_CELL];

        for (int y = 0; y < NUM_SIDE_CELL; y++) {
            for (int x = 0; x < NUM_SIDE_CELL; x++) {
                TilePane tileView = new TilePane(x, y, tileSize);
                setTile(tileView, x, y);

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
            updateTilesSize(tileSize, paneWidth, paneHeight); // TODO: Maybe let the boardPane handle tileSize calculation as well
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    public void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * NUM_SIDE_CELL)) / 2;

        for (Node node : tileGroup.getChildren()) {
            TilePane tilePane = (TilePane) node;
            tilePane.updateTileSize(tileSize, mostLeftX, mostTopY);
        }
    }


    public void setTile(TilePane tileView, int x, int y) {
        Tile tile = new Tile(tileView, (x + y) % 2 == 0);
        tiles[x][y] = tile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

}