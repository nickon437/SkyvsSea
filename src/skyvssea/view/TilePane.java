package skyvssea.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TilePane extends StackPane {

    public static final String DEFAULT_LIGHT_BASE_COLOR = "#FCF5EF";
    public static final String DEFAULT_DARK_BASE_COLOR = "#264F73";
    public static final String HIGHLIGHED_COLOR = "#FF5733";

    private Rectangle base;
    private int x;
    private int y;

    public TilePane(int x, int y, double tileSize) {
        this.x = x;
        this.y = y;
        this.base = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
        base.setStroke(Color.valueOf(DEFAULT_DARK_BASE_COLOR));

        this.getChildren().add(base);
    }

    public void updateTileSize(double newTileSize, double mostLeftX, double mostTopY) {
        base.setWidth(newTileSize);
        base.setHeight(newTileSize);
        setTranslateX(mostLeftX + x * newTileSize);
        setTranslateY(mostTopY + y * newTileSize);
    }

    public Rectangle getBase() { return base; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void updateBaseColor(String color) {
        base.setFill(Color.valueOf(color));
    }
}
