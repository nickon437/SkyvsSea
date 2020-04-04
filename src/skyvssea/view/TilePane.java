package skyvssea.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TilePane extends Rectangle {

    private int x;
    private int y;

    public TilePane(int x, int y, double tileSize) {
        super(x * tileSize, y * tileSize, tileSize, tileSize);
        this.x = x;
        this.y = y;

//this.setStrokeWidth(1d);
this.setStroke(Color.valueOf("#264F73"));
//        this.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void updateTileSize(double newTileSize, double mostLeftX, double mostTopY) {
        setX(mostLeftX + x * newTileSize);
        setY(mostTopY + y * newTileSize);
        setWidth(newTileSize);
        setHeight(newTileSize);
    }
}
