package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;

import java.util.Observable;

public class Tile extends Observable implements AvatarCore {
    private Avatar avatar;
    private int x;
	private int y;
    private GameObject gameObject;
    private boolean isHighlighted;
    private boolean isScanned; // Nick - TODO: Will implement highlightScanTile later. But need to write our own Obs classes

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL")
	public Tile(int x, int y) {
        this.x = x;
		this.y = y;
        this.gameObject = null;
    }

    public boolean hasGameObject() { return gameObject != null; }

    public boolean hasPiece() {
        return gameObject != null && gameObject instanceof AbstractPiece;
    }

    public GameObject getGameObject() { return gameObject; }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        if (gameObject.getAvatar() != null) {
            setChanged();
            notifyObservers(gameObject.getAvatar());
        }
    }

    public void removeGameObject() { 
    	this.gameObject = null; 
    	setChanged();
        notifyObservers(null);
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        setChanged();
        notifyObservers(isHighlighted);
    }

	public int getX() { return x; }
	public int getY() { return y; }

    @Override
    public void addAvatar(Avatar avatar) { this.avatar = avatar; }

    @Override
    public Avatar getAvatar() { return avatar; }
}
