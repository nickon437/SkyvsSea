package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;

import java.util.Observable;

public class Tile extends Observable {
    private int x;
	private int y;
    private AbstractPiece piece;
    private boolean isHighlighted;
    private boolean isScanned; // Nick - TODO: Will implement highlightScanTile later. But need to write our own Obs classes

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.boardCol && y < skyvssea.view.BoardPane.boardRow")
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
        this.piece = null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public AbstractPiece getPiece() { return piece; }

    public void setPiece(AbstractPiece piece) { this.piece = piece; }

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
