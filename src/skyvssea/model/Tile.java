package skyvssea.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    private Rectangle tileView; //Probably not appropriate to have a View object here in Model; should try using an interface instead such as Listener
    private Piece piece;
    private boolean light;

    // TODO: Remove this later
    private String RED = "#ff7350";
    private String DARK_BLUE = "#264F73";

    public Tile(Rectangle tilePane, boolean light) {
        this.tileView = tilePane;
        this.light = light;
        piece = null;

        tilePane.setFill(light ? Color.valueOf("#fcf5ef") : Color.valueOf("#264F73")); // TODO: This should be in the view, maybe?
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
