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

    public static final int NUM_SIDE_CELL = 10;
    private List<TileView> tileViewGroup = new ArrayList<>();
    private List<PieceView> pieceViewGroup = new ArrayList<>();
    private List<ObstacleView> obstacleViewGroup = new ArrayList<>();
    private double tileSize;

    private static final double CORNER_RADIUS = 15;

    @Requires("controller != null")
    public BoardPane(Controller controller) {
        for (int y = 0; y < NUM_SIDE_CELL; y++) {
            for (int x = 0; x < NUM_SIDE_CELL; x++) {
                TileView tileView = new TileView(x, y, tileSize, controller);
                tileViewGroup.add(tileView);
            }
        }
        this.getChildren().addAll(tileViewGroup);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        this.setEffect(dropShadow);
        RegionUtil.setCornerRadii(this, new CornerRadii(30));
        roundUpCorners();

        setDynamicTileSize();
    }

    private void roundUpCorners() {
        TileView topLeftTileView = getTileView(0, 0);
        topLeftTileView.setCornerRadius(new CornerRadii(CORNER_RADIUS, 0, 0, 0, false));

        TileView topRightTileView = getTileView(NUM_SIDE_CELL - 1, 0);
        topRightTileView.setCornerRadius(new CornerRadii(0, CORNER_RADIUS, 0, 0, false));

        TileView bottomRightTileView = getTileView(NUM_SIDE_CELL - 1, NUM_SIDE_CELL - 1);
        bottomRightTileView.setCornerRadius(new CornerRadii(0, 0, CORNER_RADIUS, 0, false));

        TileView bottomLeftTileView = getTileView(0, NUM_SIDE_CELL - 1);
        bottomLeftTileView.setCornerRadius(new CornerRadii(0, 0, 0, CORNER_RADIUS, false));
    }

    private void setDynamicTileSize() {
        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            double paneWidth = getWidth();
            double paneHeight = getHeight();
            double boardSideSize = paneWidth < paneHeight ? paneWidth : paneHeight;
            tileSize = boardSideSize / NUM_SIDE_CELL;
            updateTilesSize(tileSize, paneWidth, paneHeight);
            updatePiecesSize(tileSize);
            updateObstacleSize(tileSize);
        };

        widthProperty().addListener(paneSizeListener);
        heightProperty().addListener(paneSizeListener);
    }

    @Requires("tileSize >= 0 && width >= tileSize && height >= tileSize")
    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * NUM_SIDE_CELL)) / 2;

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

	@Requires("x >= 0 && y >= 0 && x < NUM_SIDE_CELL && y < NUM_SIDE_CELL")
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