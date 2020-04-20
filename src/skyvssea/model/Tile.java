package skyvssea.model;

import java.util.Observable;

import skyvssea.view.PieceView;
import skyvssea.view.TilePane;

public class Tile  extends Observable {
//    private TilePane tileView; //Probably not appropriate to have a View object here in Model; should try using an interface instead such as Listener
	private int x;
	private int y;
    private Piece piece;
    private boolean light;
    private boolean isHighlighted;

	public Tile(int x, int y, /* TilePane tilePane, */boolean light) {
//        this.tileView = tilePane;
		this.x = x;
		this.y = y;
        this.light = light;
        piece = null;
//        setHighlighted(false);
    }

//    public TilePane getTilePane() { return tileView; }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean setPiece(Piece piece) {
        this.piece = piece;
        setChanged();
        notifyObservers(piece.getName());
        return true;
//        PieceView pieceView = piece.getPieceView();
//        return tileView.getChildren().add(pieceView); //TODO: should be handled by observer pattern
    }

    public void removePiece() {
        piece = null;
//        setChanged();
//        notifyObservers("REMOVE_PIECEVIEW");
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        //TODO: the color changing should be handled by TilePane class and triggered by the observer pattern
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
//        tileView.updateBaseColor(baseColor);
        setChanged();
        notifyObservers(baseColor);
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
