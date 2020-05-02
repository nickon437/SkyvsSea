package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import skyvssea.controller.Controller;

import java.util.Observable;
import java.util.Observer;

public class TilePane extends StackPane implements Observer {

    public static final Color DEFAULT_LIGHT_BASE_COLOR = Color.valueOf("#FCF5EF");
    public static final Color DEFAULT_DARK_BASE_COLOR = Color.valueOf("#BDBDBD");
    public static final Color HIGHLIGHTED_COLOR = Color.valueOf("#FCC42C");
    public static final Color SCANNED_COLOR = Color.valueOf("#fde7aa");

    private Rectangle base;
    private int x;
    private int y;
    private boolean hasLightBaseColor;

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL && " +
            "tileSize >= 0 && controller != null")
    public TilePane(int x, int y, double tileSize, Controller controller) {
        this.x = x;
        this.y = y;
        this.hasLightBaseColor = setDefaultBaseColor(x, y);
        this.base = createBase(x, y, tileSize);
        this.getChildren().add(base);

        this.setOnMouseClicked(e -> controller.handleTileClicked(this));
        this.setOnMouseEntered(e -> controller.handleTileMouseEntered(this));
        this.setOnMouseExited(e -> controller.handleTileMouseExited(this));
    }

	private boolean setDefaultBaseColor(int x, int y) {
		return (x + y) % 2 == 0;
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

    public void updateBaseColorAsHovered(boolean isHovered) {
        Color baseColor = (Color) base.getFill();
        baseColor = isHovered ? Color.color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), .7) :
                Color.color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 1);
        base.setFill(baseColor);
    }

    @Requires("isHighlighted != null && isHighlighted instanceof Boolean")
	@Override
	public void update(Observable tile, Object isHighlighted) {
		if (((Boolean) isHighlighted).equals(Boolean.TRUE)) {
			updateBaseColor(HIGHLIGHTED_COLOR);
		} else {
			if (hasLightBaseColor) {
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
