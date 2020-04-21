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
    private ArrayList<PieceView> pieceViewGroup = new ArrayList<>();
    private double tileSize;

    public BoardPane(Controller controller) {
        for (int y = 0; y < NUM_SIDE_CELL; y++) {
            for (int x = 0; x < NUM_SIDE_CELL; x++) {
                TilePane tileView = new TilePane(x, y, tileSize, this);
                tileView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    controller.handleTileClicked(tileView);
                });

                tileGroup.getChildren().add(tileView);
            }
        }
        this.getChildren().add(getTileGroup());

        setDynamicTileSize();
    }

    public void setDynamicTileSize() {
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

    private void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * NUM_SIDE_CELL)) / 2;

        for (Node node : getTileGroup().getChildren()) {
            TilePane tilePane = (TilePane) node;
            tilePane.updateTileSize(tileSize, mostLeftX, mostTopY);
        }
    }

    private void updatePiecesSize(double tileSize) {
        for (PieceView pieceView : pieceViewGroup) {
            pieceView.updatePieceViewSize(tileSize);
        }
    }

//    public Tile setTile(TilePane tileView, int x, int y) {
//        Tile tile = new Tile(tileView, (x + y) % 2 == 0);
//        tiles[x][y] = tile;
//        return tile;
//    }

    // Nick - TODO: Need to modify this as this getTiles() method is only used by Board once when setting up and shouldn't be accessible in any other circumstances
//    public Tile[][] getTiles() {
//        return tiles;
//    }

    public void setPieceGroup(ArrayList<PieceView> pieceViews) {
        this.pieceViewGroup = pieceViews;
    }

    public void addPieceView(PieceView pieceView) {
    	pieceViewGroup.add(pieceView);
    }

	public Group getTileGroup() {
		return tileGroup;
	}

	public TilePane getTileView(int x, int y) {
		for (Node node : tileGroup.getChildren()) {
			if (((TilePane) node).getX() == x && ((TilePane) node).getY() == y) {
				return (TilePane) node;
			}
		}
		return null;
	}

//	public void createPieceViews(ArrayList<String> names) {
//		ArrayList<PieceView> pieceViews = new ArrayList<>();
//		for (String name : names) {
//			pieceViews.add(new PieceView(name));
//		}
//		pieceViewGroup = pieceViews;
//	}
	
//	public ArrayList<PieceView> getPieceViews() {
//		return pieceViewGroup;
//	}

//	public void removePieceView(String name) {
//		for (int i = 0; i < pieceViewGroup.size(); i ++) {
//			if (pieceViewGroup.get(i).getName().equals(name)) {
//				pieceViewGroup.remove(i);
//				break;
//			}
//		}
//	}

}