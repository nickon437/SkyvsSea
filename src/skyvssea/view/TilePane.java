package skyvssea.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TilePane extends StackPane {

    private Rectangle base;
    private int x;
    private int y;

    public TilePane(int x, int y, double tileSize) {
        this.x = x;
        this.y = y;
        this.base = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
        base.setStroke(Color.valueOf("#264F73"));

        this.getChildren().add(base);
    }

    public void updateTileSize(double newTileSize, double mostLeftX, double mostTopY) {
        base.setWidth(newTileSize);
        base.setHeight(newTileSize);
        setTranslateX(mostLeftX + x * newTileSize);
        setTranslateY(mostTopY + y * newTileSize);
    }

    public Rectangle getBase() { return base; }
}
