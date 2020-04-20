package skyvssea.view;

import java.util.Observable;
import java.util.Observer;

import com.google.java.contract.Requires;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TilePane extends StackPane implements Observer {

    public static final String DEFAULT_LIGHT_BASE_COLOR = "#FCF5EF";
    public static final String DEFAULT_DARK_BASE_COLOR = "#264F73";
    public static final String HIGHLIGHTED_COLOR = "#FF5733";

    private Rectangle base;
    private int x;
    private int y;
    private BoardPane boardPane;

    public TilePane(int x, int y, double tileSize, BoardPane boardPane) {
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
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

    private void updateBaseColor(String color) {
        base.setFill(Color.valueOf(color));
    }

    @Requires("arg != null && (arg instanceof String || arg instanceof PieceView)")
	@Override
	public void update(Observable tile, Object arg) {
		if (arg instanceof String) {
            if (((String) arg).charAt(0) == '#') {
                updateBaseColor((String) arg);
            }
        } else if (arg instanceof PieceView){
            this.getChildren().add((PieceView) arg);
		}
	}
}
