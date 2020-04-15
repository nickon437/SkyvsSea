package skyvssea.model;

import skyvssea.view.PieceView;
import skyvssea.view.TilePane;

public class Tile {
    private TilePane tileView; //Probably not appropriate to have a View object here in Model; should try using an interface instead such as Listener
    private Piece piece;
    private boolean light;
    private boolean isHighlighted;

    // TODO: Remove this later
    private String RED = "#ff7350";
    private String DARK_BLUE = "#264F73";

    public Tile(TilePane tilePane, boolean light) {
        this.tileView = tilePane;
        this.light = light;
        piece = null;
        setHighlighted(false);
    }

    public TilePane getTilePane() { return tileView; }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean setPiece(Piece piece) {
        this.piece = piece;
        PieceView pieceView = piece.getPieceView();
        return tileView.getChildren().add(pieceView);
    }

    public void removePiece() {
        piece = null;
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        String baseColor;
        if (isHighlighted) {
            baseColor = TilePane.HIGHLIGHED_COLOR;
        } else {
            if (light) {
                baseColor = TilePane.DEFAULT_LIGHT_BASE_COLOR;
            } else {
                baseColor = TilePane.DEFAULT_DARK_BASE_COLOR;
            }
        }
        tileView.updateBaseColor(baseColor);
    }
}
