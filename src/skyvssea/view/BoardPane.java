package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import skyvssea.controller.Controller;
import skyvssea.util.RegionUtil;

import java.util.ArrayList;
import java.util.List;

public class BoardPane extends Pane {
	private final int col;
	private final int row;
	
    private List<TileView> tileViewGroup = new ArrayList<>();
    private List<PieceView> pieceViewGroup = new ArrayList<>();
    private List<ObstacleView> obstacleViewGroup = new ArrayList<>();
    private double tileSize;

    private static final double CORNER_RADIUS = 15;

    @Requires("controller != null")
    public BoardPane(Controller controller, int col, int row) {
        this.col = col;
        this.row = row;

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                TileView tileView = new TileView(x, y, tileSize, controller);
                tileViewGroup.add(tileView);
            }
        }
        this.getChildren().addAll(tileViewGroup);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        this.setEffect(dropShadow);
        roundUpCorners();

        setDynamicTileSize();
    }

    private void roundUpCorners() {
        RegionUtil.setCornerRadii(this, new CornerRadii(30));

        TileView topLeftTileView = getTileView(0, 0);
        topLeftTileView.setCornerRadius(new CornerRadii(CORNER_RADIUS, 0, 0, 0, false));

        TileView topRightTileView = getTileView(col - 1, 0);
        topRightTileView.setCornerRadius(new CornerRadii(0, CORNER_RADIUS, 0, 0, false));

        TileView bottomRightTileView = getTileView(col - 1, row - 1);
        bottomRightTileView.setCornerRadius(new CornerRadii(0, 0, CORNER_RADIUS, 0, false));

        TileView bottomLeftTileView = getTileView(0, row - 1);
        bottomLeftTileView.setCornerRadius(new CornerRadii(0, 0, 0, CORNER_RADIUS, false));
    }

    private void setDynamicTileSize() {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = getWidth();
            double paneHeight = getHeight();

            double rowTileSize = paneHeight / row;
            double colTileSize = paneWidth / col;
            double newTileSize = rowTileSize < colTileSize ? rowTileSize : colTileSize;

            this.tileSize = newTileSize;
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