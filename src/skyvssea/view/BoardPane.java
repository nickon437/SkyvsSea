package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import skyvssea.controller.Controller;

import java.util.ArrayList;

public class BoardPane extends Pane {

    public static final int NUM_SIDE_CELL = 10;
    private Group tileGroup = new Group();
    private ArrayList<PieceView> pieceViewGroup = new ArrayList<>();
    private double tileSize;

    @Requires("controller != null")
    public BoardPane(Controller controller) {
        for (int y = 0; y < NUM_SIDE_CELL; y++) {
            for (int x = 0; x < NUM_SIDE_CELL; x++) {
                TileView tileView = new TileView(x, y, tileSize, controller);
                tileGroup.getChildren().add(tileView);
            }
        }
        this.getChildren().add(getTileGroup());

        setDynamicTileSize();
    }

    private void setDynamicTileSize() {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = getWidth();
            double paneHeight = getHeight();
            double boardSideSize = paneWidth < paneHeight ? paneWidth : paneHeight;
            tileSize = boardSideSize / NUM_SIDE_CELL;
            updateTilesSize(tileSize, paneWidth, paneHeight);
            updatePiecesSize(tileSize);
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    @Requires("tileSize >= 0 && width >= tileSize && height >= tileSize")
    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * NUM_SIDE_CELL)) / 2;

        for (Node node : getTileGroup().getChildren()) {
            TileView tileView = (TileView) node;
            tileView.updateTileSize(tileSize, mostLeftX, mostTopY);
        }
    }

    @Requires("tileSize >= 0")
    private void updatePiecesSize(double tileSize) {
        for (PieceView pieceView : pieceViewGroup) {
            pieceView.updatePieceViewSize(tileSize);
        }
    }

    public void setPieceGroup(ArrayList<PieceView> pieceViews) {
        this.pieceViewGroup = pieceViews;
    }

    @Requires("pieceView != null")
    public void addPieceView(PieceView pieceView) {
    	pieceViewGroup.add(pieceView);
    }

	public Group getTileGroup() {
		return tileGroup;
	}

	@Requires("x >= 0 && y >= 0 && x < NUM_SIDE_CELL && y < NUM_SIDE_CELL")
	public TileView getTileView(int x, int y) {
		for (Node node : tileGroup.getChildren()) {
			if (((TileView) node).getX() == x && ((TileView) node).getY() == y) {
				return (TileView) node;
			}
		}
		return null;
	}

	public void initializePieceView(int x, int y, String name, Color color) {
		PieceView pieceView = new PieceView(name, color);
        getTileView(x, y).setPieceView(pieceView);
        addPieceView(pieceView);
	}


}