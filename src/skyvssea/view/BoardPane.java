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

	private int boardCol;
	private int boardRow;
    
    private Group tileGroup = new Group();
    private ArrayList<PieceView> pieceViewGroup = new ArrayList<>();
    private double tileSize;

    @Requires("controller != null")
    public BoardPane(Controller controller,ChangeBoardSizePane changeBoardSizePane) {
    	this.boardCol = Integer.valueOf(changeBoardSizePane.getBoardColTextField().getText());
    	this.boardRow = Integer.valueOf(changeBoardSizePane.getBoardRowTextField().getText());
    	
        for (int y = 0; y < this.boardRow; y++) {
            for (int x = 0; x < this.boardCol; x++) {
                TileView tileView = new TileView( x, y, tileSize, controller);
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
            tileSize = boardSideSize / ((boardCol + boardRow) / 1.75);
            updateTilesSize(tileSize, paneWidth, paneHeight);
            updatePiecesSize(tileSize);
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    @Requires("tileSize >= 0 && width >= tileSize && height >= tileSize")
    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * boardCol)) / 2;
        double mostTopY = (height - (tileSize * boardRow)) / 2;

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

	@Requires("x >= 0 && y >= 0 && x < col && y < row")
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