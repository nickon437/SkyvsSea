package skyvssea.view;

import com.google.java.contract.Requires;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import skyvssea.controller.Controller;
import skyvssea.model.Avatar;
import skyvssea.util.ColorUtil;

import java.util.Observable;
import java.util.Observer;

public class TileView extends Avatar implements Observer {

    public static final Color DEFAULT_LIGHT_BASE_COLOR = Color.valueOf("#FCF5EF");
    public static final Color DEFAULT_DARK_BASE_COLOR = Color.valueOf("#BDBDBD");
    public static final Color HIGHLIGHTED_COLOR = Color.valueOf("#FCC42C");
    public static final Color SCANNED_COLOR = Color.valueOf("#fde7aa");

    private Rectangle base;
    private int x;
    private int y;
    private boolean hasLightBaseColor;

    @Requires("x >= 0 && y >= 0 && tileSize >= 0 && controller != null")
    public TileView(int x, int y, double tileSize, Controller controller) {
        this.x = x;
        this.y = y;
        this.hasLightBaseColor = setDefaultBaseColor(x, y);
        this.base = createBase(x, y, tileSize);
        this.getChildren().add(base);

        this.setOnMouseClicked(e -> controller.handleTileClicked(this));
        this.setOnMouseEntered(e -> controller.handleMouseEnteredTile(this));
        this.setOnMouseExited(e -> controller.handleMouseExitedTile(this));
    }

	private boolean setDefaultBaseColor(int x, int y) {
		return (x + y) % 2 == 0;
	}

    @Requires("x >= 0 && y >= 0 && tileSize >= 0")
    private Rectangle createBase(int x, int y, double tileSize) {
        Rectangle base = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
        base.setStroke(DEFAULT_DARK_BASE_COLOR);
        return base;
    }

    @Override
    public void updateSize(double tileSize) {
        base.setWidth(tileSize);
        base.setHeight(tileSize);
    }

    @Requires("tileSize >= 0")
    public void updatePosition(double tileSize, double mostLeftX, double mostTopY) {
        setTranslateX(mostLeftX + x * tileSize);
        setTranslateY(mostTopY + y * tileSize);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Requires("color != null")
    private void updateBaseColor(Color color) {
        base.setFill(color);
    }

    public void updateBaseColorAsHovered(boolean isHovered) {
        Color baseColor = (Color) base.getFill();
        Color modifiedColor = ColorUtil.getHoveringColor(isHovered, baseColor);
        base.setFill(modifiedColor);
    }

    @Requires("arg instanceof Boolean || arg instanceof Avatar || arg == null")
	@Override
	public void update(Observable tile, Object arg) {
        if (arg instanceof Boolean) {
            Color baseColor;
            if ((Boolean) arg == true) {
                baseColor = HIGHLIGHTED_COLOR;
            } else {
                baseColor = hasLightBaseColor ? DEFAULT_LIGHT_BASE_COLOR : DEFAULT_DARK_BASE_COLOR;
            }
            updateBaseColor(baseColor);
        } else if (arg instanceof Avatar) {
            setGameObjAvatar((Avatar) arg);
        } else if (arg == null) {
        	removeGameObjAvatar();
        }
    }

    @Requires("avatar != null")
	public void setGameObjAvatar(Avatar avatar) {
        getChildren().add(avatar);
    }
    
    private void removeGameObjAvatar() {
		for (Node node : getChildren()) {
            if (node instanceof Avatar) {
            	getChildren().remove(node);
            	return;
            }
        }
	}
}
