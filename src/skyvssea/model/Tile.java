package skyvssea.model;

import java.util.Observable;

import skyvssea.view.PieceView;
import skyvssea.view.TilePane;

public class Tile extends Observable {
    private int x;
	private int y;
    private Piece piece;
    private boolean light;
    private boolean isHighlighted;

	public Tile(int x, int y, boolean light) {
		this.x = x;
		this.y = y;
        this.light = light;
        piece = null;
        setHighlighted(false);
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

    public void removePiece() {
        piece = null;
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;

        String baseColor;
        if (isHighlighted) {
            baseColor = TilePane.HIGHLIGHTED_COLOR;
        } else {
            if (light) {
                baseColor = TilePane.DEFAULT_LIGHT_BASE_COLOR;
            } else {
                baseColor = TilePane.DEFAULT_DARK_BASE_COLOR;
            }
        }
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
