package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.Piece;

import java.util.Observable;

public class Tile extends Observable {
    private int x;
	private int y;
    private Piece piece;
    private boolean isHighlighted;

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL")
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
        this.piece = null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() { return piece; }

    public void setPiece(Piece piece) { this.piece = piece; }

    public void removePiece() { this.piece = null; }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        setChanged();
        notifyObservers(isHighlighted);
    }

	public int getX() { return x; }

	public int getY() { return y; }
}
