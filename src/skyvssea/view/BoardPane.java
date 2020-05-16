package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import skyvssea.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class BoardPane extends Pane {
	private int col;
	private int row;
	
    private List<TileView> tileViewGroup = new ArrayList<>();
    private List<PieceView> pieceViewGroup = new ArrayList<>();
    private List<ObstacleView> obstacleViewGroup = new ArrayList<>();
    private double tileSize;

    @Requires("controller != null")
    public BoardPane(Controller controller,ChangeBoardSizePane changeBoardSizePane) {
    	this.col = Integer.valueOf(changeBoardSizePane.getBoardColTextField().getText());
    	this.row = Integer.valueOf(changeBoardSizePane.getBoardRowTextField().getText());
    	 
    	for (int y = 0; y < this.row; y++) {
             for (int x = 0; x < this.col; x++) {
                 TileView tileView = new TileView(x, y, tileSize, controller);
                 tileViewGroup.add(tileView);
             }
         }
         this.getChildren().addAll(tileViewGroup);

         setDynamicTileSize();
     }

    private void setDynamicTileSize() {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = getWidth();
            double paneHeight = getHeight();
            double boardSideSize = paneWidth < paneHeight ? paneWidth : paneHeight;
            tileSize = boardSideSize / ((col + row) / 1.75);
            updateTilesSize(tileSize, paneWidth, paneHeight);
            updatePiecesSize(tileSize);
            updateObstacleSize(tileSize);
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    @Requires("tileSize >= 0 && width >= tileSize && height >= tileSize")
    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * col)) / 2;
        double mostTopY = (height - (tileSize * row)) / 2;

        for (TileView tileView : getTileViewGroup()) {
            tileView.updateSize(tileSize);
            tileView.updatePosition(tileSize, mostLeftX, mostTopY);
        }
    }

    @Requires("tileSize >= 0")
    private void updatePiecesSize(double tileSize) {
        for (PieceView pieceView : pieceViewGroup) {
            pieceView.updateSize(tileSize);
        }
    }

    private void updateObstacleSize(double tileSize) {
        for (ObstacleView obstacleView : obstacleViewGroup) {
            obstacleView.updateSize(tileSize);
        }
    }

    public List<TileView> getTileViewGroup() {
        return tileViewGroup;
    }

	@Requires("x >= 0 && y >= 0 && x < col && y < row")
    private TileView getTileView(int x, int y) {
		for (Node node : tileViewGroup) {
			if (((TileView) node).getX() == x && ((TileView) node).getY() == y) {
				return (TileView) node;
			}
		}
		return null;
	}

	public PieceView instantiatePieceView(int x, int y, String name, Color color) {
        PieceView pieceView = new PieceView(name, color);
        getTileView(x, y).setGameObjAvatar(pieceView);
        pieceViewGroup.add(pieceView);
        return pieceView;
    }

    public ObstacleView instantiateObstacleView(int x, int y) {
        ObstacleView obstacleView = new ObstacleView();
        getTileView(x, y).setGameObjAvatar(obstacleView);
        obstacleViewGroup.add(obstacleView);
        return obstacleView;
    }
}