package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import skyvssea.controller.Controller;

import java.util.Observable;
import java.util.Observer;

public class TilePane extends StackPane implements Observer {

    public static final Color DEFAULT_LIGHT_BASE_COLOR = Color.valueOf("#FCF5EF");
    public static final Color DEFAULT_DARK_BASE_COLOR = Color.valueOf("#264F73");
    public static final Color HIGHLIGHTED_COLOR = Color.valueOf("#FF5733");

    private Rectangle base;
    private int x;
    private int y;
    private boolean light;

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL && " +
            "tileSize >= 0 && controller != null")
    public TilePane(int x, int y, boolean light, double tileSize, Controller controller) {
        this.x = x;
        this.y = y;
        this.light = light;
        this.base = createBase(x, y, tileSize);
        this.getChildren().add(base);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> controller.handleTileClicked(this));
    }

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL && " +
            "tileSize >= 0")
    private Rectangle createBase(int x, int y, double tileSize) {
        Rectangle base = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
        base.setStroke(DEFAULT_DARK_BASE_COLOR);
        return base;
    }

    @Requires("newTileSize >= 0")
    public void updateTileSize(double newTileSize, double mostLeftX, double mostTopY) {
        base.setWidth(newTileSize);
        base.setHeight(newTileSize);
        setTranslateX(mostLeftX + x * newTileSize);
        setTranslateY(mostTopY + y * newTileSize);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Requires("color != null")
    private void updateBaseColor(Color color) {
        base.setFill(color);
    }

    @Requires("isHighlighted != null && isHighlighted instanceof Boolean")
	@Override
	public void update(Observable tile, Object isHighlighted) {
		if (((Boolean) isHighlighted).equals(Boolean.TRUE)) {
			updateBaseColor(HIGHLIGHTED_COLOR);
		} else {
			if (light) {
				updateBaseColor(DEFAULT_LIGHT_BASE_COLOR);
			} else {
				updateBaseColor(DEFAULT_DARK_BASE_COLOR);
			}
		}
    }

	public PieceView getPieceView() {
        for (Node node : getChildren()) {
            if (node instanceof PieceView) {
                return (PieceView) node;
            }
        }
		return null;
	}

	@Requires("pieceView != null")
	public void setPieceView(PieceView pieceView) {
		getChildren().add(pieceView);
	}
}
