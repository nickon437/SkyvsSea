package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import skyvssea.controller.Controller;
import skyvssea.model.Avatar;
import skyvssea.model.EventType;
import skyvssea.model.observer.Observer;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;

public class TileView extends Avatar implements Observer {

    private Region base;
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

        this.setOnMouseEntered(e -> {
            RegionUtil.formatHoveringEffect(base, true);
            controller.handleMouseEnteredTile(this);
        });

        this.setOnMouseExited(e -> {
            RegionUtil.formatHoveringEffect(base, false);
            this.setCursor(Cursor.DEFAULT);
        });

        this.setOnMouseClicked(e -> controller.handleTileClicked(this));
    }

	private boolean setDefaultBaseColor(int x, int y) {
		return (x + y) % 2 == 0;
	}

    @Requires("x >= 0 && y >= 0 && tileSize >= 0")
    private Region createBase(int x, int y, double tileSize) {
        Region base = new Region();
        base.setBorder(new Border(new BorderStroke(Color.SLATEGREY, BorderStrokeStyle.SOLID, null, new BorderWidths(0.5))));
        base.setTranslateX(x * tileSize);
        base.setTranslateY(y * tileSize);
        base.setPrefSize(tileSize, tileSize);
        return base;
    }

    public void setCornerRadius(CornerRadii cornerRadius) {
        RegionUtil.setCornerRadii(base, cornerRadius);
        RegionUtil.setBorderRadius(base, cornerRadius);
    }

    @Override
    public void updateSize(double tileSize) {
        base.setPrefSize(tileSize, tileSize);
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
        RegionUtil.setFill(base, color);
    }

    @Requires("arg instanceof Boolean || arg instanceof Avatar || arg == null")
	@Override
	public void update(EventType event, Object arg) {
    	switch (event) {
    		case HIGHLIGHT:
    			Color baseColor;
                if ((boolean) arg == true) {
                    baseColor = ColorUtil.HIGHLIGHTED_COLOR;
                    this.setCursor(Cursor.HAND);
                } else {
                    baseColor = hasLightBaseColor ? ColorUtil.DEFAULT_LIGHT_BASE_COLOR : ColorUtil.DEFAULT_DARK_BASE_COLOR;
                    this.setCursor(Cursor.DEFAULT);
                }
                updateBaseColor(baseColor);
    			break;
    		case SET_GAME_OBJECT:
    			if (arg != null) {
    				setGameObjAvatar((Avatar) arg);
    			} else {
    				removeGameObjAvatar();
    			}
    			break;
    		case DISABLE:
    			setDisable((boolean) arg);
    			break;
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
